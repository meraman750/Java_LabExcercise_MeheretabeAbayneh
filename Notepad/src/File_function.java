import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class File_function {

    Main main;
    String fname;
    String fpath;

    public File_function(Main main) {
        this.main = main;
    }

    public void new_file(){
        main.textArea.setText("");
        main.window.setTitle("New File");
        fname = null;
        fpath = null;
    }

    public void open_file(){
        FileDialog fd = new FileDialog(main.window, "Open File",  FileDialog.LOAD);
        fd.setVisible(true);

        if (fd.getFile()!=null){
            fname = fd.getFile();
            fpath = fd.getDirectory();
            main.window.setTitle(fname);
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(fpath + fname));

            main.textArea.setText("");

            String line;
            while ((line = br.readLine())!=null){
                main.textArea.append(line + "\n");
            }
            br.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void save_file(){
        if (fname == null){
            saveAs_file();
        }
        else{
            try {
                FileWriter fw = new FileWriter(fpath + fname);
                fw.write(main.textArea.getText());
                fw.close();

            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public void saveAs_file(){
        FileDialog fd = new FileDialog(main.window, "Save File", FileDialog.SAVE);
        fd.setVisible(true);

        if ((fd.getFile())!= null){
            fname = fd.getFile();
            fpath = fd.getDirectory();
            main.window.setTitle(fname);
        }

        try{
            FileWriter fw = new FileWriter(fpath + fname);
            fw.write(main.textArea.getText());
            fw.close();

        } catch (Exception e){
            System.out.println(e);
        }

    }

    public void rename_file(){
        if (fname == null){
            System.out.println("No file selected");
            return;
        }
        FileDialog fd = new FileDialog(main.window, "Rename File", FileDialog.SAVE);
        fd.setFile(fname);
        fd.setVisible(true);

        if ((fd.getFile())!= null){
            String new_name = fd.getFile();

            File old_file = new File(fpath + fname);
            File new_file = new File(fpath + new_name);

            if (old_file.renameTo(new_file)){
                fname = new_name;
                main.window.setTitle(fname);
                System.out.println("File renamed");
            } else {
                System.out.println("File could not be renamed");
            }
        }
    }

    public void delete_file(){
        if (fname == null){
            System.out.println("No file selected");
            return;
        }

        File file = new File(fpath + fname);

        if (file.delete()){
            main.window.setTitle("New File");
            main.textArea.setText("");

            fname = null;
            fpath = null;

            System.out.println("File deleted");
        }
        else{
            System.out.println("File could not be deleted");
        }
    }

    public void exit_file(){
        System.exit(0);
    }
}
