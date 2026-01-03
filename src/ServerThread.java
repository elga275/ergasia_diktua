import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    private Socket socket;
    private MessagingServer server;

    public ServerThread(Socket socket, MessagingServer server) {
        super();
        this.socket = socket;
        this.server=server;
        this.start();
    }


    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String command = in.readLine();
            if(command!=null){
                String[] parts = command.split(" ");
                int function_id = Integer.parseInt(parts[2]);
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
