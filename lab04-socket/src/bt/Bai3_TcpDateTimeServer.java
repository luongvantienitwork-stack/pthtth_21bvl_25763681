package bt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Bai3_TcpDateTimeServer {
    private static final int PORT = 9999;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MM yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH mm ss");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd MM yyyy HH mm ss");

    public static void main(String[] args) {
        System.out.println("TCP Server đang chạy trên cổng " + PORT + "...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client mới kết nối: " + clientSocket.getInetAddress());
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Lỗi Server: " + e.getMessage());
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
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
            ) {
                String command;
                while ((command = reader.readLine()) != null) {
                    command = command.trim().toUpperCase();
                    if ("QUIT".equals(command)) {
                        writer.println("BYE");
                        break;
                    }

                    String response = processCommand(command);
                    writer.println(response);
                }
            } catch (IOException e) {
                System.err.println("Kết nối với Client bị ngắt đột ngột: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Lỗi khi đóng socket: " + e.getMessage());
                }
            }
        }

        private String processCommand(String command) {
            return switch (command) {
                case "DATE" -> LocalDate.now().format(DATE_FORMATTER);
                case "TIME" -> LocalTime.now().format(TIME_FORMATTER);
                case "DATETIME" -> LocalDateTime.now().format(DATETIME_FORMATTER);
                default -> "ERROR: Lệnh không hợp lệ (Dùng DATE, TIME, DATETIME, QUIT)";
            };
        }
    }
}