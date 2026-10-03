import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TestRequestDouble {

    public static int requestDouble(Socket socket, int value) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(value);
        dos.flush();
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        return dis.readInt();
    }

    public static void main(String[] args) {
        try {
            ByteArrayOutputStream outgoing = new ByteArrayOutputStream();
            ByteArrayOutputStream incoming = new ByteArrayOutputStream();
            new DataOutputStream(incoming).writeInt(14);

            Socket socket = new Socket() {
                @Override
                public OutputStream getOutputStream() {
                    return outgoing;
                }

                @Override
                public InputStream getInputStream() {
                    return new ByteArrayInputStream(incoming.toByteArray());
                }
            };

            int reply = requestDouble(socket, 7);
            DataInputStream sent = new DataInputStream(new ByteArrayInputStream(outgoing.toByteArray()));

            System.out.println("SENT=" + sent.readInt() + ";REPLY=" + reply);
            System.out.println(sent.available() == 0 ? "OK" : "EXTRA_DATA");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}