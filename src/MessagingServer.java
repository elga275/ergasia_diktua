import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MessagingServer {
    private int counter;
    private List<Account> accounts;

    public MessagingServer(){
        counter=1;
        accounts = Collections.synchronizedList(new ArrayList<>());
    }

    //generates a unique auth token for every user
    public int generateUniqueToken() {
        Random rand = new Random();
        int token;
        boolean exists;

        do {
            token = rand.nextInt(5000);
            exists = false;
            for (Account a : accounts) {
                if (a.getAuthToken() == token) {
                    exists = true;
                    break;
                }
            }
        } while (exists);

        return token;
    }



    //creates a new account
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

        Account acc = new Account(username,generateUniqueToken());
        accounts.add(acc);
        return String.valueOf(acc.getAuthToken());
    }



    //shows all the accounts that are registered in the system
    public String showAccounts(){
        StringBuilder list = new StringBuilder();
        int num=1;
        for (Account a : accounts){
            list.append(num).append(". ").append(a.getUsername()).append("\n");
            num++;
        }
        return list.toString();
    }



    //allows the user to send a message
    public String sendMessage(int authToken, String recipient, String message){
        //check if sender's authToken is valid
        Account acc1 = null;
        for (Account a : accounts){
            if (a.getAuthToken()==authToken){
                acc1=a;
                break;
            }
        }
        if (acc1==null){
            return "Invalid authorization token";
        }

        //check if recipient's username exists
        Account acc2 =null;
        for (Account a : accounts){
            if (recipient.equals(a.getUsername())){
                acc2=a;
                break;
            }
        }
        if (acc2==null){
            return "User does not exist";
        }

        //send the message
        Message m = new Message (false,acc1.getUsername(), recipient, message, counter);
        acc2.getMessageBox().add(m);
        counter++;
        return "OK";
    }



    //shows a list of all the messages in a user's inbox
    public String showInbox(int authToken){
        //check if user's authorization token is valid
        Account acc = null;
        for (Account a : accounts){
            if (a.getAuthToken()==authToken){
                acc=a;
                break;
            }
        }
        if (acc==null){
            return "Invalid authorization token";
        }

        StringBuilder list = new StringBuilder();
        for (Message m : acc.getMessageBox()){
            list.append(m.getMessageID()).append(". from: ").append(m.getSender());
            if (!m.getIsRead()){
                list.append("*");
            }
            list.append("\n");
        }
        return list.toString();
    }



    //allows the user to read a message
    public String readMessage(int authToken, int messageID){
        //check if authorization token is valid
        Account acc = null;
        for (Account a : accounts){
            if (a.getAuthToken()==authToken){
                acc=a;
                break;
            }
        }
        if (acc==null){
            return "Invalid authorization token";
        }

        //check if a message with messageID exists
        Message msgToBeRead = null;
        for (Message m : acc.getMessageBox()){
            if (m.getMessageID()==messageID){
                msgToBeRead=m;
                break;
            }
        }
        if (msgToBeRead==null){
            return "Message ID does not exist.";
        }

        //if the message exists, read it
        msgToBeRead.setIsRead(true);
        return "(" + msgToBeRead.getSender() + ") " + msgToBeRead.getBody();
    }



    //allows the user to delete a message
    public String deleteMessage(int authToken, int messageID){
        //check if authorization token is valid
        Account acc = null;
        for (Account a : accounts){
            if (a.getAuthToken()==authToken){
                acc=a;
                break;
            }
        }
        if (acc==null){
            return "Invalid authorization token";
        }

        //check if message with messageID exists
        Message msgToBeDeleted = null;
        for (Message m : acc.getMessageBox()){
            if (m.getMessageID()==messageID){
                msgToBeDeleted=m;
                break;
            }
        }
        if (msgToBeDeleted==null){
            return "Message does not exist.";
        }

        //if the message exists, delete it
        acc.getMessageBox().remove(msgToBeDeleted);
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
