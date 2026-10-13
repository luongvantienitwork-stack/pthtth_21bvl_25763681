package model;

import server.ClientHandler;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Room {
    private String roomName;
    private Set<ClientHandler> clients;

    public Room(String roomName) {
        this.roomName = roomName;
        this.clients = ConcurrentHashMap.newKeySet();
    }

    public String getRoomName() {
        return roomName;
    }

    public Set<ClientHandler> getClients() {
        return clients;
    }

    public void addClient(ClientHandler client) {
        clients.add(client);
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}