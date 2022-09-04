package libraryofnotes.model;

public class Tag {
    private int id;
    private int tagname;

    public Tag(int id, int tagname) {
        this.id = id;
        this.tagname = tagname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTagname() {
        return tagname;
    }

    public void setTagname(int tagname) {
        this.tagname = tagname;
    }
    
    
}
