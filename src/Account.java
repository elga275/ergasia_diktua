import java.util.ArrayList;
import java.util.List;

public class Account {
    private String username;
    private int authToken;
    private List<Message> messageBox;

    public Account(String us, int t, List<Message> mb){
        username=us;
        authToken=t;
        messageBox=mb;
    }

    //getters
    public String getUsername() {
        return username;
    }

    public int getAuthToken() {
        return authToken;
    }

    public List<Message> getMessageBox() {
        return messageBox;
    }


    //setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthToken(int authToken) {
        this.authToken = authToken;
    }

    public void setMessageBox(List<Message> messageBox) {
        this.messageBox = messageBox;
    }
}
