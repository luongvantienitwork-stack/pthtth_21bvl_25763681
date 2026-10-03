package ktrathuTCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 7000;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Đã kết nối thành công tới Server " + SERVER_IP + ":" + SERVER_PORT);

            while (true) {
                String menu = dis.readUTF();
                System.out.print(menu);

                String choice = scanner.nextLine();
                dos.writeUTF(choice);
                dos.flush();

                if (choice.equalsIgnoreCase("EXIT.")) {
                    String serverRes = dis.readUTF();
                    System.out.println("Server phản hồi: " + serverRes);
                    break;
                }

                String status = dis.readUTF();
                if (!status.equals("OK")) {
                    System.out.println("Server phản hồi: " + status);
                    continue;
                }

                System.out.println("\n--- BẮT ĐẦU NHẬP DỮ LIỆU ---");
                System.out.println("- Gửi '.' trên một dòng riêng để kết thúc đợt nhập liệu và về lại Menu.");
                System.out.println("- Gửi 'EXIT.' để ngắt kết nối hoàn toàn.\n");

                while (true) {
                    System.out.print("Nhập chuỗi: ");
                    String inputLine = scanner.nextLine();

                    dos.writeUTF(inputLine);
                    dos.flush();

                    String response = dis.readUTF();
                    System.out.println("Server phản hồi: " + response);

                    if (inputLine.equalsIgnoreCase("EXIT.")) {
                        return;
                    }

                    if (inputLine.trim().equals(".")) {
                        break;
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Lỗi kết nối Client: " + e.getMessage());
        }
    }
}