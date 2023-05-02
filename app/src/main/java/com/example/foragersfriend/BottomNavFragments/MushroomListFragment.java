package com.example.foragersfriend.BottomNavFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foragersfriend.MushroomRVListItem;
import com.example.foragersfriend.MushroomViewAdapter;
import com.example.foragersfriend.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MushroomListFragment extends Fragment {

    public MushroomListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mushroom_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MushroomViewAdapter(MushroomRVListItem.getDummyList(getContext())));
        return view;
    }
}