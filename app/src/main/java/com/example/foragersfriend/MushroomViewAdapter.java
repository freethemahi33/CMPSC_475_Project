package com.example.foragersfriend;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
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
        holder.mushroomImage.setImageResource(mushroomRVListItems.get(position).getImage());
        holder.mushroomName.setText(mushroomRVListItems.get(position).getName());
        holder.mushroomLastSeen.setText(mushroomRVListItems.get(position).getLastSeen());
        holder.detailsButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MushroomDetail.class);
            intent.putExtra("mushroomName", mushroomRVListItems.get(position).getName());
            intent.putExtra("mushroomImage", mushroomRVListItems.get(position).getImage());
            intent.putExtra("mushroomLastSeen", mushroomRVListItems.get(position).getLastSeen());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mushroomRVListItems.size();
    }
}
