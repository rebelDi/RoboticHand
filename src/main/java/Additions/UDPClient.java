package Additions;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

    public String sendData(String dataQuery){
        try{
            byte[] b=(dataQuery.getBytes());
            byte[] receiveData = new byte[1024];

            InetAddress ip = InetAddress.getByName("192.168.0.111");

            DatagramSocket datagram = new DatagramSocket();

            DatagramPacket send = new DatagramPacket(b, b.length, ip, 8032);

            datagram.send(send);

            DatagramPacket rec = new DatagramPacket(receiveData, receiveData.length);

            datagram.setSoTimeout(4000);

            datagram.receive(rec);
            String modifiedSentence = new String(rec.getData());

            InetAddress returnIPAddress = rec.getAddress();

            int port = rec.getPort();

            System.out.println ("From server at: " + returnIPAddress + ":" + port);
            System.out.println("Message: '" + modifiedSentence + "'");
            modifiedSentence = modifiedSentence.replace("\u0000", "");

            datagram.close();
            return modifiedSentence;
        }catch(Exception e){
            System.out.println("Error:" + e);
        }
        return "Not Connected";
    }
}
