package libraryofnotes.model;

import java.sql.Date;

/**
 *
 * @author YuriPilshikov
 */
public class Task {
    private int id;
    private String title;
    private String content;
    private SimpleNote note;
    private int projectID;
    private Date createDate;
    private Date deadline;

    public Task(int id, String title, String content, SimpleNote note, int projectID, Date createDate, Date deadline) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.note = note;
        this.projectID = projectID;
        this.createDate = createDate;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SimpleNote getNote() {
        return note;
    }

    public void setNote(SimpleNote note) {
        this.note = note;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", title=" + title + ", content=" + content + ", note=" + note + ", projectID=" + projectID + ", createDate=" + createDate + ", deadline=" + deadline + '}';
    }
    
    
    
}
