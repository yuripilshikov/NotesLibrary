package libraryofnotes.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author YuriPilshikov
 */
public class SimpleNote {
    private int id;
    private String name;
    private String content;
    private List<SimpleNote> children;

    public SimpleNote(int id, String name, String content) {
        this.id = id;
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

    public List<SimpleNote> getChildren() {
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
    
    public static SimpleNote getByID(int id, SimpleNote rootNote) {
        SimpleNote note = null;
        if(rootNote.getId() == id) return rootNote;
        note = searchChildrenByID(id, rootNote);
        return note;
    }

    private static SimpleNote searchChildrenByID(int id, SimpleNote note) {
        for(SimpleNote tnc : note.getChildren()) {
            if(tnc.getId() == id) {
                return tnc;                
            } else {
                note = searchChildrenByID(id, tnc);
            }
        }        
        return note;
    }    
}
