import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:chat_history.db";

    public DatabaseManager() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS messages (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "sender TEXT, " +
                    "is_image BOOLEAN, " +
                    "text_content TEXT, " +
                    "image_data BLOB)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveMessage(ChatMessage msg) {
        String sql = "INSERT INTO messages(sender, is_image, text_content, image_data) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, msg.sender);
            pstmt.setBoolean(2, msg.isImage);
            pstmt.setString(3, msg.textMessage);
            pstmt.setBytes(4, msg.imageBytes);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ChatMessage> getHistory() {
        List<ChatMessage> history = new ArrayList<>();
        String sql = "SELECT * FROM messages ORDER BY id ASC";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String sender = rs.getString("sender");
                if (rs.getBoolean("is_image")) {
                    history.add(new ChatMessage(sender, rs.getBytes("image_data")));
                } else {
                    history.add(new ChatMessage(sender, rs.getString("text_content")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }
}