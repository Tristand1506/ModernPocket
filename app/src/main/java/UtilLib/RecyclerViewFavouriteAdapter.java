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
import com.SleeplessStudios.modernpocket.R;

import java.util.Date;

import ObjectLib.Collectible;
import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerViewFavouriteAdapter extends RecyclerView.Adapter<RecyclerViewFavouriteAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewCollectionAdapter";
    private Context mContext;

    public RecyclerViewFavouriteAdapter(Context mContext) {

        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_favourites,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d(TAG, "onBindViewHolder: called.");
        Collectible load = DataManager.getInstance().getFavourites().get(position);
        holder.itemName.setText(load.getName());
        holder.favorite.setChecked(load.isFavourite());
        //holder.lent.setEnabled(load.isLent());

        holder.favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DataManager.getInstance().setActiveItem(load);
                DataManager.getInstance().setActiveCollectionFromId(load.getCollectionId());
                System.out.println("owned is now " + isChecked + "\nnow updating" );
                load.setFavourite(isChecked);
                System.out.println(load.toString());
                DataManager.getInstance().AddOrUpdateItem(load);
                DataManager.getInstance().setActiveItem(null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return DataManager.getInstance().getFavourites().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        CheckBox favorite;
        //ImageButton lent;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.fav_item_name);
            favorite = itemView.findViewById(R.id.fav_favorite_btn);
            //lent = itemView.findViewById(R.id.);
            //favorite = itemView.findViewById(R.id.favorite_btn);

            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}