package bt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Bai10_ServiceClient {
    private static final int DISCOVERY_PORT = 9990;
    private static final int TIMEOUT_MS = 3000;

    static class DiscoveredServer {
        InetAddress ipAddress;
        String serviceName;
        int tcpPort;
        String version;

        public DiscoveredServer(InetAddress ipAddress, String serviceName, int tcpPort, String version) {
            this.ipAddress = ipAddress;
            this.serviceName = serviceName;
            this.tcpPort = tcpPort;
            this.version = version;
        }

        @Override
        public String toString() {
            return serviceName + " (" + version + ") tại " + ipAddress.getHostAddress() + ":" + tcpPort;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== UDP DISCOVERY CLIENT ===");
        List<DiscoveredServer> serverList = discoverServices();

        if (serverList.isEmpty()) {
            System.out.println("Không tìm thấy Server nào trong mạng!");
            return;
        }

        System.out.println("\n--- DANH SÁCH SERVER TÌM THẤY ---");
        for (int i = 0; i < serverList.size(); i++) {
            System.out.println((i + 1) + ". " + serverList.get(i));
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nChọn Server để kết nối TCP (1 - " + serverList.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > serverList.size()) {
            System.out.println("Lựa chọn không hợp lệ!");
            return;
        }

        DiscoveredServer selectedServer = serverList.get(choice - 1);
        connectTcpService(selectedServer, scanner);
    }

    private static List<DiscoveredServer> discoverServices() {
        List<DiscoveredServer> servers = new ArrayList<>();
        Set<String> uniqueKeys = new HashSet<>();

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);
            socket.setSoTimeout(TIMEOUT_MS);

            byte[] requestData = "DISCOVER_SERVICE".getBytes();
            InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
            DatagramPacket packet = new DatagramPacket(requestData, requestData.length, broadcastAddress, DISCOVERY_PORT);

            System.out.println("Đang phát broadcast 'DISCOVER_SERVICE' (Timeout: " + (TIMEOUT_MS / 1000) + "s)...");
            socket.send(packet);

            byte[] buffer = new byte[1024];

            while (true) {
                try {
                    DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
                    socket.receive(responsePacket);

                    String msg = new String(responsePacket.getData(), 0, responsePacket.getLength()).trim();
                    String[] parts = msg.split(" ");

                    if (parts.length == 4 && "SERVICE".equals(parts[0])) {
                        InetAddress serverIp = responsePacket.getAddress();
                        String serviceName = parts[1];
                        int tcpPort = Integer.parseInt(parts[2]);
                        String version = parts[3];

                        String uniqueKey = serverIp.getHostAddress() + ":" + tcpPort;
                        if (!uniqueKeys.contains(uniqueKey)) {
                            uniqueKeys.add(uniqueKey);
                            servers.add(new DiscoveredServer(serverIp, serviceName, tcpPort, version));
                        }
                    }
                } catch (SocketTimeoutException e) {
                    System.out.println("Hết thời gian chờ phản hồi Discovery.");
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi Discovery: " + e.getMessage());
        }

        return servers;
    }

    private static void connectTcpService(DiscoveredServer server, Scanner scanner) {
        System.out.println("\nĐang kết nối tới TCP Server " + server.ipAddress.getHostAddress() + ":" + server.tcpPort + "...");
        try (
            Socket tcpSocket = new Socket(server.ipAddress, server.tcpPort);
            BufferedReader in = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
            PrintWriter out = new PrintWriter(tcpSocket.getOutputStream(), true)
        ) {
            System.out.println("Phản hồi từ TCP Server: " + in.readLine());
            System.out.println("Nhập dữ liệu gửi tới TCP Server (Gõ 'QUIT' để thoát):");

            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine();
                out.println(input);

                if ("QUIT".equalsIgnoreCase(input.trim())) {
                    break;
                }

                String response = in.readLine();
                System.out.println("Server trả lời: " + response);
            }
        } catch (IOException e) {
            System.err.println("Lỗi kết nối TCP: " + e.getMessage());
        }
    }
}