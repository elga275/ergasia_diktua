import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class MessagingServer {
    private int counter;
    private ArrayList<Account> accounts;

    public MessagingServer(){
        counter=0;
        accounts = new ArrayList<>();
    }

    public String createAccount(String username){
        //check if username is invalid
        for (char c : username.toLowerCase().toCharArray()){
            if (!((c>='a' && c<='z') || (c=='-'))){
                return "Invalid Username";
            }
        }

        //check if user with this username already exists
        for (Account a : accounts){
            if (username.equals(a.getUsername())){
                return "Sorry, the user already exists.";
            }
        }

        Account acc = new Account(username,counter,new ArrayList<Message>());
        accounts.add(acc);
        counter++;

        return String.valueOf(counter);
    }

    public void showAccounts(){
        int num=1;
        for (Account a : accounts){
            System.out.println(num + ". " + a.getUsername());
            num++;
        }
    }

    public String sendMessage(String ip, int port, int authToken, String username, String message){
        //check if sender's authToken is valid
        boolean flag1=false;
        for (Account a : accounts){
            if (a.getAuthToken()==authToken){
                flag1=true;
                break;
            }
        }
        if (!flag1){
            return "Invalid authorization token";
        }


        //check if recipient's username exists
        boolean flag2=false;
        Account acc = null;
        for (Account a : accounts){
            if (username.equals(a.getUsername())){
                flag2=true;
                acc=a;
                break;
            }
        }
        if (!flag2){
            return "User does not exist";
        }

        //send the message
        Message m = new Message (false,acc.getUsername(), username, message);
        acc.getMessageBox().add(m);
        return "OK";
    }

    public static void main(String[] args) throws IOException {
        MessagingServer server = new MessagingServer();
        String ip = args[0];
        int port = Integer.parseInt(args[1]);

        ServerSocket socket = null;

        try {
            socket = new ServerSocket(port);
            System.out.println("Server is now running @ " + ip);
            while (true) {
                Socket clientConnection = socket.accept();
                ServerThread st = new ServerThread(clientConnection, server);
                st.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (!socket.isClosed())
                socket.close();
        }
    }

}
