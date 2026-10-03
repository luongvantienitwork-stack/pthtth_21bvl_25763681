package ktrathuTCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private static final int PORT = 7000;

    public static void main(String[] args) {
        System.out.println("Server đang khởi tạo tại port " + PORT + "...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server đã sẵn sàng chờ kết nối từ client...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client mới kết nối từ: " + clientSocket.getInetAddress().getHostAddress());

                ClientHandle clientThread = new ClientHandle(clientSocket);
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Lỗi Server: " + e.getMessage());
        }
    }
}

class ClientHandle extends Thread {
    private Socket socket;

    public ClientHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream())
        ) {
            while (true) {
                String menu = "\n=== DANH SÁCH DỊCH VỤ ===\n" +
                              "1. Đảo ngược toàn bộ chuỗi & in hoa ký tự đầu từng từ\n" +
                              "2. Đảo ngược từng từ & in hoa ký tự đầu từng từ (giữ nguyên vị trí)\n" +
                              "3. Đếm số lượng từ của tất cả các dòng gửi đến\n" +
                              "4. Đếm số lượng từ từng dòng\n" +
                              "Nhập lựa chọn của bạn (1-4, hoặc 'EXIT.' để thoát): ";
                dos.writeUTF(menu);
                dos.flush();

                String choiceStr = dis.readUTF();

                if (choiceStr.equalsIgnoreCase("EXIT.")) {
                    dos.writeUTF("Đã nhận lệnh EXIT. Server đóng kết nối!");
                    dos.flush();
                    break;
                }

                int option = 0;
                try {
                    option = Integer.parseInt(choiceStr);
                } catch (NumberFormatException e) {
                    dos.writeUTF("Lựa chọn không hợp lệ. Vui lòng thử lại!");
                    dos.flush();
                    continue;
                }

                dos.writeUTF("OK");
                dos.flush();

                int totalWords = 0;
                int lineIndex = 1;

                while (true) {
                    String line = dis.readUTF();

                    if (line.equalsIgnoreCase("EXIT.")) {
                        dos.writeUTF("Đã nhận lệnh EXIT. Server đóng kết nối!");
                        dos.flush();
                        return;
                    }

                    if (line.trim().equals(".")) {
                        if (option == 3) {
                            dos.writeUTF("-> TỔNG SỐ TỪ CỦA TẤT CẢ CÁC DÒNG LÀ: " + totalWords);
                        } else {
                            dos.writeUTF("-> Hoàn tất xử lý đợt nhập này.");
                        }
                        dos.flush();
                        break;
                    }

                    String response = "";
                    switch (option) {
                        case 1:
                            response = dichVu1(line);
                            break;
                        case 2:
                            response = dichVu2(line);
                            break;
                        case 3:
                            int w3 = countWords(line);
                            totalWords += w3;
                            response = "Đã nhận dòng (" + w3 + " từ). Nhập tiếp hoặc gửi '.' để xem tổng số từ.";
                            break;
                        case 4:
                            int w4 = countWords(line);
                            response = "Dòng " + lineIndex + " có: " + w4 + " từ.";
                            lineIndex++;
                            break;
                        default:
                            response = "Dịch vụ không tồn tại!";
                            break;
                    }

                    dos.writeUTF(response);
                    dos.flush();
                }
            }

        } catch (IOException e) {
            System.err.println("Lỗi xử lý Client [" + socket.getPort() + "]: " + e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Đã đóng kết nối với Client.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String dichVu1(String input) {
        if (input == null || input.trim().isEmpty()) return "";
        String reversed = new StringBuilder(input.toLowerCase()).reverse().toString();
        String[] words = reversed.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty()) {
                result.append(Character.toUpperCase(w.charAt(0)))
                      .append(w.substring(1))
                      .append(" ");
            }
        }
        return result.toString().trim();
    }

    private String dichVu2(String input) {
        if (input == null || input.trim().isEmpty()) return "";
        String[] words = input.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty()) {
                String revWord = new StringBuilder(w.toLowerCase()).reverse().toString();
                String formatted = Character.toUpperCase(revWord.charAt(0)) + revWord.substring(1);
                result.append(formatted).append(" ");
            }
        }
        return result.toString().trim();
    }

    private int countWords(String input) {
        if (input == null || input.trim().isEmpty()) return 0;
        return input.trim().split("\\s+").length;
    }
}