package py.pol.una.sd;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class ClientGUI extends JFrame {
    private JPanel contentPane;
    private JTextPane chatPane;
    private StyledDocument doc;

    public ClientGUI() {
        setTitle("Chatroom");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        setSize(400, 300);
        pack();

        chatPane = new JTextPane();
        chatPane.setEditable(false);
        doc = chatPane.getStyledDocument();

        JScrollPane scrollPane = new JScrollPane(chatPane);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);

    }

    public void addMessage(String username, String message) {
        try {
            Style usernameStyle = chatPane.addStyle("UsernameStyle", null);
            StyleConstants.setForeground(usernameStyle, Color.BLUE);
            StyleConstants.setBold(usernameStyle, true);

            Style messageStyle = chatPane.addStyle("MessageStyle", null);
            StyleConstants.setForeground(messageStyle, Color.BLACK);

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
