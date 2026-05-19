import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class ChatClient extends JFrame {
    private JTextPane chatPane;
    private JTextField inputField, ipField;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private JButton connectBtn;

    public ChatClient() {
        setupGUI();
    }

    private void setupGUI() {
        setTitle("Chat App - CLIENT");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(200, 200, 230));
        ipField = new JTextField("localhost", 15);
        connectBtn = new JButton("Connect to Server");
        topPanel.add(new JLabel("Server IP:"));
        topPanel.add(ipField);
        topPanel.add(connectBtn);
        add(topPanel, BorderLayout.NORTH);

        chatPane = new JTextPane();
        chatPane.setEditable(false);
        add(new JScrollPane(chatPane), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.setEnabled(false);
        JButton sendTextBtn = new JButton("Send");
        JButton sendImageBtn = new JButton("Send Image");

        bottomPanel.add(sendImageBtn, BorderLayout.WEST);
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendTextBtn, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        connectBtn.addActionListener(e -> connectToServer(ipField.getText().trim(), 5005));
        sendTextBtn.addActionListener(e -> sendText());
        inputField.addActionListener(e -> sendText()); // User can press Enter key to send message
        sendImageBtn.addActionListener(e -> sendImage());

        setVisible(true);
    }

    private void connectToServer(String ip, int port) {
        connectBtn.setEnabled(false);
        new Thread(() -> {
            try {
                appendSystemMessage("Connecting to " + ip + ":" + port + "...");
                Socket socket = new Socket(ip, port);
                appendSystemMessage("Connected!");

                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                SwingUtilities.invokeLater(() -> inputField.setEnabled(true));

                // Listen for incoming messages (starts with history dump from server)
                while (true) {
                    ChatMessage msg = (ChatMessage) in.readObject();
                    displayMessage(msg);
                }
            } catch (Exception e) {
                appendSystemMessage("Connection Failed: " + e.getMessage());
                SwingUtilities.invokeLater(() -> connectBtn.setEnabled(true));
            }
        }).start();
    }

    private void sendText() {
        String text = inputField.getText().trim();
        if (text.isEmpty() || out == null) return;
        ChatMessage msg = new ChatMessage("Client", text);
        transmit(msg);
        inputField.setText("");
    }

    private void sendImage() {
        if (out == null) return;
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                byte[] imageBytes = Files.readAllBytes(fileChooser.getSelectedFile().toPath());
                ChatMessage msg = new ChatMessage("Client", imageBytes);
                transmit(msg);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error reading image.");
            }
        }
    }

    private void transmit(ChatMessage msg) {
        try {
            out.writeObject(msg);
            out.flush();
            displayMessage(msg); // Display our own message locally
        } catch (IOException e) {
            appendSystemMessage("Failed to send.");
        }
    }

    private void displayMessage(ChatMessage msg) {
        SwingUtilities.invokeLater(() -> {
            try {
                Document doc = chatPane.getDocument();
                doc.insertString(doc.getLength(), msg.sender + ": ", null);

                if (msg.isImage) {
                    ImageIcon icon = new ImageIcon(msg.imageBytes);
                    Image img = icon.getImage();

                    // Scale for chat window
                    if (icon.getIconWidth() > 250) {
                        img = img.getScaledInstance(250, -1, Image.SCALE_SMOOTH);
                    }

                    JLabel imageLabel = new JLabel(new ImageIcon(img));
                    imageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    imageLabel.setToolTipText("Click to view full image");

                    // CLICK EVENT TO OPEN FULL IMAGE
                    imageLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            showFullImage(msg.imageBytes);
                        }
                    });

                    chatPane.setCaretPosition(doc.getLength());
                    chatPane.insertComponent(imageLabel);
                    doc.insertString(doc.getLength(), "\n\n", null);
                } else {
                    doc.insertString(doc.getLength(), msg.textMessage + "\n", null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void showFullImage(byte[] imageBytes) {
        JFrame imageFrame = new JFrame("Image Viewer");
        ImageIcon originalIcon = new ImageIcon(imageBytes);
        JLabel fullImageLabel = new JLabel(originalIcon);
        imageFrame.add(new JScrollPane(fullImageLabel));
        imageFrame.pack();
        // Prevent it from being larger than the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (imageFrame.getWidth() > screenSize.width || imageFrame.getHeight() > screenSize.height) {
            imageFrame.setSize(screenSize.width - 100, screenSize.height - 100);
        }
        imageFrame.setLocationRelativeTo(this);
        imageFrame.setVisible(true);
    }

    private void appendSystemMessage(String text) {
        SwingUtilities.invokeLater(() -> {
            try {
                Document doc = chatPane.getDocument();
                doc.insertString(doc.getLength(), "[SYSTEM] " + text + "\n", null);
            } catch (Exception e) {}
        });
    }

    public static void main(String[] args) {
        new ChatClient();
    }
}