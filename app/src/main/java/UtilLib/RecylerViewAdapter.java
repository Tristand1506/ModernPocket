package UtilLib;

 import android.content.Context;
 import android.content.Intent;
 import android.media.Image;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.ImageButton;
 import android.widget.ProgressBar;
 import android.widget.RelativeLayout;
 import android.widget.TextView;

 import androidx.annotation.NonNull;
 import androidx.recyclerview.widget.*;

 import com.SleeplessStudios.modernpocket.Items;
 import com.SleeplessStudios.modernpocket.R;

 import java.util.ArrayList;

 import ObjectLib.ItemCollection;
 import ObjectLib.UserAcount;
 import de.hdodenhof.circleimageview.CircleImageView;


public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder> {
    private static final String TAG = "RecylerViewAdapter";
    private Context mContext;

    public RecylerViewAdapter(Context mContext) {

        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_collection,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        ItemCollection load = DataManager.getInstance().collections.get(position);
        holder.image.setImageBitmap(load.image);
        holder.collectionName.setText(load.getCollectionName());
        //holder.completion.

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DataManager.getInstance().setActiveCollection(load);
                Intent intent = new Intent(mContext, Items.class);
                mContext.startActivity(intent);
            }
        } );
    }

    @Override
    public int getItemCount() {

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
            completion = itemView.findViewById(R.id.compleation);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}