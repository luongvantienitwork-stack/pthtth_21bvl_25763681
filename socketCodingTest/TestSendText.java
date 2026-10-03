import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class TestSendText {

    public static void sendText(DatagramSocket socket, InetAddress address, int port, String message) throws IOException {
        byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
    }

    public static void main(String[] args) {
        try {
            class Fake extends DatagramSocket {
                DatagramPacket sent;
                int count;

                Fake() throws SocketException {
                    super((java.net.SocketAddress) null);
                }

                @Override
                public void send(DatagramPacket p) {
                    sent = p;
                    count++;
                }
            }

            Fake socket = new Fake();
            InetAddress target = InetAddress.getLoopbackAddress();
            sendText(socket, target, 5001, "Hello UDP");

            String actual = new String(socket.sent.getData(), socket.sent.getOffset(), socket.sent.getLength(), StandardCharsets.UTF_8);

            System.out.println(actual.equals("Hello UDP") ? "TEXT=OK" : "TEXT=BAD");
            System.out.println(socket.sent.getAddress().equals(target) && socket.sent.getPort() == 5001 && socket.count == 1 ? "TARGET=OK" : "TARGET=BAD");

            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}