package libraryofnotes.model;

import java.util.Date;
import java.util.List;

public class Note_old {
    private int id;
    private String header;
    private String notecontent;
    private Date creationDate;
    private Date changeDate;
    private List<Tag_old> tags;
    private List<Note_old> childNotes;

    public Note_old(int id, String header, String name, Date creationDate, Date changeDate, List<Tag_old> tags, List<Note_old> childNotes) {
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

    public List<Tag_old> getTags() {
        return tags;
    }

    public void setTags(List<Tag_old> tags) {
        this.tags = tags;
    }

    public List<Note_old> getChildNotes() {
        return childNotes;
    }

    public void setChildNotes(List<Note_old> childNotes) {
        this.childNotes = childNotes;
    }
    
    
    
    
    
}
