package UtilLib;

 import android.content.Context;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.RelativeLayout;
 import android.widget.TextView;

 import androidx.annotation.NonNull;
 import androidx.recyclerview.widget.*;

 import com.SleeplessStudios.modernpocket.R;

 import java.util.ArrayList;

 import de.hdodenhof.circleimageview.CircleImageView;


public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder> {
    private static final String TAG = "RecylerViewAdapter";

    private ArrayList<String> mCollectionNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    public RecylerViewAdapter(Context mContext, ArrayList<String> mCollectionNames, ArrayList<String> mImages ) {
        this.mCollectionNames = mCollectionNames;
        this.mImages = mImages;
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


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView collectionName;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById((R.id.image));
            collectionName = itemView.findViewById(R.id.collection_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
