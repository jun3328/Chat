import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args) throws IOException {

        System.out.println("채팅서버를 시작합니다.");

        RoomManager.getInstance().initRoom();

        ServerSocket serverSocket = new ServerSocket(8798);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("소켓 연결됨 " + socket);
            new ChatHandler(socket).start();
        }
    }
}