package bt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bai7_LogServer {
    private static final int PORT = 9993;
    private static final String LOG_DIR = "data/logs/";
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        try {
            Files.createDirectories(Paths.get(LOG_DIR));
        } catch (IOException e) {
            System.err.println("Không thể tạo thư mục log: " + e.getMessage());
            return;
        }

        System.out.println("Log Server đang chạy trên cổng " + PORT + "...");
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
            String remoteAddress = socket.getRemoteSocketAddress().toString();
            try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
            ) {
                String firstLine = reader.readLine();
                if (firstLine == null || !firstLine.startsWith("HELLO ")) {
                    writer.println("ERR Cần gửi lệnh HELLO clientId trước!");
                    return;
                }

                String clientId = firstLine.substring(6).trim();

                if (!clientId.matches("^[a-zA-Z0-9_-]+$")) {
                    writer.println("ERR clientId chỉ được chứa chữ, số, dấu gạch ngang (-) và gạch dưới (_)");
                    return;
                }

                writer.println("OK Vui lòng bắt đầu gửi tin nhắn. Gửi QUIT để kết thúc.");
                System.out.println("[Server LOG] Client đã đăng nhập với clientId: " + clientId + " từ " + remoteAddress);

                File logFile = new File(LOG_DIR + clientId + ".txt");

                try (FileWriter fw = new FileWriter(logFile, true);
                     BufferedWriter bw = new BufferedWriter(fw);
                     PrintWriter logWriter = new PrintWriter(bw)) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        if ("QUIT".equalsIgnoreCase(line.trim())) {
                            writer.println("BYE");
                            break;
                        }

                        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
                        String logEntry = String.format("[%s] [%s] %s", timestamp, remoteAddress, line);
                        
                        logWriter.println(logEntry);
                        logWriter.flush(); 

                        writer.println("ACK");
                    }
                }

            } catch (IOException e) {
                System.err.println("Lỗi xử lý Client " + remoteAddress + ": " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Lỗi đóng socket: " + e.getMessage());
                }
            }
        }
    }
}