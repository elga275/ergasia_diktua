public class Message {
    private boolean isRead;
    private String sender;
    private String receiver;
    private String body;
    private int messageID;

    public Message(boolean isRead, String sender, String receiver, String body, int messageID){
        this.isRead=isRead;
        this.sender=sender;
        this.receiver=receiver;
        this.body=body;
        this.messageID=messageID;
    }



    //getters
    public boolean getIsRead() {
        return isRead;
    }

    public String getSender() {
        return sender;
    }

    public String getBody() {
        return body;
    }

    public int getMessageID() {
        return messageID;
    }

    //setters
    public void setIsRead(boolean read) {
        isRead = read;
    }
}