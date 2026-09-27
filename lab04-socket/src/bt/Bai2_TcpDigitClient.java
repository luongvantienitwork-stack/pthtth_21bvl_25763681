package bt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Bai2_TcpDigitClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)
        ) {
            System.out.println("Da ket noi toi Server. Nhap chu so (0-9) hoac 'QUIT' de thoat:");

            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine();

                out.write(input);
                out.newLine();
                out.flush();

                if ("QUIT".equalsIgnoreCase(input.trim())) {
                    break;
                }

                String response = in.readLine();
                System.out.println("Server: " + response);
            }
        } catch (IOException e) {
            System.err.println("Loi Client: " + e.getMessage());
        }
    }
}