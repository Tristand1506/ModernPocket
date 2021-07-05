package ObjectLib;

public class Objective {

    String id;
    String taskId;
    String objectiveName;
    String description;
    boolean isComplete;

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getObjectiveName() {
        return objectiveName;
    }
    public void setObjectiveName(String objectiveName) {
        this.objectiveName = objectiveName;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return isComplete;
    }
    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
