package bt;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Bai3_UdpDateTimeClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9998;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(); Scanner scanner = new Scanner(System.in)) {
            socket.setSoTimeout(5000);
            InetAddress serverAddress = InetAddress.getByName(SERVER_HOST);

            while (true) {
                System.out.print("Nhập lệnh UDP (DATE, TIME, DATETIME, exit để thoát): ");
                String command = scanner.nextLine();

                if ("exit".equalsIgnoreCase(command)) {
                    break;
                }

                byte[] sendData = command.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
                socket.send(sendPacket);

                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                try {
                    socket.receive(receivePacket);
                    String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("Phản hồi từ Server: " + response);
                } catch (SocketTimeoutException e) {
                    System.err.println("Lỗi: Server không phản hồi (Timeout).");
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi UDP Client: " + e.getMessage());
        }
    }
}