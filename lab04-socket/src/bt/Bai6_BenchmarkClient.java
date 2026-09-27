package bt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

public class Bai6_BenchmarkClient {
    private static final String HOST = "localhost";
    private static final int TCP_PORT = 9995;
    private static final int UDP_PORT = 9994;
    private static final int MESSAGE_COUNT = 1000;
    private static final int MESSAGE_SIZE = 1024;
    private static final int TIMEOUT_MS = 2000; 
    private static final int TEST_ROUNDS = 5;   

    public static void main(String[] args) {
        byte[] payload = new byte[MESSAGE_SIZE];
        Arrays.fill(payload, (byte) 'A'); 

        System.out.println("=== BẮT ĐẦU THỰC NGHIỆM SO SÁNH TCP VÀ UDP ===");
        System.out.println("Tổng số thông điệp mỗi lần: " + MESSAGE_COUNT);
        System.out.println("Kích thước mỗi thông điệp: " + MESSAGE_SIZE + " bytes");
        System.out.println("Môi trường: localhost\n");

        long[] tcpTimes = new long[TEST_ROUNDS];
        long[] udpTimes = new long[TEST_ROUNDS];
        int[] udpReceivedCounts = new int[TEST_ROUNDS];

        System.out.println("--- KẾT QUẢ THỬ NGHIỆM TCP ---");
        for (int round = 1; round <= TEST_ROUNDS; round++) {
            long startTime = System.currentTimeMillis();
            int received = testTcp(payload);
            long totalTime = System.currentTimeMillis() - startTime;
            
            tcpTimes[round - 1] = totalTime;
            System.out.printf("Lần %d: Thời gian = %d ms | Phản hồi = %d/%d\n", round, totalTime, received, MESSAGE_COUNT);
        }

        System.out.println("\n--- KẾT QUẢ THỬ NGHIỆM UDP ---");
        for (int round = 1; round <= TEST_ROUNDS; round++) {
            long startTime = System.currentTimeMillis();
            int received = testUdp(payload);
            long totalTime = System.currentTimeMillis() - startTime;

            udpTimes[round - 1] = totalTime;
            udpReceivedCounts[round - 1] = received;
            System.out.printf("Lần %d: Thời gian = %d ms | Phản hồi = %d/%d\n", round, totalTime, received, MESSAGE_COUNT);
        }

        printSummary(tcpTimes, udpTimes, udpReceivedCounts);
    }

    private static int testTcp(byte[] payload) {
        int count = 0;
        try (
            Socket socket = new Socket(HOST, TCP_PORT);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream()
        ) {
            byte[] buffer = new byte[MESSAGE_SIZE];
            for (int i = 0; i < MESSAGE_COUNT; i++) {
                out.write(payload);
                out.flush();

                int totalRead = 0;
                while (totalRead < MESSAGE_SIZE) {
                    int read = in.read(buffer, totalRead, MESSAGE_SIZE - totalRead);
                    if (read == -1) break;
                    totalRead += read;
                }
                if (totalRead == MESSAGE_SIZE) {
                    count++;
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi TCP Client: " + e.getMessage());
        }
        return count;
    }

    private static int testUdp(byte[] payload) {
        int count = 0;
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(TIMEOUT_MS);
            InetAddress address = InetAddress.getByName(HOST);
            byte[] receiveBuffer = new byte[MESSAGE_SIZE];

            for (int i = 0; i < MESSAGE_COUNT; i++) {
                DatagramPacket sendPacket = new DatagramPacket(payload, payload.length, address, UDP_PORT);
                socket.send(sendPacket);

                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                try {
                    socket.receive(receivePacket);
                    count++;
                } catch (SocketTimeoutException e) {
                    
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi UDP Client: " + e.getMessage());
        }
        return count;
    }

    private static void printSummary(long[] tcpTimes, long[] udpTimes, int[] udpCounts) {
        double avgTcpTime = Arrays.stream(tcpTimes).average().orElse(0);
        double avgUdpTime = Arrays.stream(udpTimes).average().orElse(0);
        double avgUdpReceived = Arrays.stream(udpCounts).average().orElse(0);

        System.out.println("\n================ BÁO CÁO TỔNG HỢP ================");
        System.out.printf("Thời gian trung bình TCP : %.2f ms (Độ tin cậy: 100%%)\n", avgTcpTime);
        System.out.printf("Thời gian trung bình UDP : %.2f ms (Phản hồi trung bình: %.1f/%d)\n", avgUdpTime, avgUdpReceived, MESSAGE_COUNT);
        System.out.println("==================================================");
    }
}