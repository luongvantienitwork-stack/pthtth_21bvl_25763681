package bt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bai5_TcpChatServer {
    private static final int PORT = 9996;
    private static final Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        System.out.println("Chat TCP Server đang chạy trên cổng " + PORT + "...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                threadPool.execute(new ClientHandler(socket));
            }
        } catch (IOException e) {
            System.err.println("Lỗi Server: " + e.getMessage());
        } finally {
            threadPool.shutdown();
        }
    }

    public static void broadcast(String message, String senderNickname) {
        for (Map.Entry<String, ClientHandler> entry : clients.entrySet()) {
            if (senderNickname == null || !entry.getKey().equalsIgnoreCase(senderNickname)) {
                entry.getValue().sendMessage(message);
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private BufferedReader reader;
        private PrintWriter writer;
        private String nickname;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    writer.println("SUBMIT_NICKNAME");
                    String inputNick = reader.readLine();
                    if (inputNick == null) return;

                    inputNick = inputNick.trim();
                    if (inputNick.isEmpty() || inputNick.contains(" ")) {
                        writer.println("ERR_NICKNAME Nickname không được trống hoặc chứa khoảng trắng!");
                        continue;
                    }

                    if (clients.putIfAbsent(inputNick.toLowerCase(), this) == null) {
                        this.nickname = inputNick;
                        writer.println("NICKNAME_ACCEPTED " + nickname);
                        System.out.println("[Server LOG] " + nickname + " đã kết nối.");
                        broadcast("[Hệ thống] " + nickname + " đã tham gia phòng chat!", nickname);
                        break;
                    } else {
                        writer.println("ERR_NICKNAME Nickname đã tồn tại, vui lòng chọn tên khác!");
                    }
                }

                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    if ("QUIT".equalsIgnoreCase(line)) {
                        writer.println("BYE");
                        break;
                    } else if ("USERS".equalsIgnoreCase(line)) {
                        writer.println("[Hệ thống] Các user online: " + String.join(", ", clients.keySet()));
                    } else if (line.toUpperCase().startsWith("MSG ")) {
                        String msgContent = line.substring(4).trim();
                        if (!msgContent.isEmpty()) {
                            broadcast("[" + nickname + "]: " + msgContent, nickname);
                        }
                    } else {
                        writer.println("[Hệ thống] Lệnh không hợp lệ! Dùng: MSG <nội dung> | USERS | QUIT");
                    }
                }

            } catch (IOException e) {
                System.err.println("Client " + (nickname != null ? nickname : socket.getInetAddress()) + " ngắt kết nối.");
            } finally {
                if (nickname != null) {
                    clients.remove(nickname.toLowerCase());
                    System.out.println("[Server LOG] " + nickname + " đã ngắt kết nối.");
                    broadcast("[Hệ thống] " + nickname + " đã rời phòng chat.", null);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Lỗi đóng socket: " + e.getMessage());
                }
            }
        }

        public void sendMessage(String msg) {
            if (writer != null) {
                writer.println(msg);
            }
        }
    }
}