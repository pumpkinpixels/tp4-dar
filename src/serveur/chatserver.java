import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class chatserver {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(null);
        socket.bind(new InetSocketAddress(1234));
        System.out.println("Chat server listening on port 1234...");

        List<SocketAddress> clients = new ArrayList<>();

        while (true) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            SocketAddress sender = packet.getSocketAddress();
            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("From " + sender + " -> " + message);

            if (!clients.contains(sender)) {
                clients.add(sender);
                System.out.println("New client registered: " + sender);
            }

            byte[] data = message.getBytes();
            for (SocketAddress client : clients) {
                if (!client.equals(sender)) {
                    DatagramPacket out = new DatagramPacket(data, data.length, client);
                    socket.send(out);
                }
            }
        }
    }
}