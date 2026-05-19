import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyboard_function implements KeyListener {

    Main main;

    public keyboard_function(Main main){
        this.main = main;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.isShiftDown() && e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S){
            main.file.saveAs_file();
        }
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S){
            main.file.save_file();
        }
        if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_F){
            main.menuFile.doClick();
        }
        if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_O){
            main.file.open_file();
        }
        if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z){
            main.edit.undo();
        }
        if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y){
            main.edit.redo();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
