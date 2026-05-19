import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FiveCardDraw extends JFrame {

    // ENUMS
    enum Suit {
        HEARTS("♥", Color.RED),
        DIAMONDS("♦", Color.RED),
        CLUBS("♣", Color.BLACK),
        SPADES("♠", Color.BLACK);

        final String symbol;
        final Color color;

        Suit(String s, Color c) { symbol = s; color = c; }
    }

    enum Rank {
        TWO(2,"2"), THREE(3,"3"), FOUR(4,"4"), FIVE(5,"5"),
        SIX(6,"6"), SEVEN(7,"7"), EIGHT(8,"8"), NINE(9,"9"),
        TEN(10,"10"), JACK(11,"J"), QUEEN(12,"Q"),
        KING(13,"K"), ACE(14,"A");

        final int value;
        final String name;
        Rank(int v,String n){value=v;name=n;}
    }

    static class Card {
        Rank rank;
        Suit suit;

        Card(Rank r,Suit s){rank=r;suit=s;}

        public String toString(){
            return rank.name + suit.symbol;
        }
    }

    // DECK
    static class Deck {
        ArrayList<Card> cards = new ArrayList<>();

        Deck(){
            for(Suit s:Suit.values())
                for(Rank r:Rank.values())
                    cards.add(new Card(r,s));
            Collections.shuffle(cards);
        }

        Card draw(){
            return cards.remove(cards.size()-1);
        }
    }

    // GAME STATE
    private Deck deck;
    private Card[] playerHand = new Card[5];
    private Card[] cpuHand = new Card[5];

    private boolean drawPhase=false;

    private int bankroll=100;
    private int cpuBankroll=100;
    private final int bet=5;

    // GUI
    private JToggleButton[] playerButtons=new JToggleButton[5];
    private JLabel[] cpuLabels=new JLabel[5];

    private JButton actionButton=new JButton("DEAL");
    private JLabel status=new JLabel();
    private JLabel result=new JLabel("Welcome!");

    public FiveCardDraw(){

        setTitle("Five Card Draw Poker");
        setSize(750,450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the game window on screen

        // STATUS
        status.setFont(new Font("Arial",Font.BOLD,18));
        add(status,BorderLayout.NORTH);

        // CENTER
        JPanel center=new JPanel(new GridLayout(2,5,10,10));
        center.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        for(int i=0;i<5;i++){
            cpuLabels[i]=new JLabel("??",SwingConstants.CENTER);
            cpuLabels[i].setFont(new Font("Arial",Font.BOLD,28));
            center.add(cpuLabels[i]);
        }

        for(int i=0;i<5;i++){
            playerButtons[i]=new JToggleButton("[]");
            playerButtons[i].setFont(new Font("Arial",Font.BOLD,28));
            playerButtons[i].setEnabled(false);
            center.add(playerButtons[i]);
        }

        add(center,BorderLayout.CENTER);

        // BOTTOM
        JPanel bottom=new JPanel(new GridLayout(2,1));
        result.setHorizontalAlignment(SwingConstants.CENTER);
        result.setFont(new Font("Arial",Font.BOLD,16));

        JPanel btnPanel=new JPanel();
        actionButton.setFont(new Font("Arial",Font.BOLD,20));
        actionButton.addActionListener(e->{
            if(!drawPhase) deal();
            else draw();
        });

        btnPanel.add(actionButton);

        bottom.add(result);
        bottom.add(btnPanel);
        add(bottom,BorderLayout.SOUTH);

        updateStatus();
    }

    // DEAL
    private void deal(){

        if(bankroll<bet || cpuBankroll<bet){
            JOptionPane.showMessageDialog(this,"Game Over!");
            return;
        }

        bankroll-=bet;
        cpuBankroll-=bet;

        deck=new Deck();

        for(int i=0;i<5;i++){
            playerHand[i]=deck.draw();
            cpuHand[i]=deck.draw();
        }

        renderPlayer();
        hideCPU();

        drawPhase=true;
        actionButton.setText("DRAW");
        result.setText("Select cards to HOLD.");
        enablePlayer(true);

        updateStatus();
    }

    // DRAW
    private void draw(){

        // player replacement
        for(int i=0;i<5;i++){
            if(!playerButtons[i].isSelected())
                playerHand[i]=deck.draw();
        }

        // CPU strategy
        cpuReplaceLogic();

        renderPlayer();
        revealCPU();

        determineWinner();

        drawPhase=false;
        actionButton.setText("DEAL");
        enablePlayer(false);
    }

    // CPU AI
    private void cpuReplaceLogic(){

        Map<Rank,Integer> count=new HashMap<>();

        for(Card c:cpuHand)
            count.put(c.rank,count.getOrDefault(c.rank,0)+1);

        for(int i=0;i<5;i++){
            if(count.get(cpuHand[i].rank)<2){
                cpuHand[i]=deck.draw();
            }
        }
    }

    // HAND SCORE
    private int evaluate(Card[] hand){

        Map<Rank,Integer> ranks=new HashMap<>();
        boolean flush=true;
        Suit first=hand[0].suit;

        for(Card c:hand){
            ranks.put(c.rank,ranks.getOrDefault(c.rank,0)+1);
            if(c.suit!=first) flush=false;
        }

        ArrayList<Integer> values=new ArrayList<>();
        for(Card c:hand) values.add(c.rank.value);
        Collections.sort(values);

        boolean straight=
                values.get(4)-values.get(0)==4 &&
                        new HashSet<>(values).size()==5;

        Collection<Integer> c=ranks.values();

        if(straight && flush) return 8;
        if(c.contains(4)) return 7;
        if(c.contains(3)&&c.contains(2)) return 6;
        if(flush) return 5;
        if(straight) return 4;
        if(c.contains(3)) return 3;
        if(Collections.frequency(new ArrayList<>(c),2)==2) return 2;
        if(c.contains(2)) return 1;

        return 0;
    }

    // WINNER
    private void determineWinner(){

        int playerScore=evaluate(playerHand);
        int cpuScore=evaluate(cpuHand);

        int pot=bet*2;

        if(playerScore>cpuScore){
            bankroll+=pot;
            result.setText("YOU WIN the pot!");
        }
        else if(cpuScore>playerScore){
            cpuBankroll+=pot;
            result.setText("CPU WINS!");
        }
        else{
            bankroll+=bet;
            cpuBankroll+=bet;
            result.setText("Tie!");
        }

        updateStatus();
    }

    // RENDER
    private void renderPlayer(){
        for(int i=0;i<5;i++){
            playerButtons[i].setText(playerHand[i].toString());
            playerButtons[i].setForeground(playerHand[i].suit.color);
        }
    }

    private void hideCPU(){
        for(JLabel l:cpuLabels) l.setText("??");
    }

    private void revealCPU(){
        for(int i=0;i<5;i++){
            cpuLabels[i].setText(cpuHand[i].toString());
            cpuLabels[i].setForeground(cpuHand[i].suit.color);
        }
    }

    private void enablePlayer(boolean b){
        for(JToggleButton btn:playerButtons){
            btn.setEnabled(b);
            btn.setSelected(false);
        }
    }

    private void updateStatus(){
        status.setText(
                "Player: $" + bankroll +
                        "   |   CPU: $" + cpuBankroll);
    }

    // MAIN
    public static void main(String[] args){
        // Changed to open the PokerIntroPage first
        SwingUtilities.invokeLater(
                ()-> new PokerIntroPage());
    }
}