package bt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Bai8_SafeFileClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9992;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Nhập đường dẫn file cần gửi: ");
            String filePathStr = scanner.nextLine().trim();

            File file = new File(filePathStr);
            if (!file.exists() || !file.isFile()) {
                System.err.println("Lỗi: File không tồn tại!");
                return;
            }

            String sha256Hash = calculateSHA256(file);
            long fileSize = file.length();
            String fileName = file.getName();

            System.out.println("\n--- Thông tin File ---");
            System.out.println("Tên file  : " + fileName);
            System.out.println("Kích thước: " + fileSize + " bytes");
            System.out.println("SHA-256   : " + sha256Hash);

            try (
                Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());
                FileInputStream fis = new FileInputStream(file)
            ) {
                out.writeUTF(fileName);
                out.writeLong(fileSize);
                out.writeUTF(sha256Hash);

                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                out.flush();

                String response = in.readUTF();
                System.out.println("\nKết quả từ Server: " + response);

            } catch (IOException e) {
                System.err.println("Lỗi kết nối truyền file: " + e.getMessage());
            }

        } catch (NoSuchAlgorithmException | IOException e) {
            System.err.println("Lỗi xử lý file hoặc thuật toán mã hóa: " + e.getMessage());
        }
    }

    private static String calculateSHA256(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }
        byte[] hashBytes = digest.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}