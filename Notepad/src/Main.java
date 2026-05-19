import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener {

    JFrame window;
    JTextArea textArea;
    JScrollPane scrollPane;
    JMenuBar menuBar;
    boolean wordWrapOn = false;

    JMenu menuFile, menuEdit, menuFormat, menuColor, menuHelp, menuAbout;
    JMenuItem item_new, item_open, item_save, item_saveas, item_delete, item_rename, item_exit;

    JMenuItem item_wrap, item_fontArial, item_fontCSMS, item_fontTNR, item_font8, item_font10, item_font12, item_font16, item_font20, item_font24, item_font28;
    JMenu menuFont, menuFontSize;

    JMenuItem item_color1, item_color2;

    JMenuItem item_undo, item_redo;

    File_function file = new File_function(this);
    Format_function format = new Format_function(this);
    Color_function color = new Color_function(this);
    Edit_function edit = new Edit_function(this);
    keyboard_function keyboard = new keyboard_function(this);
    UndoManager  undo = new UndoManager();

    public static void main(String[] args) {
        new Main();
    }
    public Main(){
        createWindow();
        createTextArea();
        createMenuBar();
        createFilemenu();
        createMenuFormat();
        createMenuColor();
        createMenuEdit();

        format.selected_font = "arial";
        format.create_font(16);
        format.word_wrap();

        color.set_color("dark");

        window.setVisible(true);
    }

    public void createWindow(){
        window = new JFrame("NOTE-PAD");
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void createTextArea(){
        textArea = new JTextArea();

        textArea.addKeyListener(keyboard);

        textArea.getDocument().addUndoableEditListener(
                new UndoableEditListener() {
                    public void undoableEditHappened(UndoableEditEvent e) {
                        undo.addEdit(e.getEdit());
                    }
                }
        );

        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        window.add(scrollPane);
    }

    public void createMenuBar(){
        menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);

        menuFile = new JMenu("File");
        menuBar.add(menuFile);

        menuEdit = new JMenu("Edit");
        menuBar.add(menuEdit);

        menuFormat = new JMenu("Format");
        menuBar.add(menuFormat);

        menuColor = new JMenu("Color");
        menuBar.add(menuColor);

        menuHelp = new JMenu("Help");
        menuBar.add(menuHelp);

        menuAbout = new JMenu("About");
        menuBar.add(menuAbout);
    }

    public void createFilemenu(){
        item_new = new JMenuItem("New");
        item_new.addActionListener(this);
        item_new.setActionCommand("new");
        menuFile.add(item_new);

        item_open = new JMenuItem("Open");
        item_open.addActionListener(this);
        item_open.setActionCommand("open");
        menuFile.add(item_open);

        item_save = new JMenuItem("Save");
        item_save.addActionListener(this);
        item_save.setActionCommand("save");
        menuFile.add(item_save);

        item_saveas = new JMenuItem("SaveAs");
        item_saveas.addActionListener(this);
        item_saveas.setActionCommand("saveas");
        menuFile.add(item_saveas);

        item_rename = new JMenuItem("Rename");
        item_rename.addActionListener(this);
        item_rename.setActionCommand("rename");
        menuFile.add(item_rename);

        item_delete = new JMenuItem("Delete");
        item_delete.addActionListener(this);
        item_delete.setActionCommand("delete");
        menuFile.add(item_delete);

        item_exit = new JMenuItem("Exit");
        item_exit.addActionListener(this);
        item_exit.setActionCommand("exit");
        menuFile.add(item_exit);
    }

    public void createMenuFormat(){
        item_wrap = new JMenuItem("Word Wrap: Off");
        item_wrap.addActionListener(this);
        item_wrap.setActionCommand("wrap");
        menuFormat.add(item_wrap);

        menuFont = new JMenu("Font");
        menuFormat.add(menuFont);

        item_fontArial = new JMenuItem("Arial");
        item_fontArial.addActionListener(this);
        item_fontArial.setActionCommand("arial");
        menuFont.add(item_fontArial);

        item_fontCSMS = new JMenuItem("Comic Sans MS");
        item_fontCSMS.addActionListener(this);
        item_fontCSMS.setActionCommand("CSMS");
        menuFont.add(item_fontCSMS);

        item_fontTNR = new JMenuItem("Times New Roman");
        item_fontTNR.addActionListener(this);
        item_fontTNR.setActionCommand("TNR");
        menuFont.add(item_fontTNR);

        menuFontSize = new JMenu("Font Size");
        menuFormat.add(menuFontSize);

        item_font8 = new JMenuItem("8");
        item_font8.addActionListener(this);
        item_font8.setActionCommand("8");
        menuFontSize.add(item_font8);

        item_font10 = new JMenuItem("10");
        item_font10.addActionListener(this);
        item_font10.setActionCommand("10");
        menuFontSize.add(item_font10);

        item_font12 = new JMenuItem("12");
        item_font12.addActionListener(this);
        item_font12.setActionCommand("12");
        menuFontSize.add(item_font12);

        item_font16 = new JMenuItem("16");
        item_font16.addActionListener(this);
        item_font16.setActionCommand("16");
        menuFontSize.add(item_font16);

        item_font20 = new JMenuItem("20");
        item_font20.addActionListener(this);
        item_font20.setActionCommand("20");
        menuFontSize.add(item_font20);

        item_font24 = new JMenuItem("24");
        item_font24.addActionListener(this);
        item_font24.setActionCommand("24");
        menuFontSize.add(item_font24);

        item_font28 = new JMenuItem("28");
        item_font28.addActionListener(this);
        item_font28.setActionCommand("28");
        menuFontSize.add(item_font28);

    }

    public void createMenuColor(){
        item_color1 = new JMenuItem("White Theme");
        item_color1.addActionListener(this);
        item_color1.setActionCommand("white");
        menuColor.add(item_color1);

        item_color2 = new JMenuItem("Dark Theme");
        item_color2.addActionListener(this);
        item_color2.setActionCommand("dark");
        menuColor.add(item_color2);
    }

    public void createMenuEdit(){
        item_undo = new JMenuItem("Undo");
        item_undo.addActionListener(this);
        item_undo.setActionCommand("undo");
        menuEdit.add(item_undo);

        item_redo = new JMenuItem("Redo");
        item_redo.addActionListener(this);
        item_redo.setActionCommand("redo");
        menuEdit.add(item_redo);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();

        switch (command){
            case "new": file.new_file(); break;
            case "open": file.open_file(); break;
            case "save": file.save_file(); break;
            case "saveas": file.saveAs_file(); break;
            case "rename": file.rename_file(); break;
            case "delete": file.delete_file(); break;
            case "exit": file.exit_file(); break;
            case "wrap": format.word_wrap(); break;
            case "arial": format.set_font(command); break;
            case "CSMS": format.set_font(command); break;
            case "TNR": format.set_font(command); break;
            case "8": format.create_font(8); break;
            case "10": format.create_font(10); break;
            case "12": format.create_font(12); break;
            case "16": format.create_font(16); break;
            case "20": format.create_font(20); break;
            case "24": format.create_font(24); break;
            case "28": format.create_font(28); break;
            case "white": color.set_color(command); break;
            case "dark": color.set_color(command); break;
            case "undo": edit.undo(); break;
            case "redo": edit.redo(); break;
        }
    }
}