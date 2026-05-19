import javax.swing.*;
import java.awt.*;

public class PokerIntroPage extends JFrame {

    public PokerIntroPage() {
        setTitle("Poker Game Guide & Rankings");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("♠ Welcome to Poker ♣", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(title, BorderLayout.NORTH);

        // Main Scrollable Container
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(30, 120, 70)); // Casino green
        container.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Top Explanations
        container.add(createInfoBlock("What is Poker?", "Poker is a strategy card game where players try to build the strongest 5-card hand."));
        container.add(Box.createRigidArea(new Dimension(0, 10)));

        container.add(createInfoBlock("Grid Game Flow", "1. Both players receive cards.\n2. Replace unwanted cards.\n3. Hands are compared.\n4. Winner takes the pot."));
        container.add(Box.createRigidArea(new Dimension(0, 10)));

        container.add(createInfoBlock("Winning Rules", "The player with the higher ranked hand wins. Each hand type has a strict strength value."));
        container.add(Box.createRigidArea(new Dimension(0, 10)));

        container.add(createInfoBlock("Beginner Tips", "• Keep pairs!\n• Replace random cards.\n• Strong hands are rare.\n• Don't change everything blindly."));
        container.add(Box.createRigidArea(new Dimension(0, 20)));

        // Section Divider
        JLabel sectionHeader = new JLabel("Official Hand Rankings (Strongest to Weakest)");
        sectionHeader.setFont(new Font("Arial", Font.BOLD, 15));
        sectionHeader.setForeground(Color.WHITE);
        sectionHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        container.add(sectionHeader);
        container.add(Box.createRigidArea(new Dimension(0, 10)));

        // Hand Rankings Grid
        container.add(createRankRow(8, "Straight Flush", "<html><b><font color='black'>[9♠]</font><font color='black'>[10♠]</font><font color='black'>[J♠]</font><font color='black'>[Q♠]</font><font color='black'>[K♠]</font></b></html>"));
        container.add(Box.createRigidArea(new Dimension(0, 6)));
        container.add(createRankRow(7, "Four of a Kind", "<html><b><font color='black'>[A♠]</font><font color='red'>[A♥]</font><font color='red'>[A♦]</font><font color='black'>[A♣]</font><font color='red'>[K♦]</font></b></html>"));
        container.add(Box.createRigidArea(new Dimension(0, 6)));
        container.add(createRankRow(6, "Full House", "<html><b><font color='black'>[K♠]</font><font color='red'>[K♥]</font><font color='red'>[K♦]</font><font color='black'>[10♠]</font><font color='red'>[10♥]</font></b></html>"));
        container.add(Box.createRigidArea(new Dimension(0, 6)));
        container.add(createRankRow(5, "Flush", "<html><b><font color='black'>[2♣]</font><font color='black'>[5♣]</font><font color='black'>[8♣]</font><font color='black'>[J♣]</font><font color='black'>[K♣]</font></b></html>"));
        container.add(Box.createRigidArea(new Dimension(0, 6)));
        container.add(createRankRow(4, "Straight", "<html><b><font color='black'>[5♠]</font><font color='red'>[6♥]</font><font color='red'>[7♦]</font><font color='black'>[8♣]</font><font color='black'>[9♠]</font></b></html>"));
        container.add(Box.createRigidArea(new Dimension(0, 6)));
        container.add(createRankRow(3, "Three of a Kind", "<html><b><font color='black'>[Q♠]</font><font color='red'>[Q♥]</font><font color='red'>[Q♦]</font><font color='black'>[2♣]</font><font color='black'>[7♠]</font></b></html>"));
        container.add(Box.createRigidArea(new Dimension(0, 6)));
        container.add(createRankRow(2, "Two Pair", "<html><b><font color='black'>[J♠]</font><font color='red'>[J♥]</font><font color='red'>[4♦]</font><font color='black'>[4♣]</font><font color='black'>[A♠]</font></b></html>"));
        container.add(Box.createRigidArea(new Dimension(0, 6)));
        container.add(createRankRow(1, "One Pair", "<html><b><font color='black'>[10♠]</font><font color='red'>[10♥]</font><font color='red'>[3♦]</font><font color='black'>[6♣]</font><font color='black'>[K♠]</font></b></html>"));
        container.add(Box.createRigidArea(new Dimension(0, 6)));
        container.add(createRankRow(0, "High Card", "<html><b><font color='black'>[A♠]</font><font color='red'>[J♥]</font><font color='red'>[8♦]</font><font color='black'>[4♣]</font><font color='black'>[2♠]</font></b></html>"));

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smoother scrolling
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Control Panel (Small Skip Button)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));

        JButton startBtn = new JButton("Skip Tutorial");
        startBtn.setFont(new Font("Arial", Font.BOLD, 14));
        startBtn.setPreferredSize(new Dimension(180, 35)); // Kept small and tight

        startBtn.addActionListener(e -> {
            dispose();
            new FiveCardDraw().setVisible(true);
        });

        bottomPanel.add(startBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Reusable Layout Block for Explanations
    private JPanel createInfoBlock(String title, String text) {
        JPanel block = new JPanel(new BorderLayout());
        block.setBackground(new Color(250, 250, 250));
        block.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        block.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(new Color(40, 40, 40));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));

        JTextArea contentLabel = new JTextArea(text);
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        contentLabel.setEditable(false);
        contentLabel.setBackground(new Color(250, 250, 250));
        contentLabel.setLineWrap(true);
        contentLabel.setWrapStyleWord(true);

        block.add(titleLabel, BorderLayout.NORTH);
        block.add(contentLabel, BorderLayout.CENTER);
        return block;
    }

    // Reusable Layout Row for Ranks
    private JPanel createRankRow(int rankNum, String rankName, String visualCards) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        JLabel rankLabel = new JLabel("Rank " + rankNum + ": " + rankName);
        rankLabel.setFont(new Font("Arial", Font.BOLD, 14));
        rankLabel.setForeground(Color.DARK_GRAY);

        JLabel cardsLabel = new JLabel(visualCards);
        cardsLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));

        row.add(rankLabel, BorderLayout.WEST);
        row.add(cardsLabel, BorderLayout.EAST);

        return row;
    }
}