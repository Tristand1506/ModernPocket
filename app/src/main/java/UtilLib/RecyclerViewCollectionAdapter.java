package UtilLib;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.*;

import com.SleeplessStudios.modernpocket.Items;
import com.SleeplessStudios.modernpocket.R;

import ObjectLib.ItemCollection;
import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewCollectionAdapter extends RecyclerView.Adapter<RecyclerViewCollectionAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewCollectionAdapter";
    private Context mContext;

    public RecyclerViewCollectionAdapter(Context mContext) {

        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_collection, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d(TAG, "onBindViewHolder: called.");
        ItemCollection load = DataManager.getInstance().collections.get(position);
        holder.image.setImageBitmap(load.getImageBitmap());
        holder.collectionName.setText(load.getCollectionName());
        System.out.println("Loading progress of " + load.getCollectionName() + "\nProgress at: " + load.getCompletion()*100  );
        holder.completion.setProgress(load.getCompletion());

        holder.parentLayout.setOnClickListener(v -> {
            DataManager.getInstance().setActiveCollection(load);
            Intent intent = new Intent(mContext, Items.class);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (DataManager.getInstance().collections == null){
            return 0;
        }
        return DataManager.getInstance().collections.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView collectionName;
        //ImageButton favorite;
        ProgressBar completion;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById((R.id.profile_image));
            collectionName = itemView.findViewById(R.id.collection_name);
            //favorite = itemView.findViewById(R.id.favorite_btn);
            completion = itemView.findViewById(R.id.coll_completion);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
