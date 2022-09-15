package libraryofnotes.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author YuriPilshikov
 */
public class Project {
    private int id;
    private String title;
    private List<Task> tasks;

    public Project(int id, String title) {
        this.id = id;
        this.title = title;
        this.tasks = new ArrayList<>();
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
    

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", title=" + title + ", tasks: " + tasks + '}';
    }       
}
