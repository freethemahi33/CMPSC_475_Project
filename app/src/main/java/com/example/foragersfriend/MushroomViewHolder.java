package com.example.foragersfriend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MushroomViewHolder extends RecyclerView.ViewHolder{


    public ImageView mushroomImage;
    public TextView mushroomName, mushroomLastSeen;

    public Button detailsButton;

    public MushroomViewHolder(@NonNull View itemView) {
        super(itemView);
        mushroomImage = itemView.findViewById(R.id.mushroom_image);
        mushroomName = itemView.findViewById(R.id.mushroom_name);
        mushroomLastSeen = itemView.findViewById(R.id.mushroom_last_seen);
        detailsButton = itemView.findViewById(R.id.mushroom_button);
    }
}
