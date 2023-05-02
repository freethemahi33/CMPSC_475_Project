package com.example.foragersfriend;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MushroomViewAdapter extends RecyclerView.Adapter<MushroomViewHolder> {

    private final List<MushroomRVListItem> mushroomRVListItems;

    public MushroomViewAdapter(List<MushroomRVListItem> mushroomRVListItems) {
        this.mushroomRVListItems = mushroomRVListItems;
    }

    @NonNull
    @Override
    public MushroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MushroomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mushroom_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MushroomViewHolder holder, int position) {
        byte[] imageBytes = mushroomRVListItems.get(position).getImageBytes();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.mushroomImage.setImageBitmap(bitmap);
        holder.mushroomImage.setScaleY(-1);
        holder.mushroomImage.setRotation(270);
        holder.mushroomName.setText(mushroomRVListItems.get(position).getName());
        holder.mushroomLastSeen.setText(mushroomRVListItems.get(position).getDate());
        holder.detailsButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MushroomDetail.class);
            intent.putExtra("mushroomId", mushroomRVListItems.get(position).getId());
            intent.putExtra("mushroomImage", mushroomRVListItems.get(position).getImageBytes());
            intent.putExtra("mushroomName", mushroomRVListItems.get(position).getName());
            intent.putExtra("mushroomLastSeen", mushroomRVListItems.get(position).getDate());
            intent.putExtra("mushroomDescription", mushroomRVListItems.get(position).getDescription());
            intent.putExtra("mushroomToxicity", mushroomRVListItems.get(position).getIsToxic());
            intent.putExtra("mushroomSeason", mushroomRVListItems.get(position).getSeason());
            intent.putExtra("mushroomType", mushroomRVListItems.get(position).getType());
            intent.putExtra("mushroomLocation", mushroomRVListItems.get(position).getLocation());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mushroomRVListItems.size();
    }
}