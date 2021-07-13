package ObjectLib;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import UtilLib.DataManager;

public class Task {

    private String id;
    private String taskName;
    private Date date;
    private List<Objective> objectives;

    public Task(){
    }
    public Task(String name, String date){
        taskName = name;
        setDate(date);
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public void setDate(String date) {
        if (!date.isEmpty()) {
            this.date = DataManager.getDateFromString(date);
        }
        else this.date = null;
    }

    public void setDateWithDate(Date acquisitionDate) {
        this.date = acquisitionDate;
    }

    public String getDate() {
        if (date != null) {
            return dateFormat.format(date);
        }
        else return null;
    }

    public List<Objective> getObjectives() {
        if(objectives ==null){
            objectives = new ArrayList<Objective>();
        }
        return objectives;
    }
    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public int getCompletion(){
        float percCompletion;
        float collected = 0f;
        if (objectives == null){
            return 0;
        }
        for (Objective item : objectives) {
            if (item.isComplete()){
                collected++;
            }
        }
        if (objectives.size() > 0) {
            percCompletion = (float)(collected / (float)objectives.size());
            System.out.println("Compleation: "+ collected + "/" + objectives.size() + "\n at: " + percCompletion*100f +"%");
        }
        else{percCompletion = 0;}
        return (int)(percCompletion*100f);
    }

}
