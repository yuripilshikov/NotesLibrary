package libraryofnotes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author YuriPilshikov
 */
public class TestNoteClass {
    String name;
    String content;
    List<TestNoteClass> children;
    
    public static TestNoteClass testNotes;
    
    static {
        testNotes = new TestNoteClass("Root note", "Philosophy");
        
        testNotes.getChildren().add(new TestNoteClass("first level", "e is more narrow.[24][27][28]"));
        testNotes.getChildren().add(new TestNoteClass("first level another note", "i am too lazy to add more stuff"));
        testNotes.getChildren().get(0).getChildren().add(new TestNoteClass("first level third note", "Here be something"));
    }

    public TestNoteClass(String name, String content) {
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

    @Override
    public String toString() {
        return this.name;
    }
    
    
    
        
}
