package UtilLib;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.SleeplessStudios.modernpocket.CreateItem;
import com.SleeplessStudios.modernpocket.R;

import java.util.Date;

import ObjectLib.Collectible;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewItemAdapter extends RecyclerView.Adapter<RecyclerViewItemAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewCollectionAdapter";
    private Context mContext;
    private String filter;
    public RecyclerViewItemAdapter(Context mContext) {

        this.mContext = mContext;
    }
    public RecyclerViewItemAdapter(Context mContext, String Filter) {
        this.filter = Filter;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_items,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d(TAG, "onBindViewHolder: called.");
        Collectible load;
        if (filter!=null){
            load = DataManager.getInstance().getActiveCollection(filter).getCollectibles().get(position);
        }
        else {
            load = DataManager.getInstance().getActiveCollection().getCollectibles().get(position);
        }
        System.out.println("loading item: "+ load.getName() );
        holder.image.setImageBitmap(load.getImageBitmap());
        holder.itemName.setText(load.getName());
        System.out.println("Favorite: " + load.isFavourite() + "\nIs Owned: "+load.isOwned());
        holder.owned.setChecked(load.isOwned());
        holder.favorite.setChecked(load.isFavourite());
        if (load.isLent()){
            holder.lent.setVisibility(View.VISIBLE);
        }
        else {
            holder.lent.setVisibility(View.INVISIBLE);
        }


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DataManager.getInstance().setActiveItem(load);
                Intent intent = new Intent(mContext, CreateItem.class);
                mContext.startActivity(intent);
            }
        } );

        holder.owned.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DataManager.getInstance().setActiveItem(load);
                System.out.println("owned is now " + isChecked + "\nnow updating" );
                if (isChecked) {
                    load.setAcquisitionDateWithDate((new Date()));
                    load.setAcquisitionLoc(new Location(LocationManager.GPS_PROVIDER).toString());
                }
                else{
                    load.setAcquisitionDateWithDate(null);
                    load.setAcquisitionLoc(null);
                }
                System.out.println(load.toString());
                DataManager.getInstance().AddOrUpdateItem(load);
                //DataManager.getInstance().setActiveCollection(DataManager.getInstance().getActiveCollection());
                DataManager.getInstance().setActiveItem(null);
            }
        });
        holder.favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DataManager.getInstance().setActiveItem(load);
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
        return DataManager.getInstance().getActiveCollection().getCollectibles().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView itemName;
        CheckBox favorite;
        CheckBox owned;
        ImageView lent;
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

