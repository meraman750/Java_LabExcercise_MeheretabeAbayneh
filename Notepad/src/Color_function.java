import java.awt.*;

public class Color_function {

    Main main;

    public Color_function(Main main) {
        this.main = main;
    }

    public void set_color(String s){
        switch (s){
            case "white":
                main.window.getContentPane().setBackground(Color.WHITE);
                main.textArea.setBackground(Color.WHITE);
                main.textArea.setForeground(Color.BLACK);
                break;

            case "dark":
                main.window.getContentPane().setBackground(Color.BLACK);
                main.textArea.setBackground(Color.BLACK);
                main.textArea.setForeground(Color.WHITE);
                break;
        }
    }

}
