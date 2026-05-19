import java.io.Serializable;

public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    public String sender;
    public boolean isImage;
    public String textMessage;
    public byte[] imageBytes;

    public ChatMessage(String sender, String textMessage) {
        this.sender = sender;
        this.isImage = false;
        this.textMessage = textMessage;
    }

    public ChatMessage(String sender, byte[] imageBytes) {
        this.sender = sender;
        this.isImage = true;
        this.imageBytes = imageBytes;
    }
}