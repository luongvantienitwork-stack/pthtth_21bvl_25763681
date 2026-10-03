
public class TestSendTextSocket {

    public static void sendText(java.net.Socket socket, String message) throws java.io.IOException {
        java.io.DataOutputStream dos = new java.io.DataOutputStream(socket.getOutputStream());
        dos.writeUTF(message);
        dos.flush();
    }

    public static void main(String[] args) {
        try {
            java.io.ByteArrayOutputStream bytes = new java.io.ByteArrayOutputStream();
            java.net.Socket socket = new java.net.Socket() {
                @Override
                public java.io.OutputStream getOutputStream() {
                    return bytes;
                }
            };

            sendText(socket, "Hello TCP");

            java.io.DataInputStream verify = new java.io.DataInputStream(
                new java.io.ByteArrayInputStream(bytes.toByteArray())
            );

            System.out.println(verify.readUTF().equals("Hello TCP") ? "MATCH" : "MISMATCH");
            System.out.println(verify.available() == 0 ? "OK" : "EXTRA_DATA");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}