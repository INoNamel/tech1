import java.io.*;
import java.net.*;

class Handler implements Runnable {

    private Socket socket;
    private String name;
    private final DataInputStream receive;
    private final DataOutputStream send;

    public Handler(Socket socket, String name, DataInputStream receive, DataOutputStream send) {
        this.socket = socket;
        this.name = name;
        this.receive = receive;
        this.send = send;
    }

    @Override
    public void run() {
        String message;

            while (true) {
                try {
                    message = receive.readUTF();
                    System.out.println(this.name + " > " + message);


                    if (message.equals("exit")) {
                        try {
                            System.out.println(this.name + " requests to leave...");

                            this.send.writeUTF(" < server > : goodbye, "+this.name);

                            this.socket.close();

                            System.out.println(this.name + " has left the server.");

                        } catch (Exception e) {
                            System.out.println("connection termination failed!");
                            break;
                        }
                    } else for (Handler client : Server.clients) {
                        client.send.writeUTF(this.name + " > " + message);
                    }

                } catch (SocketException e) {
                    System.out.println("Connection for "+ this.name+" has been terminated.");
                    break;
                } catch (IOException e) {
                    System.out.println("Error while reading message!");
                    e.printStackTrace();
                }
            }
    }
}