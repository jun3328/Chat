public class ChatMessage {
    private int event;
    private int roomID;
    private int messageType;
    private String message;
    private User sender;
    private long time;

    public ChatMessage(int event, int roomID, int messageType, String message, User sender, long time) {
        this.event = event;
        this.roomID = roomID;
        this.messageType = messageType;
        this.message = message;
        this.sender = sender;
        this.time = time;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "event=" + event +
                ", roomID=" + roomID +
                ", messageType=" + messageType +
                ", message='" + message + '\'' +
                ", sender=" + sender +
                ", time=" + time +
                '}';
    }
}
