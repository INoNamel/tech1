import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {

    static List<Handler> clients = new ArrayList<>();

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        System.out.println(" SERVER READY ");

        try {
            ServerSocket server = new ServerSocket(6666);

            while (true) {
                Socket client = server.accept();

                DataInputStream fromClient = new DataInputStream(client.getInputStream());
                DataOutputStream toClient = new DataOutputStream(client.getOutputStream());

                System.out.println(" New connection detected. Waiting for response...");

                toClient.writeUTF(" < server > : who are you?");
                String clientName = fromClient.readUTF();
                toClient.writeUTF(" < server > : welcome, "+ clientName);
                System.out.println("user "+clientName+" joined");

                Handler handler = new Handler(client, clientName, fromClient, toClient);
                Thread thread = new Thread(handler);
                clients.add(handler);
                thread.start();

            }

        } catch (IOException e) {
            System.out.println(" < server > : one connection failed.");
        }

    }
}
