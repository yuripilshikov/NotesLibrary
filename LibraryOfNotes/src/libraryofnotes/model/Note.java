package libraryofnotes.model;

import java.util.Date;
import java.util.List;

public class Note {
    private int id;
    private String header;
    private String notecontent;
    private Date creationDate;
    private Date changeDate;
    private List<Tag> tags;
    private List<Note> childNotes;

    public Note(int id, String header, String name, Date creationDate, Date changeDate, List<Tag> tags, List<Note> childNotes) {
        this.id = id;
        this.header = header;
        this.notecontent = name;
        this.creationDate = creationDate;
        this.changeDate = changeDate;
        this.tags = tags;
        this.childNotes = childNotes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getName() {
        return notecontent;
    }

    public void setName(String name) {
        this.notecontent = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Note> getChildNotes() {
        return childNotes;
    }

    public void setChildNotes(List<Note> childNotes) {
        this.childNotes = childNotes;
    }
    
    
    
    
    
}
