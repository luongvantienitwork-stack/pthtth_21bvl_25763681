package bt;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Bai3_UdpDateTimeServer {
    private static final int PORT = 9998;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MM yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH mm ss");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd MM yyyy HH mm ss");

    public static void main(String[] args) {
        System.out.println("UDP Server đang chạy trên cổng " + PORT + "...");
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivePacket);

                String command = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim().toUpperCase();
                String response = processCommand(command);

                byte[] sendData = response.getBytes();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                socket.send(sendPacket);
            }
        } catch (IOException e) {
            System.err.println("Lỗi UDP Server: " + e.getMessage());
        }
    }

    private static String processCommand(String command) {
        return switch (command) {
            case "DATE" -> LocalDate.now().format(DATE_FORMATTER);
            case "TIME" -> LocalTime.now().format(TIME_FORMATTER);
            case "DATETIME" -> LocalDateTime.now().format(DATETIME_FORMATTER);
            default -> "ERROR: Lệnh không hợp lệ (Dùng DATE, TIME, DATETIME)";
        };
    }
}