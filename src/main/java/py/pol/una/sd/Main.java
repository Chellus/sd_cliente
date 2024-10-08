package py.pol.una.sd;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
        JSONObject json_message = new JSONObject();
        boolean login;

        json_message.put("tipo", "login");
        json_message.put("mensaje", user);

        try {
            // Primero enviamos al servidor el nombre de usuario del cliente
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getLocalHost();
            buffer = json_message.toString().getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
            socket.send(packet);

            // limpiamos el buffer
            buffer = new byte[1024];
            packet = new DatagramPacket(buffer, buffer.length);

            // esperamos recibir un mensaje para saber si pudimos iniciar sesion
            socket.receive(packet);

            String res = new String(packet.getData(), 0, packet.getLength());
            System.out.println(res);
            Object obj = new JSONParser().parse(res);
            JSONObject json_res = (JSONObject) obj;
            login = (boolean) json_res.get("login");

            // verificamos que hayamos iniciado sesion satisfactoriamente
            while (!login) {
                buffer = new byte[1024];
                user = JOptionPane.showInputDialog("El nombre de usuario ya existe. Ingrese su nombre de usuario: ");
                json_message = new JSONObject();

                json_message.put("tipo", "login");
                json_message.put("mensaje", user);

                buffer = json_message.toString().getBytes();
                packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
                socket.send(packet);

                // limpiamos el buffer
                buffer = new byte[1024];
                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                res = new String(packet.getData(), 0, packet.getLength());
                obj = new JSONParser().parse(res);
                json_res = (JSONObject) obj;
                login = (boolean) json_res.get("login");
            }

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
                res = new String(packet.getData(), 0, packet.getLength());
                obj = new JSONParser().parse(res);
                json_res = (JSONObject) obj;

                String origen = (String) json_res.get("origen");
                String mensaje = (String) json_res.get("mensaje");

                client.addMessage(origen, mensaje);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}