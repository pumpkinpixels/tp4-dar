package serveur;
import java.net.*;

public class UDPserver {
    public static void main(String[]args)throws Exception{
        DatagramSocket socket=new DatagramSocket(null);
        socket.bind(new InetSocketAddress(1234));
        System.out.println("server listening en port 1234");
        byte[]buffer=new byte[1024];
        while(true){
            DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String message=new String(packet.getData(),0,packet.getLength()) ;
            System.out.println("from"+packet.getAddress()+":"+packet.getPort()+message);
        }
    }
}
