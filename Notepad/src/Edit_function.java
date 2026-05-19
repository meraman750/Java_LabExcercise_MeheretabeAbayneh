public class Edit_function {

    Main main;

    public Edit_function(Main main){
        this.main = main;
    }

    public void undo(){
        main.undo.undo();
    }

    public void redo(){
        main.undo.redo();
    }

}
