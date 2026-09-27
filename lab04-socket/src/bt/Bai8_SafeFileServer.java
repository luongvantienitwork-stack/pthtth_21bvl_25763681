package bt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bai8_SafeFileServer {
    private static final int PORT = 9992;
    private static final String UPLOAD_DIR = "uploads/";
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        } catch (IOException e) {
            System.err.println("Không thể tạo thư mục upload: " + e.getMessage());
            return;
        }

        System.out.println("Safe File Server đang chạy trên cổng " + PORT + "...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                threadPool.execute(new ClientHandler(socket));
            }
        } catch (IOException e) {
            System.err.println("Lỗi Server: " + e.getMessage());
        } finally {
            threadPool.shutdown();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream())
            ) {
                String rawFileName = in.readUTF();
                long fileSize = in.readLong();
                String clientHash = in.readUTF();

                String safeFileName = Paths.get(rawFileName).getFileName().toString();
                if (safeFileName.isEmpty() || rawFileName.contains("..")) {
                    safeFileName = safeFileName.replaceAll("[^a-zA-Z0-9._-]", "_");
                    if (safeFileName.isEmpty()) safeFileName = "uploaded_file.bin";
                }

                File destFile = new File(UPLOAD_DIR + safeFileName);
                System.out.printf("[Server] Đang nhận file: %s (%d bytes) từ %s\n", safeFileName, fileSize, socket.getRemoteSocketAddress());

                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                try (FileOutputStream fos = new FileOutputStream(destFile)) {
                    byte[] buffer = new byte[8192];
                    long bytesRemaining = fileSize;
                    int bytesToRead;

                    while (bytesRemaining > 0) {
                        bytesToRead = (int) Math.min(buffer.length, bytesRemaining);
                        int bytesRead = in.read(buffer, 0, bytesToRead);
                        
                        if (bytesRead == -1) {
                            out.writeUTF("ERR FILE_INCOMPLETE (Thiếu dữ liệu)");
                            destFile.delete();
                            return;
                        }

                        fos.write(buffer, 0, bytesRead);
                        digest.update(buffer, 0, bytesRead);
                        bytesRemaining -= bytesRead;
                    }
                }

                byte[] calculatedHashBytes = digest.digest();
                StringBuilder hexString = new StringBuilder();
                for (byte b : calculatedHashBytes) {
                    hexString.append(String.format("%02x", b));
                }
                String serverHash = hexString.toString();

                if (serverHash.equalsIgnoreCase(clientHash)) {
                    out.writeUTF("OK");
                    System.out.println("[Server] Lưu file thành công, SHA-256 khớp: " + safeFileName);
                } else {
                    out.writeUTF("ERR HASH_MISMATCH");
                    System.err.println("[Server] Lỗi SHA-256 không khớp! Đã xóa file hỏng.");
                    destFile.delete();
                }

            } catch (NoSuchAlgorithmException e) {
                System.err.println("Lỗi thuật toán SHA-256: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Lỗi truyền nhận file: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }
    }
}