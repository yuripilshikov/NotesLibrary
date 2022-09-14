package libraryofnotes.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Statement;
import libraryofnotes.model.Note_old;
import libraryofnotes.utils.Settings;

public class CRUD_old {
    
    Settings settings;

    public CRUD_old() {
        settings = new Settings();
    }
    
    public List<Note_old> getAllNotes() {
        List<Note_old> notes = new ArrayList<>();
        //notes = getNotesJDBC();
        notes = testNoteList();

        return notes;
    }

    // For now, we will use Glassfish database.
    // root : root
    // jdbc:derby://localhost:1527/NoteLibrary [root on ROOT]
    private List<Note_old> getNotesJDBC() {
        List<Note_old> notes = new ArrayList<>();
        
        Note_old note = null;

        try (Connection connection = DriverManager.getConnection(
                settings.getValue(Settings.URL),
                settings.getValue(Settings.USER),
                settings.getValue(Settings.PSW)); ) {
            
            Statement stm = connection.createStatement(); 
            ResultSet rs = stm.executeQuery("select * from notes");
            while(rs.next()) {
                note = new Note_old(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getDate(5), null, null);
                notes.add(note);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return notes;
    }
    
    public void insertNote(Note_old note) {
        // insert(note);
        
    }
    
    private void insert(Note_old note) {
        int max = getMaxIdFromNotes();
        
        try (Connection connection = DriverManager.getConnection(
                settings.getValue(Settings.URL),
                settings.getValue(Settings.USER),
                settings.getValue(Settings.PSW)); ) {
            
            Statement stm = connection.createStatement(); 
            String sql = "INSERT INTO NOTES (HEADER, NOTECONTENT, CREATIONDATE, CHANGEDATE) VALUES ('There is a note number none', 'Test note 1', '2022-09-03', '2022-09-03')";
            stm.execute(sql);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private int getMaxIdFromNotes() {
        int max = -1;
        
        try (Connection connection = DriverManager.getConnection(
                settings.getValue(Settings.URL),
                settings.getValue(Settings.USER),
                settings.getValue(Settings.PSW)); ) {
            
            Statement stm = connection.createStatement(); 
            ResultSet rs = stm.executeQuery("SELECT MAX (id) FROM notes");
            if(rs != null && rs.next()) {
                max = rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return max;
    }

    // for tests
    private List<Note_old> testNoteList() {
        List<Note_old> list = new ArrayList<>();
        list.add(new Note_old(0, "test", "test", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), null, null));
        list.add(new Note_old(1, "note2", "test1", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), null, null));
        list.add(new Note_old(2, "something", "test2", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), null, null));
//        for (int i = 3; i < 25; i++) {
//            list.add(new Note(i, "Lorem", "Ipsum\n\n\nppppp", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), null, null));
//        }

        return list;
    }
}
