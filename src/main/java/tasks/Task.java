package tasks;

/**
 * Created by clara on 4/12/18.
 */
public class Task {
    
    private Long id;
    private String text;
    private boolean urgent;
    private boolean completed = false;
    
    public Task() {}
    
    public Task(String text, boolean urgent) {
        this.text = text;
        this.urgent = urgent;
    }   // empty constructor, you need this
    
    public Task(String text, boolean urgent, boolean completed) {
        this.text = text;
        this.urgent = urgent;
        this.completed = completed;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public boolean isUrgent() {
        return urgent;
    }
    
    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    
    @Override
    public String toString() {
        return (urgent ? "URGENT! " : " ") +
                text +
                ", completed= " + (completed ? "completed" : "not completed");
    }
}