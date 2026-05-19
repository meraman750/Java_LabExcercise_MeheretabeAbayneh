import java.awt.*;

public class Format_function {
    Main main;
    Font arial, csms, tnr;
    String selected_font;

    public Format_function(Main main){
        this.main = main;
    }

    public void word_wrap(){
        if (!main.wordWrapOn){
            main.wordWrapOn = true;
            main.textArea.setLineWrap(true);
            main.textArea.setWrapStyleWord(true);
            main.item_wrap.setText("Word Wrap: On");
        }
        else if (main.wordWrapOn){
            main.wordWrapOn = false;
            main.textArea.setLineWrap(false);
            main.textArea.setWrapStyleWord(false);
            main.item_wrap.setText("Word Wrap: Off");
        }
    }

    public void create_font(int num){
        arial = new Font("Arial", Font.PLAIN, num);
        csms = new Font("Comic Sans MS", Font.PLAIN, num);
        tnr = new Font("Times New Roman", Font.PLAIN, num);

        set_font(selected_font);
    }

    public void set_font(String s){
        selected_font = s;

        switch (selected_font){
            case "arial":
                main.textArea.setFont(arial);
                break;

            case "CSMS":
                main.textArea.setFont(csms);
                break;

            case "TNR":
                main.textArea.setFont(tnr);
                break;
        }
    }

}
