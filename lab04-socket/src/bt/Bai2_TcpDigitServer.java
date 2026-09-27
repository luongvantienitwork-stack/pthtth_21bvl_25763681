package bt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Bai2_TcpDigitServer {
    private static final int PORT = 5000;
    private static final String[] DIGIT_NAMES = {
        "khong", "mot", "hai", "ba", "bon", "nam", "sau", "bay", "tam", "chin"
    };

    public static void main(String[] args) {
        System.out.println("TCP Digit Server dang lang nghe tai port " + PORT + "...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client ket noi tu: " + clientSocket.getRemoteSocketAddress());
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Loi Server: " + e.getMessage());
        }
    }

    private static void handleClient(Socket socket) {
        try (
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))
        ) {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                if ("QUIT".equalsIgnoreCase(inputLine.trim())) {
                    System.out.println("Client yeu cau ngat ket noi.");
                    break;
                }

                String response = processDigit(inputLine);
                writer.write(response);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            System.err.println("Loi ket noi client: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    private static String processDigit(String input) {
        if (input == null || input.length() != 1) {
            return "ERR INVALID_DIGIT";
        }
        char ch = input.charAt(0);
        if (ch >= '0' && ch <= '9') {
            return DIGIT_NAMES[ch - '0'];
        }
        return "ERR INVALID_DIGIT";
    }
}