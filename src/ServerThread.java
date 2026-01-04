import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    private Socket socket;
    private MessagingServer server;

    public ServerThread(Socket socket, MessagingServer server) {
        super();
        this.socket = socket;
        this.server=server;
    }


    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String command = in.readLine();
            if(command!=null){
                String[] parts = command.split(" ");
                int function_id = Integer.parseInt(parts[0]);

                if (function_id==1){
                    String username = parts[1];
                    String response = server.createAccount(username);
                    out.println(response);
                }
                else if (function_id==2){
                    String response = server.showAccounts();
                    out.println(response);
                }
                else if (function_id==3){
                    int authToken=Integer.parseInt(parts[1]);
                    String recipient=parts[2];

                    StringBuilder message = new StringBuilder();
                    for (int i=3; i< parts.length; i++){
                        message.append(parts[i]).append(" ");
                    }

                    String response = server.sendMessage(authToken,recipient,message.toString());
                    out.println(response);
                }
                else if (function_id==4){
                    int authToken=Integer.parseInt(parts[1]);
                    String response = server.showInbox(authToken);
                    out.println(response);
                }
                else if (function_id==5){
                    int authToken = Integer.parseInt(parts[1]);
                    int messageID = Integer.parseInt(parts[2]);
                    String response = server.readMessage(authToken, messageID);
                    out.println(response);
                }
                else if (function_id==6){
                    int authToken = Integer.parseInt(parts[1]);
                    int messageID = Integer.parseInt(parts[2]);
                    String response = server.deleteMessage(authToken, messageID);
                    out.println(response);
                }
            }

            out.close();
            in.close();
            socket.close();
            System.out.println("Terminating connection. Client: " + socket.getInetAddress().getHostAddress());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
