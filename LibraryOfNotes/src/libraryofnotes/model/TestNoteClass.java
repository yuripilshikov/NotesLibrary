package libraryofnotes.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author YuriPilshikov
 */
public class TestNoteClass {
    int id;
    String name;
    String content;
    List<TestNoteClass> children;
    
    public static TestNoteClass testNotes;
    
    static {
        testNotes = new TestNoteClass(1, "Root note", "Philosophy");
        
        testNotes.getChildren().add(new TestNoteClass(2, "first level", "e is more narrow.[24][27][28]"));
        testNotes.getChildren().add(new TestNoteClass(3, "first level another note", "i am too lazy to add more stuff"));
        testNotes.getChildren().get(0).getChildren().add(new TestNoteClass(4, "first level third note", "Here be something"));
    }

    public TestNoteClass(int id, String name, String content) {
        this.name = name;
        this.content = content;
        children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<TestNoteClass> getChildren() {
        return children;
    }  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    
    public static TestNoteClass getByID(int id, TestNoteClass rootNote) {
        TestNoteClass note = null;
        System.out.println(">> " + rootNote.getId() + " " + rootNote.getName());
        if(rootNote.getId() == id) return rootNote;
        note = searchChildrenByID(id, rootNote);
        return note;
    }

    private static TestNoteClass searchChildrenByID(int id, TestNoteClass note) {
        for(TestNoteClass tnc : note.getChildren()) {
            System.out.println(">> " + tnc.getId() + " " + tnc.getName());
            if(tnc.getId() == id) {
                return tnc;                
            } else {
                note = searchChildrenByID(id, tnc);
            }
        }        
        return note;
    }
    
    
    
        
}
