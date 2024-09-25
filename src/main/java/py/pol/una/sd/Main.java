package py.pol.una.sd;

import javax.swing.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        int serverPort = 1234;
        byte[] buffer = new byte[1024];
        String user = JOptionPane.showInputDialog("Ingrese su nombre de usuario: ");

        try {
            // Primero enviamos al servidor el nombre de usuario del cliente
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getLocalHost();
            buffer = user.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
            socket.send(packet);
            // una vez enviado el nombre de usuario, creamos la interfaz y esperamos a recibir mensajes
            ClientGUI client = new ClientGUI();
            JFrame frame = new JFrame("ClientGUI");
            frame.setContentPane(client.getContentPane());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(800, 600);
            frame.setVisible(true);

            while (true) {
                // limpiamos el buffer
                buffer = new byte[1024];
                packet = new DatagramPacket(buffer, buffer.length);
                // esperamos recibir un mensaje
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                String sender = received.substring(0, received.indexOf(":")).trim();
                String message = received.substring(received.indexOf(":")+1).trim();
                client.addMessage(sender, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}