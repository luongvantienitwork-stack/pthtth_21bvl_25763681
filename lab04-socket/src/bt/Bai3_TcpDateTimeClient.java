package bt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Bai3_TcpDateTimeClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9999;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Đã kết nối tới TCP Server.");
            while (true) {
                System.out.print("Nhập lệnh (DATE, TIME, DATETIME, QUIT): ");
                String command = scanner.nextLine();
                writer.println(command);

                String response = reader.readLine();
                if (response == null) {
                    System.out.println("Server đã ngắt kết nối!");
                    break;
                }

                System.out.println("Phản hồi từ Server: " + response);
                if ("BYE".equals(response) || "QUIT".equalsIgnoreCase(command)) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi kết nối TCP: " + e.getMessage());
        }
    }
}