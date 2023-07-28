import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Room {

    private Map<Integer, User> userMap = Collections.synchronizedMap(new HashMap<>());

    public Boolean isRoomEmpty() {
        return userMap.isEmpty();
    }

    public void joinUser(User user){
        userMap.put(user.getId(), user);
    }

    public void leaveUser(User user) {
        userMap.remove(user.getId());
    }

    public void broadcast(String chatJson){
        for (Integer key : userMap.keySet()) {
            PrintWriter writer = userMap.get(key).getWriter();
            writer.println(chatJson);
            writer.flush();
        }
    }
}