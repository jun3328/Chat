import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatHandler extends Thread {

    private RoomManager roomManager = RoomManager.getInstance();
    private Gson gson = new Gson();

    private User user;
    private PrintWriter writer;
    private BufferedReader reader;

    public ChatHandler(Socket socket) {
        try {
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String chatJson = reader.readLine();
                if (chatJson == null) break;
                handleMessage(gson.fromJson(chatJson, ChatMessage.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(ChatMessage chatMessage) {
        System.out.println(chatMessage);
        int roomID = chatMessage.getRoomID();
        switch (chatMessage.getEvent()) {
            case ChatEvent.CONNECT:
                user = chatMessage.getSender();
                user.setWriter(writer);
                roomManager.initRoomUser(user);
                break;
            case ChatEvent.JOIN:
                if (user == null) return;
                roomManager.joinRoom(roomID, user);
                break;
            case ChatEvent.LEAVE:
                if (user == null) return;
                roomManager.leaveRoom(roomID, user);
                break;
            case ChatEvent.BROADCAST:
                if (user == null) return;
                roomManager.broadcast(roomID, gson.toJson(chatMessage));
                break;
            default:
                break;
        }
    }
}