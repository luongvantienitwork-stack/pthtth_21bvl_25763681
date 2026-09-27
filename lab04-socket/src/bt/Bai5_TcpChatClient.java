package bt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Bai5_TcpChatClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9996;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            while (true) {
                String serverMsg = reader.readLine();
                if (serverMsg == null) {
                    System.out.println("Không thể kết nối đến Server.");
                    return;
                }

                if (serverMsg.startsWith("SUBMIT_NICKNAME")) {
                    System.out.print("Nhập nickname của bạn: ");
                    String nick = scanner.nextLine().trim();
                    writer.println(nick);
                } else if (serverMsg.startsWith("ERR_NICKNAME")) {
                    System.out.println("Lỗi: " + serverMsg.substring(13));
                } else if (serverMsg.startsWith("NICKNAME_ACCEPTED")) {
                    System.out.println("-> Đăng ký thành công! Bạn có thể bắt đầu chat.");
                    System.out.println("Cú pháp: MSG <nội dung> | USERS | QUIT\n");
                    break;
                }
            }

            Thread receiveThread = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = reader.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    System.out.println("Đã ngắt kết nối khỏi phòng chat.");
                }
            });
            receiveThread.setDaemon(true);
            receiveThread.start();

            while (true) {
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    if (input != null && !input.trim().isEmpty()) {
                        writer.println(input.trim());
                        if ("QUIT".equalsIgnoreCase(input.trim())) {
                            break;
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Lỗi kết nối Server: " + e.getMessage());
        }
    }
}