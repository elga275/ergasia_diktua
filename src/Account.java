import java.util.*;

public class Account {
    private String username;
    private int authToken;
    private List<Message> messageBox;

    public Account(String us, int t){
        username=us;
        authToken=t;
        messageBox= Collections.synchronizedList(new ArrayList<>());
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
}
