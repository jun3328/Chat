import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RoomManager {

    private static RoomManager instance;
    private MySQL mariaDb = MySQL.getInstance();
    private Map<Integer, Room> roomMap = Collections.synchronizedMap(new HashMap<>());

    private RoomManager() { }

    public static synchronized RoomManager getInstance() {
        if (instance == null) {
            instance = new RoomManager();
        }
        return instance;
    }

    // 채팅방 초기화
    public void initRoom() {
        PreparedStatement pstmt = mariaDb.getPstmt("SELECT * FROM chat_room");

        try {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                int roomID = resultSet.getInt("id");
                createRoom(roomID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 채팅방 유저 초기화
    public void initRoomUser(User user) {
        PreparedStatement pstmt = mariaDb.getPstmt("SELECT * FROM chat_user WHERE user_id = ?");
        try {
            pstmt.setInt(1, user.getId());
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                int roomID = resultSet.getInt("room_id");
                joinRoom(roomID, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 채팅방 생성
    public void createRoom(int roomID) {
        System.out.println("채팅방 생성 : 방id = " + roomID);
        roomMap.put(roomID, new Room());
    }

    // 채팅방 삭제
    public void destroyRoom(int roomID) {
        System.out.println("채팅방 삭제 : 방id = " + roomID);
        roomMap.remove(roomID);
    }

    // 채팅방에 유저 입장
    public void joinRoom(int roomID, User user) {
        if (!isExistRoom(roomID)) {
            createRoom(roomID); // 채팅방이 없으면 생성
        }
        roomMap.get(roomID).joinUser(user);
    }

    // 채팅방에 유저 퇴장
    public void leaveRoom(int roomID, User user) {
        roomMap.get(roomID).leaveUser(user);
        if (roomMap.get(roomID).isRoomEmpty()) {
            destroyRoom(roomID); // 채팅방에 유저가 없으면 방 삭제
        }
    }

    // 채팅방 모든 유저에게 메시지 전송
    public void broadcast(int roomID, String message) {
        roomMap.get(roomID).broadcast(message);
    }

    // 채팅방의 존재유무
    private Boolean isExistRoom(int roomID) {
        return roomMap.containsKey(roomID);
    }
}