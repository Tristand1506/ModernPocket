package UtilLib;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SleeplessStudios.modernpocket.CreateItem;
import com.SleeplessStudios.modernpocket.CreateObjective;
import com.SleeplessStudios.modernpocket.R;

import java.util.Date;

import ObjectLib.Objective;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewObjectiveAdapter extends RecyclerView.Adapter<RecyclerViewObjectiveAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewObjectiveAdapter";
    private Context mContext;

    public RecyclerViewObjectiveAdapter(Context mContext) {

        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_objectives,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d(TAG, "onBindViewHolder: called.");
        Objective load = DataManager.getInstance().getActiveTask().getObjectives().get(position);
        holder.objectiveName.setText(load.getObjectiveName());
        //System.out.println("Favorite: " + load.isFavourite() + "\nIs Owned: "+load.isOwned());
        holder.complete.setChecked(load.isComplete());


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DataManager.getInstance().setActiveObjective(load);
                Intent intent = new Intent(mContext, CreateObjective.class);
                mContext.startActivity(intent);
            }
        } );

        holder.complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DataManager.getInstance().setActiveObjective(load);
                System.out.println("owned is now " + isChecked + "\nnow updating" );
                if (isChecked) {
                    load.setComplete(isChecked);
                }
                else{
                    load.setComplete(isChecked);
                }
                System.out.println(load.toString());
                DataManager.getInstance().AddOrUpdateObjective(load);
                //DataManager.getInstance().setActiveCollection(DataManager.getInstance().getActiveCollection());
                DataManager.getInstance().setActiveObjective(null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return DataManager.getInstance().getActiveTask().getObjectives().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView objectiveName;
        CheckBox complete;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            objectiveName = itemView.findViewById(R.id.objective_name);
            complete = itemView.findViewById(R.id.objective_checkbox);
            //favorite = itemView.findViewById(R.id.favorite_btn);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
