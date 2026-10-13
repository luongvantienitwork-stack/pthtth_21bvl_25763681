package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import model.Room;

public class ChatServer {
    private static final int PORT = 7000;
    private ConcurrentHashMap<String, Room> rooms = new ConcurrentHashMap<>();

    public void start() {
        rooms.put("Study", new Room("Study"));
        rooms.put("Game", new Room("Game"));
        rooms.put("TraiNghiem", new Room("TraiNghiem"));

        System.out.println("Server đang chạy trên cổng " + PORT + "...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, this);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getRoomNames() {
        return rooms.keySet();
    }

    public synchronized void joinRoom(ClientHandler client, String roomName) {
        leaveRoom(client);
        rooms.putIfAbsent(roomName, new Room(roomName));
        Room room = rooms.get(roomName);
        room.addClient(client);
        client.setCurrentRoom(room);
        broadcastToRoom(room, "[Hệ thống]: " + client.getUsername() + " đã vào phòng.", client);
    }

    public synchronized void leaveRoom(ClientHandler client) {
        Room room = client.getCurrentRoom();
        if (room != null) {
            room.removeClient(client);
            broadcastToRoom(room, "[Hệ thống]: " + client.getUsername() + " đã rời phòng.", client);
            client.setCurrentRoom(null);
        }
    }

    public void broadcastToRoom(Room room, String message, ClientHandler sender) {
        for (ClientHandler client : room.getClients()) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public static void main(String[] args) {
        new ChatServer().start();
    }
}