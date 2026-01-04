import java.io.*;
import java.net.*;

public class MessagingClient {
    public static void main(String[] args) throws IOException {
        String ip = args[0];
        int port = Integer.parseInt(args[1]);

        StringBuilder commandFromUser = new StringBuilder();
        for (int i=2; i<args.length; i++){
            commandFromUser.append(args[i]).append(" ");
        }

        try (Socket socket = new Socket(ip, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));){

            out.println(commandFromUser);

            String line;
            while((line=in.readLine())!=null){
                System.out.println(line);
            }
        } catch (Exception e) {
            System.err.println("Could not initiate a connection to server");
            System.exit(1);
        }
    }
}