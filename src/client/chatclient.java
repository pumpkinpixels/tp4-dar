package client;
import java.net.*;
import java.util.Scanner;

public class chatclient {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = sc.nextLine();

        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");

        // Thread that receives messages
        Thread receiver = new Thread(() -> {
            byte[] buffer = new byte[1024];
            try {
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String msg = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("\n" + msg);
                }
            } catch (Exception e) {
                System.out.println("Receiver stopped.");
            }
        });
        receiver.setDaemon(true); // stops automatically when the program ends
        receiver.start();

        // Send a first message so the server registers this client
        send(socket, serverAddress, "[" + username + "] joined the chat");

        // Main thread: read keyboard and send
        while (true) {
            String text = sc.nextLine();
            if (text.equalsIgnoreCase("exit")) break;
            send(socket, serverAddress, "[" + username + "] : " + text);
        }
        socket.close();
        sc.close();
    }

    private static void send(DatagramSocket socket, InetAddress address, String message) throws Exception {
        byte[] data = message.getBytes();
        socket.send(new DatagramPacket(data, data.length, address, 1234));
    }
}
