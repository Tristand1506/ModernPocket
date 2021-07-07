package UtilLib;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SleeplessStudios.modernpocket.Objectives;
import com.SleeplessStudios.modernpocket.R;

import ObjectLib.Task;

public class RecyclerViewTaskAdapter extends RecyclerView.Adapter<RecyclerViewTaskAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewCollectionAdapter";
    private Context mContext;

    public RecyclerViewTaskAdapter(Context mContext) {

        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tasks, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d(TAG, "onBindViewHolder: called.");
        Task load = DataManager.getInstance().tasks.get(position);
        holder.taskName.setText(load.getTaskName());
        System.out.println("Loading progress of " + load.getTaskName() + "\nProgress at: " + load.getCompletion() *100  );
        int perc =load.getCompletion();
        holder.completion.setProgress(perc);
        if (holder.completion.getProgress()<=0){
            holder.incompleat.setVisibility(View.VISIBLE);
            holder.compleat.setVisibility(View.INVISIBLE);
            holder.percentage.setVisibility(View.INVISIBLE);
        }
        else if (holder.completion.getProgress()>0){
            holder.incompleat.setVisibility(View.INVISIBLE);
            holder.compleat.setVisibility(View.INVISIBLE);
            holder.percentage.setVisibility(View.VISIBLE);
            holder.percentage.setText(holder.completion.getProgress()+"");
        }
        else if (holder.completion.getProgress()>=99){
            holder.incompleat.setVisibility(View.INVISIBLE);
            holder.compleat.setVisibility(View.VISIBLE);
            holder.percentage.setVisibility(View.INVISIBLE);
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Checking Active Item at -"+position+"- \n" + DataManager.getInstance().tasks.get(position) );
                DataManager.getInstance().setActiveTask(load);
                Intent intent = new Intent(mContext, Objectives.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (DataManager.getInstance().tasks == null){
            return 0;
        }
        return DataManager.getInstance().tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView taskName;
        //ImageButton favorite;
        ProgressBar completion;
        TextView percentage;
        ImageView compleat;
        ImageView incompleat;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.task_name);
            compleat = itemView.findViewById(R.id.complete_img);
            percentage = itemView.findViewById(R.id.task_completion_perc);
            incompleat = itemView.findViewById(R.id.incomplete_img);
            //favorite = itemView.findViewById(R.id.favorite_btn);
            completion = itemView.findViewById(R.id.task_completion);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
