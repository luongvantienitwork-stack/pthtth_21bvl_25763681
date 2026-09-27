package bt;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Bai9_MulticastReceiver {
    private static final String MULTICAST_ADDRESS = "239.255.0.1";
    private static final int PORT = 9991;

    public static void main(String[] args) {
        MulticastSocket socket = null;
        InetSocketAddress groupAddress = null;
        NetworkInterface netIf = null;

        try {
            socket = new MulticastSocket(PORT);
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            groupAddress = new InetSocketAddress(group, PORT);

            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                if (ni.isUp() && ni.supportsMulticast() && !ni.getName().startsWith("docker") && !ni.getName().startsWith("veth")) {
                    netIf = ni;
                    break;
                }
            }

            if (netIf == null) {
                netIf = NetworkInterface.getNetworkInterfaces().nextElement();
            }

            socket.joinGroup(groupAddress, netIf);
            System.out.println("=== MULTICAST RECEIVER ===");
            System.out.println("Đã tham gia nhóm multicast " + MULTICAST_ADDRESS + " trên card mạng: " + netIf.getDisplayName());
            System.out.println("Đang chờ nhận thông báo...\n");

            byte[] buffer = new byte[1024];

            while (!socket.isClosed()) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String receivedMsg = new String(packet.getData(), 0, packet.getLength());
                System.out.println("[" + packet.getSocketAddress() + "]: " + receivedMsg);
            }

        } catch (IOException e) {
            System.out.println("Đã ngắt kết nối Multicast.");
        } finally {
            if (socket != null && groupAddress != null && netIf != null) {
                try {
                    if (!socket.isClosed()) {
                        socket.leaveGroup(groupAddress, netIf);
                        System.out.println("Đã rời khỏi nhóm Multicast an toàn.");
                        socket.close();
                    }
                } catch (IOException e) {
                    System.err.println("Lỗi khi rời nhóm Multicast: " + e.getMessage());
                }
            }
        }
    }
}