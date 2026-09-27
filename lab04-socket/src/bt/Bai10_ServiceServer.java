package bt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class Bai10_ServiceServer {
    private static final int UDP_PORT = 9990;
    private static final int TCP_PORT = 8888;
    private static final String SERVICE_NAME = "FileService";
    private static final String VERSION = "v1.0";

    public static void main(String[] args) {
        new Thread(() -> startTcpServer()).start();

        try (DatagramSocket udpSocket = new DatagramSocket(UDP_PORT)) {
            System.out.println("=== UDP DISCOVERY SERVER ===");
            System.out.println("Đang lắng nghe yêu cầu Discovery trên cổng UDP " + UDP_PORT + "...");

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                udpSocket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength()).trim();
                System.out.println("Nhận UDP request từ " + packet.getSocketAddress() + ": " + message);

                if ("DISCOVER_SERVICE".equals(message)) {
                    String responseStr = "SERVICE " + SERVICE_NAME + " " + TCP_PORT + " " + VERSION;
                    byte[] sendData = responseStr.getBytes();

                    DatagramPacket replyPacket = new DatagramPacket(
                        sendData, sendData.length,
                        packet.getAddress(), packet.getPort()
                    );
                    udpSocket.send(replyPacket);
                    System.out.println("Đã gửi phản hồi: " + responseStr);
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi UDP Server: " + e.getMessage());
        }
    }

    private static void startTcpServer() {
        try (ServerSocket serverSocket = new ServerSocket(TCP_PORT)) {
            System.out.println("=== TCP SERVICE SERVER ===");
            System.out.println("TCP Service đang lắng nghe trên cổng " + TCP_PORT + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleTcpClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Lỗi TCP Server: " + e.getMessage());
        }
    }

    private static void handleTcpClient(Socket socket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            System.out.println("Đã kết nối TCP thành công với Client: " + socket.getRemoteSocketAddress());
            out.println("Chào mừng bạn đã kết nối tới " + SERVICE_NAME + " (" + VERSION + ")");

            String line;
            while ((line = in.readLine()) != null) {
                if ("QUIT".equalsIgnoreCase(line.trim())) {
                    out.println("Goodbye!");
                    break;
                }
                out.println("ECHO từ Server: " + line);
            }
        } catch (IOException e) {
            System.err.println("Lỗi xử lý TCP Client: " + e.getMessage());
        }
    }
}