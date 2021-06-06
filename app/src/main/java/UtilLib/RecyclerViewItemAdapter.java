package UtilLib;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SleeplessStudios.modernpocket.CreateItem;
import com.SleeplessStudios.modernpocket.Items;
import com.SleeplessStudios.modernpocket.R;

import ObjectLib.Collectible;
import ObjectLib.ItemCollection;
import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewItemAdapter extends RecyclerView.Adapter<RecyclerViewItemAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewCollectionAdapter";
    private Context mContext;

    public RecyclerViewItemAdapter(Context mContext) {

        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_items,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d(TAG, "onBindViewHolder: called.");
        Collectible load = DataManager.getInstance().getActiveCollection().collectibles.get(position);
        holder.image.setImageBitmap(load.image);
        holder.itemName.setText(load.getName());
        holder.owned.setChecked(load.isOwned);
        holder.favorite.setChecked(load.isFavourite);
        holder.lent.setEnabled(load.isLent());


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DataManager.getInstance().setActiveItem(load);
                Intent intent = new Intent(mContext, CreateItem.class);
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
        TextView itemName;
        CheckBox favorite;
        CheckBox owned;
        ImageButton lent;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById((R.id.item_image));
            itemName = itemView.findViewById(R.id.item_name);
            favorite = itemView.findViewById(R.id.item_favorite_btn);
            owned = itemView.findViewById(R.id.item_checkbox);
            lent = itemView.findViewById(R.id.item_lent_btn);
            //favorite = itemView.findViewById(R.id.favorite_btn);

            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
