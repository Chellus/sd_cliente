package py.pol.una.sd;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramSocket;

public class Main {
    public static void main(String[] args) {
        int PUERTO_SERVIDOR = 1234;
        String user = "";
        user = JOptionPane.showInputDialog("Ingrese su nombre de usuario:");
        System.out.println(user);

        byte buffer[] = new byte[1024];

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ClientGUI cliente = new ClientGUI();
                cliente.setVisible(true);
            }
        });

    }
}