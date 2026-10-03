import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UdpRequestUpperTest {

    public static String requestUpper(DatagramSocket socket, InetAddress address, int port, String message) throws IOException {
        byte[] sendBuffer = message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
        socket.send(sendPacket);

        byte[] receiveBuffer = new byte[65535];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        socket.receive(receivePacket);

        return new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength(), StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        try (Fake socket = new Fake()) {
            InetAddress address = InetAddress.getLoopbackAddress();
            String result = requestUpper(socket, address, 5003, "hello");

            DatagramPacket p = socket.sent;
            String actual = new String(p.getData(), p.getOffset(), p.getLength(), StandardCharsets.UTF_8);

            System.out.println(actual.equals("hello") && p.getAddress().equals(address)
                    && p.getPort() == 5003 && socket.sends == 1 ? "REQUEST=OK" : "REQUEST=BAD");
            System.out.println(result.equals("HELLO") && socket.receives == 1 ? "REPLY=OK" : "REPLY=BAD");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static class Fake extends DatagramSocket {
        DatagramPacket sent;
        int sends, receives;

        Fake() throws SocketException {
            super((java.net.SocketAddress) null);
        }

        @Override
        public void send(DatagramPacket p) {
            sent = p;
            sends++;
        }

        @Override
        public void receive(DatagramPacket p) {
            byte[] b = "HELLO".getBytes(StandardCharsets.UTF_8);
            byte[] padded = new byte[b.length + 4];
            System.arraycopy(b, 0, padded, 2, b.length);
            p.setData(padded, 2, b.length);
            receives++;
        }
    }
}