package bt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bai6_BenchmarkServer {
    private static final int TCP_PORT = 9995;
    private static final int UDP_PORT = 9994;
    private static final int BUFFER_SIZE = 1024; 

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        executor.execute(() -> {
            try (ServerSocket serverSocket = new ServerSocket(TCP_PORT)) {
                System.out.println("TCP Benchmark Server đang lắng nghe ở cổng " + TCP_PORT);
                while (true) {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> handleTcpClient(socket)).start();
                }
            } catch (IOException e) {
                System.err.println("Lỗi TCP Server: " + e.getMessage());
            }
        });

        executor.execute(() -> {
            try (DatagramSocket socket = new DatagramSocket(UDP_PORT)) {
                System.out.println("UDP Benchmark Server đang lắng nghe ở cổng " + UDP_PORT);
                byte[] buffer = new byte[BUFFER_SIZE];
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    
                    DatagramPacket responsePacket = new DatagramPacket(
                        packet.getData(), packet.getLength(), packet.getAddress(), packet.getPort()
                    );
                    socket.send(responsePacket);
                }
            } catch (IOException e) {
                System.err.println("Lỗi UDP Server: " + e.getMessage());
            }
        });
    }

    private static void handleTcpClient(Socket socket) {
        try (
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream()
        ) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                out.flush();
            }
        } catch (IOException e) {
            
        }
    }
}