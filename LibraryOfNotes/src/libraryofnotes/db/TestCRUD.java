/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryofnotes.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import libraryofnotes.model.Note;
import libraryofnotes.model.TestNoteClass;
import libraryofnotes.utils.Settings;

/**
 *
 * @author YuriPilshikov
 */
public class TestCRUD {
    Settings settings;
    
    public TestCRUD() {
        settings = new Settings();
    }
    
    public TestNoteClass getNoteTree() {
        TestNoteClass noteTree = null;
        
        try (Connection connection = DriverManager.getConnection(
                settings.getValue(Settings.URL),
                settings.getValue(Settings.USER),
                settings.getValue(Settings.PSW)); ) {
            
            Statement stm = connection.createStatement(); 
            ResultSet rs = stm.executeQuery("select * from testnotes");
            while(rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String content = rs.getString(3);
                int parentId = rs.getInt(4);      
                System.out.println(id + " " + title + " " + content + " " + parentId);
                TestNoteClass currentNote = new TestNoteClass(id, title, content);
                // root node id is always 1                
                if(id == 1) {
                    noteTree = new TestNoteClass(id, title, content);
                    continue;
                }
                if(parentId != 0) {
                    TestNoteClass parent = TestNoteClass.getByID(parentId, noteTree);
                    if(parent != null) {
                        parent.getChildren().add(currentNote);
                    }
                }                
//                note = new Note(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getDate(5), null, null);
//                notes.add(note);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return noteTree;
    }
}
