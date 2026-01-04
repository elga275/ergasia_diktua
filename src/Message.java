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

    public String getReceiver() {
        return receiver;
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

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }
}