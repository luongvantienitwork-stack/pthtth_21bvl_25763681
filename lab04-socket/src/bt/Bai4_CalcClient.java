package bt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Bai4_CalcClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9997;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Đã kết nối tới Calculator Server.");
            System.out.println("Cú pháp gửi: CALC <toán_tử> <số_1> <số_2> (Ví dụ: CALC + 100 200)");
            System.out.println("Gửi QUIT để thoát.\n");

            while (true) {
                System.out.print("Nhập lệnh: ");
                String command = scanner.nextLine();

                if (command.trim().isEmpty()) {
                    continue;
                }

                writer.println(command);

                String response = reader.readLine();
                if (response == null) {
                    System.out.println("Server đã ngắt kết nối!");
                    break;
                }

                System.out.println("Phản hồi từ Server: " + response);

                if ("BYE".equalsIgnoreCase(response) || "QUIT".equalsIgnoreCase(command.trim())) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi kết nối TCP Client: " + e.getMessage());
        }
    }
}