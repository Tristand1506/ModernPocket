package UtilLib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SleeplessStudios.modernpocket.R;

import ObjectLib.Collectible;

public class RecyclerViewLentsAdapter extends RecyclerView.Adapter<RecyclerViewLentsAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewCollectionAdapter";
    private Context mContext;

    public RecyclerViewLentsAdapter(Context mContext) {

        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lent_items,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d(TAG, "onBindViewHolder: called.");
        Collectible load = DataManager.getInstance().getLent().get(position);
        holder.itemInfo.setText(load.getName()+"\nTo: " + load.getBorrowedTo() + "\n Return: " + load.getExpectedReturn());

    }

    @Override
    public int getItemCount() {
        return DataManager.getInstance().getLent().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemInfo;
        ImageView lent;
        //ImageButton lent;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemInfo = itemView.findViewById(R.id.lent_item_name);
            lent = itemView.findViewById(R.id.lent_icon);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
