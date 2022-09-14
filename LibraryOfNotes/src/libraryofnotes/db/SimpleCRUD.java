package libraryofnotes.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import libraryofnotes.model.SimpleNote;
import libraryofnotes.utils.MyLogger;
import libraryofnotes.utils.Settings;

/**
 *
 * @author YuriPilshikov
 */
public class SimpleCRUD {
    private Settings settings;
    private MyLogger logger;
    
    public SimpleCRUD() {
        settings = new Settings();
        logger = MyLogger.getLogger();
    }
    
    public SimpleNote getNoteTree() {
        SimpleNote noteTree = null;
        
        try (Connection connection = DriverManager.getConnection(
                settings.getValue(Settings.URL),
                settings.getValue(Settings.USER),
                settings.getValue(Settings.PSW)); ) {
            
            Statement stm = connection.createStatement(); 
            ResultSet rs = stm.executeQuery("select ID, TITLE, CONTENT, PARENTID from testnotes");
            while(rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String content = rs.getString(3);
                int parentId = rs.getInt(4);      
                logger.info("From DB: note" + id + ". " + title + ", child of " + parentId);                
                SimpleNote currentNote = new SimpleNote(id, title, content);
                // root node id is always 1                
                if(id == 1) {
                    noteTree = new SimpleNote(id, title, content);
                    continue;
                }
                if(parentId != 0) {
                    SimpleNote parent = SimpleNote.getByID(parentId, noteTree);
                    if(parent != null) {
                        parent.getChildren().add(currentNote);
                    }
                }                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return noteTree;
    }
    
    public SimpleNote getNoteById(int id) {
        SimpleNote note = null;
        
        try (Connection connection = DriverManager.getConnection(
                settings.getValue(Settings.URL),
                settings.getValue(Settings.USER),
                settings.getValue(Settings.PSW)); ) {
            
            Statement stm = connection.createStatement(); 
            String sqlQuery = "select TITLE, CONTENT, PARENTID from testnotes where id=" + id;
            ResultSet rs = stm.executeQuery(sqlQuery);
            if(rs.next()) {                
                String title = rs.getString(1);
                String content = rs.getString(2);
                int parentId = rs.getInt(3);      
                logger.info("From DB: note" + id + ". " + title + ", child of " + parentId);  
                SimpleNote currentNote = new SimpleNote(id, title, content);
                // root node id is always 1                
                if(id == 1) {
                    note = new SimpleNote(id, title, content);
                } else if(parentId != 0) {
                    SimpleNote parent = SimpleNote.getByID(parentId, note);
                    if(parent != null) {
                        parent.getChildren().add(currentNote);
                    }
                }                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        return note;
    }
    
    public void createNote(SimpleNote note, int parentId) {
        try (Connection connection = DriverManager.getConnection(
                settings.getValue(Settings.URL),
                settings.getValue(Settings.USER),
                settings.getValue(Settings.PSW)); ) {
            
            int maxId;
            try(Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("select max(id) from testnotes")) {
                rs.next();
                maxId = rs.getInt(1);
            }
            
            try(PreparedStatement pstmt = connection.prepareStatement("insert into testnotes (title, content, parentid) values (?, ?, ?)")) {
                pstmt.setString(1, note.getName());
                pstmt.setString(2, note.getContent());
                pstmt.setInt(3, parentId);
                pstmt.executeUpdate();
            }            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void createNote(int parentId) {
        SimpleNote n = new SimpleNote(-1, "new empty note", "empty note content");
        createNote(n, parentId);
    }
    
    public void updateNote(SimpleNote note, int parentId) {                                
        try (Connection connection = DriverManager.getConnection(
                settings.getValue(Settings.URL),
                settings.getValue(Settings.USER),
                settings.getValue(Settings.PSW)); ) {            
            try(PreparedStatement pstmt = connection.prepareStatement(""
                    + "update testnotes set title=? where id=?"
                    + "update testnotes set content=? where id=?"
                    + "update testnotes set parentid=? where id=?"
                    + "")) {
                pstmt.setString(1, note.getName());
                pstmt.setInt(2, note.getId());
                pstmt.setString(3, note.getContent());
                pstmt.setInt(4, note.getId());
                pstmt.setInt(5, parentId);
                pstmt.setInt(6, note.getId());
                pstmt.executeUpdate();
            }            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void updateNote(SimpleNote note) {                                
        try (Connection connection = DriverManager.getConnection(
                settings.getValue(Settings.URL),
                settings.getValue(Settings.USER),
                settings.getValue(Settings.PSW)); ) {            
            try(PreparedStatement pstmt = connection.prepareStatement("update testnotes set title=?, content=? where id=?")) {
                pstmt.setString(1, note.getName());                
                pstmt.setString(2, note.getContent());
                pstmt.setInt(3, note.getId());
                pstmt.executeUpdate();
            }            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void DeleteNoteByID(int id) {
        try (Connection connection = DriverManager.getConnection(
                settings.getValue(Settings.URL),
                settings.getValue(Settings.USER),
                settings.getValue(Settings.PSW)); ) {            
            try(PreparedStatement pstmt = connection.prepareStatement(""
                    + "delete from testnotes where id=?")) {                
                pstmt.setInt(1, id);                                
                pstmt.executeUpdate();
            }            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /////
    public int getMaxId(){
        int max = 0;
        try (Connection connection = DriverManager.getConnection(
                settings.getValue(Settings.URL),
                settings.getValue(Settings.USER),
                settings.getValue(Settings.PSW)); ) {
            
            Statement stm = connection.createStatement(); 
            ResultSet rs = stm.executeQuery("select max(id) from testnotes");
            if(rs.next()) {                
                max = rs.getInt(1);                
            } else {
                max = -1;
            }            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }                
        return max;
    }
}
