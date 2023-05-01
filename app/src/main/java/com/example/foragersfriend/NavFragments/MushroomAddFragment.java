package com.example.foragersfriend.NavFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.room.Room;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



import com.example.foragersfriend.AppDatabase;
import com.example.foragersfriend.Mushroom;
import com.example.foragersfriend.R;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MushroomAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MushroomAddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MushroomAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MushroomAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MushroomAddFragment newInstance(String param1, String param2) {
        MushroomAddFragment fragment = new MushroomAddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mushroom_add, container, false);

        EditText nameEditText = view.findViewById(R.id.mushroom_name_edit_text);
        EditText locationEditText = view.findViewById(R.id.mushroom_location_edit_text);
        EditText dateEditText = view.findViewById(R.id.mushroom_date_edit_text);
        RadioGroup seasonRadioGroup = view.findViewById(R.id.radio_group_season);
        CheckBox toxicityCheckBox = view.findViewById(R.id.toxicity_check_box);
        EditText typeEditText = view.findViewById(R.id.mushroom_type_edit_text);
        Button addButton = view.findViewById(R.id.add_mushroom_button);

        addButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String location = locationEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();
            RadioButton checkedRadioButton = view.findViewById(seasonRadioGroup.getCheckedRadioButtonId());
            String season = checkedRadioButton != null ? checkedRadioButton.getText().toString() : "";
            boolean isToxic = toxicityCheckBox.isChecked();
            String type = typeEditText.getText().toString().trim();

            if (name.isEmpty() || location.isEmpty() || date.isEmpty() || season.isEmpty() || type.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Mushroom mushroom = new Mushroom(name, location, date, season, isToxic, type);

            AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "my-db").allowMainThreadQueries().build();
            // Create a call back for when the database is created and then insert the mushroom
            executorService.execute(() -> {
                db.mushroomDao().insert(mushroom);
                List<Mushroom> mushrooms = Arrays.asList(db.mushroomDao().getAll());

                for (Mushroom m : mushrooms) {
                    Log.d("Database Contents", m.toString());
                }
                getActivity().runOnUiThread(() -> {
                    nameEditText.setText("");
                    locationEditText.setText("");
                    dateEditText.setText("");
                    seasonRadioGroup.clearCheck();
                    toxicityCheckBox.setChecked(false);
                    typeEditText.setText("");

                    Toast.makeText(getContext(), "Mushroom added successfully", Toast.LENGTH_SHORT).show();
                });
            });
        });
        return view;
    }
}
