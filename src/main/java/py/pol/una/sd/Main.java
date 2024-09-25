package py.pol.una.sd;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int PUERTO_SERVIDOR = 1234;
                String user = "";
                user = JOptionPane.showInputDialog("Ingrese su nombre de usuario:");
                byte[] buffer = new byte[1024];
                System.out.println(user);

                try {
                    DatagramSocket socketUDP = new DatagramSocket();
                    InetAddress server = InetAddress.getByName("localhost");

                    buffer = user.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, server, PUERTO_SERVIDOR);
                    socketUDP.send(packet);

                    ClientGUI client = new ClientGUI();
                    client.setVisible(true);

                    while (true) {
                        buffer = new byte[1024];
                        //packet de escucha
                        packet = new DatagramPacket(buffer, buffer.length);
                        //el socket escucha y recibe en el packet
                        socketUDP.receive(packet);
                        String message = new String(packet.getData(), 0, packet.getLength());
                        client.addMessage(message.substring(0, message.indexOf(":") - 1), message.substring(message.indexOf(":")));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }




            }
        });

    }
}