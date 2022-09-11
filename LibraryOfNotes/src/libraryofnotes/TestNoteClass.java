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
