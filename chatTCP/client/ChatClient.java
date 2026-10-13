package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 7000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            Thread readThread = new Thread(() -> {
                try {
                    String response;
                    while ((response = reader.readLine()) != null) {
                        if (response.equals("SUBMIT_NAME")) {
                            System.out.print("Nhập Tên của bạn: ");
                        } else if (response.startsWith("ROOM_MENU:")) {
                            String rooms = response.substring(10);
                            System.out.println("\n--- DANH SÁCH PHÒNG CHAT ---");
                            System.out.println("Phòng có sẵn: " + rooms);
                            System.out.println("Nhập tên phòng muốn vào (hoặc tự tạo mới), hoặc gõ '/exit' để thoát:");
                            System.out.print("Lựa chọn: ");
                        } else if (response.startsWith("IN_ROOM:")) {
                            System.out.println("\n-> Đã vào phòng [" + response.substring(8) + "]");
                            System.out.println("Gõ tin nhắn để chat, '/quit' để về danh sách phòng, '/exit' để thoát.");
                        } else if (response.equals("LEFT_ROOM")) {
                            System.out.println("\n-> Đã rời phòng.");
                        } else {
                            System.out.println(response);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Mất kết nối tới server.");
                }
            });
            readThread.start();

            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                writer.println(input);
                if (input.equalsIgnoreCase("/exit")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Không thể kết nối đến server.");
        }
    }
}