package py.pol.una.sd;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class ClientGUI {
    private JPanel contentPane;
    private JTextPane chatPane;
    private HashMap<String, Color> userColors;
    private Random random;

    public ClientGUI() {
        userColors = new HashMap<>();
        random = new Random();
        setupChatPane();
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void setupChatPane() {
        StyledDocument doc = chatPane.getStyledDocument();

        Style messageStyle = chatPane.addStyle("MessageStyle", null);
        StyleConstants.setForeground(messageStyle, Color.BLACK);

    }

    public void addMessage(String user, String message) {
        StyledDocument doc = chatPane.getStyledDocument();

        if (!userColors.containsKey(user)) {
            userColors.put(user, generateRandomColor());
        }

        Color userColor = userColors.get(user);

        Style usernameStyle = chatPane.addStyle("UsernameStyle", null);
        StyleConstants.setForeground(usernameStyle, userColor);
        StyleConstants.setBold(usernameStyle, true);

        try {
            doc.insertString(doc.getLength(), user + ": ", chatPane.getStyle("UsernameStyle"));
            doc.insertString(doc.getLength(), message + "\n", chatPane.getStyle("MessageStyle"));

            chatPane.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    private Color generateRandomColor() {
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return new Color(red, green, blue);
    }
    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        final JScrollPane scrollPane1 = new JScrollPane();
        contentPane.add(scrollPane1, BorderLayout.CENTER);
        chatPane = new JTextPane();
        chatPane.setEditable(false);
        scrollPane1.setViewportView(chatPane);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
