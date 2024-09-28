package py.pol.una.sd;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sender {
    public static void main(String[] args ) throws Exception{
        int serverPort = 1234;
        byte[] buffer = new byte[1024];

        String user = JOptionPane.showInputDialog("Ingrese su nombre de usuario: ");
        String receptor;
        JSONObject json_message = new JSONObject();
        boolean login;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

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

            while (true) {
                buffer = new byte[1024];
                json_message = new JSONObject();
                System.out.println("Ingrese el destino: ");
                receptor = inFromUser.readLine();
                System.out.println("Ingrese el mensaje: ");
                String mensaje = inFromUser.readLine();

                json_message.put("tipo", "mensaje");
                json_message.put("destino", receptor);
                json_message.put("mensaje", mensaje);


                buffer = json_message.toString().getBytes();
                packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
                socket.send(packet);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
