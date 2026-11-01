package client;
import java.net.*;
import java.util.Scanner;

public class UDPclient {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = sc.nextLine();

        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");

        while (true) {
            System.out.print("> ");
            String text = sc.nextLine();
            if (text.equalsIgnoreCase("exit")) break;

            String message = "[" + username + "] : " + text;
            byte[] data = message.getBytes();

            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, 1234);
            socket.send(packet);
        }
        socket.close();
        sc.close();
    }
}