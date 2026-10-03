import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TestReceiveTextUDP {

    public static String receiveText(DatagramSocket socket) throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        return new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        try {
            class Fake extends DatagramSocket {
                int count;

                Fake() throws SocketException {
                    super((java.net.SocketAddress) null);
                }

                @Override
                public void receive(DatagramPacket p) {
                    byte[] actual = ("UDP").getBytes(StandardCharsets.UTF_8);
                    byte[] padded = new byte[actual.length + 0 + 2];
                    Arrays.fill(padded, (byte) 'X');
                    System.arraycopy(actual, 0, padded, 0, actual.length);
                    p.setData(padded, 0, actual.length);
                    count++;
                }
            }

            Fake socket = new Fake();
            String result = receiveText(socket);

            System.out.println(result.equals("UDP") ? "TEXT=OK" : "TEXT=BAD");
            System.out.println(socket.count == 1 ? "RECEIVE=1" : "RECEIVE=BAD");

            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}