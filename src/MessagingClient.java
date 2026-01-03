import java.io.*;
import java.net.*;

public class MessagingClient {
    public static void main(String[] args) throws IOException {

        String ip = args[0];
        int port = Integer.parseInt(args[1]);

        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            System.err.println("Could not initiate a connection to server");
            System.exit(1);
        }

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        String commandFromUser = stdin.readLine();
        if (commandFromUser!=null){
            //String[] parts = command.split(" ");
            out.println(commandFromUser);
        }

        out.close();
        in.close();
        socket.close();
    }

}
