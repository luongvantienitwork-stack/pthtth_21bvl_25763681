package bt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Bai4_CalcServer {
    private static final int PORT = 9997;

    public static void main(String[] args) {
        System.out.println("Calculator TCP Server đang chạy trên cổng " + PORT + "...");
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
                String line;
                while ((line = reader.readLine()) != null) {
                    if ("QUIT".equalsIgnoreCase(line.trim())) {
                        writer.println("BYE");
                        break;
                    }

                    String response = processCalculation(line);
                    writer.println(response);
                }
            } catch (IOException e) {
                System.err.println("Kết nối với Client bị ngắt đột ngột: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Lỗi đóng socket: " + e.getMessage());
                }
            }
        }

        private String processCalculation(String input) {
            if (input == null || input.trim().isEmpty()) {
                return "ERR INVALID_FORMAT";
            }

            String[] parts = input.trim().split("\\s+");

            if (parts.length != 4 || !"CALC".equalsIgnoreCase(parts[0])) {
                return "ERR INVALID_FORMAT";
            }

            String operator = parts[1];
            double num1, num2;

            try {
                num1 = Double.parseDouble(parts[2]);
                num2 = Double.parseDouble(parts[3]);
            } catch (NumberFormatException e) {
                return "ERR INVALID_NUMBER";
            }

            switch (operator) {
                case "+" -> {
                    return formatResult(num1 + num2);
                }
                case "-" -> {
                    return formatResult(num1 - num2);
                }
                case "*" -> {
                    return formatResult(num1 * num2);
                }
                case "/" -> {
                    if (num2 == 0) {
                        return "ERR DIVIDE_BY_ZERO";
                    }
                    return formatResult(num1 / num2);
                }
                default -> {
                    return "ERR UNSUPPORTED_OPERATOR";
                }
            }
        }

        private String formatResult(double result) {
            if (result == (long) result) {
                return String.format("OK %d", (long) result);
            } else {
                return String.format("OK %s", result);
            }
        }
    }
}