package ObjectLib;

import java.util.List;

public class Task {

    String taskName;
    String description;
    List<Objective> objectives;

    public float Compleation(){
        float percCompleation;
        int completed = 0;
        for (Objective objective : objectives) {
            if (objective.isComplete){
                completed++;
            }
        }
        percCompleation = (float) (objectives.size() / completed) ;
        return percCompleation;
    }

    //TODO
    // image reference


}
