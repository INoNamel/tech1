import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {

            Socket client = new Socket("localhost", 6666);

            DataInputStream receive = new DataInputStream(client.getInputStream());
            DataOutputStream send = new DataOutputStream(client.getOutputStream());

            Thread sendMsg = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String message = sc.nextLine();
                        try {
                            send.writeUTF(message);
                            if(message.equalsIgnoreCase("exit")) {
                                break;
                            }
                        } catch (SocketException e) {
                            System.out.println("connection terminated");
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                }
            });
            sendMsg.start();

            Thread receiveMsg = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {

                            String message = receive.readUTF();
                            System.out.println(message);

                        } catch (EOFException e) {
                            System.out.println("connection terminated");
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                }
            });
            receiveMsg.start();

        } catch (SocketException e) {
            System.out.println("Server not responding!");
        }catch (Exception e) {
            System.out.println("error?!");
        }
    }
}
