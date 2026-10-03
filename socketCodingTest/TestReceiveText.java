
public class TestReceiveText {

    public static String receiveText(java.net.Socket socket) throws java.io.IOException {
        java.io.DataInputStream dis = new java.io.DataInputStream(socket.getInputStream());
        return dis.readUTF();
    }

    public static void main(String[] args) {
        try {
            java.io.ByteArrayOutputStream bytes = new java.io.ByteArrayOutputStream();
            new java.io.DataOutputStream(bytes).writeUTF("Client A");

            java.net.Socket socket = new java.net.Socket() {
                @Override
                public java.io.InputStream getInputStream() {
                    return new java.io.ByteArrayInputStream(bytes.toByteArray());
                }
            };

            System.out.println(receiveText(socket).equals("Client A") ? "MATCH" : "MISMATCH");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}