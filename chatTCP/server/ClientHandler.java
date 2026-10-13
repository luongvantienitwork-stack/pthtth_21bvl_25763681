package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import model.Room;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String username;
    private Room currentRoom;
    private ChatServer server;

    public ClientHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            writer.println("SUBMIT_NAME");
            username = reader.readLine();
            if (username == null || username.trim().isEmpty()) return;
            
            System.out.println("Client kết nối: " + username);

            String line;
            while (true) {
                if (currentRoom == null) {
                    writer.println("ROOM_MENU:" + String.join(",", server.getRoomNames()));
                    line = reader.readLine();
                    if (line == null) break;
                    line = line.trim();

                    if (line.equalsIgnoreCase("/exit")) {
                        break;
                    } else if (!line.isEmpty()) {
                        server.joinRoom(this, line);
                        writer.println("IN_ROOM:" + currentRoom.getRoomName());
                    }
                } else {
                    line = reader.readLine();
                    if (line == null) break;
                    line = line.trim();

                    if (line.equalsIgnoreCase("/exit")) {
                        break;
                    } else if (line.equalsIgnoreCase("/quit")) {
                        server.leaveRoom(this);
                        writer.println("LEFT_ROOM");
                    } else if (!line.isEmpty()) {
                        server.broadcastToRoom(currentRoom, username + ": " + line, this);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi kết nối với client: " + username);
        } finally {
            server.leaveRoom(this);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Client ngắt kết nối: " + username);
        }
    }

    public void sendMessage(String msg) {
        writer.println(msg);
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public String getUsername() {
        return username;
    }
}