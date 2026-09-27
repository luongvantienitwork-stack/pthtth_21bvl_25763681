package bt;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Bai9_MulticastSender {
    private static final String MULTICAST_ADDRESS = "239.255.0.1";
    private static final int PORT = 9991;

    public static void main(String[] args) {
        try (
            DatagramSocket socket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in)
        ) {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            System.out.println("=== MULTICAST SENDER ===");
            System.out.println("Gửi thông báo tới địa chỉ: " + MULTICAST_ADDRESS + ":" + PORT);
            System.out.println("Nhập nội dung thông báo (Nhập 'QUIT' để thoát):");

            while (true) {
                System.out.print("> ");
                String message = scanner.nextLine();

                if ("QUIT".equalsIgnoreCase(message.trim())) {
                    break;
                }

                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                socket.send(packet);
                System.out.println("Đã phát tin multicast thành công.");
            }
        } catch (IOException e) {
            System.err.println("Lỗi Sender: " + e.getMessage());
        }
    }
}