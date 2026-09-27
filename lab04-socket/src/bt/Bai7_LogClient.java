package bt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Bai7_LogClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9993;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.print("Nhập clientId của bạn (chữ, số, -, _): ");
            String clientId = scanner.nextLine().trim();
            writer.println("HELLO " + clientId);

            String response = reader.readLine();
            System.out.println("Server: " + response);

            if (response == null || response.startsWith("ERR")) {
                return;
            }

            System.out.println("\nNhập nội dung tin nhắn (Nhập QUIT để thoát):");
            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine();
                writer.println(input);

                String serverAck = reader.readLine();
                if ("BYE".equalsIgnoreCase(serverAck) || "QUIT".equalsIgnoreCase(input.trim())) {
                    System.out.println("Đã ngắt kết nối.");
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Lỗi kết nối Server: " + e.getMessage());
        }
    }
}