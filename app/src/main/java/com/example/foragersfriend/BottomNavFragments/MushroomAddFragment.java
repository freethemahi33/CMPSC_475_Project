package com.example.foragersfriend.BottomNavFragments;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foragersfriend.MushroomDatabase;
import com.example.foragersfriend.CameraActivity;
import com.example.foragersfriend.Mushroom;
import com.example.foragersfriend.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MushroomAddFragment extends Fragment {
    private final ExecutorService executorService = Executors.newFixedThreadPool(8);
    private byte[] imageBytes;

    public MushroomAddFragment() {
        // Required empty public constructor
    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    if (data != null) {
                        imageBytes = (byte[]) data.getExtras().get("bytes");
                        if (imageBytes != null) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                            ImageView imageView = requireActivity().findViewById(R.id.imageView);
                            imageView.setImageBitmap(bitmap);
                            imageView.setRotation(90);
                            Toast.makeText(requireActivity(), "Successfully Image captured", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireActivity(), "Error capturing image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mushroom_add, container, false);

        EditText nameEditText = rootView.findViewById(R.id.mushroom_name_edit_text);
        EditText dateEditText = rootView.findViewById(R.id.mushroom_date_edit_text);
        EditText locationEditText = rootView.findViewById(R.id.mushroom_location_edit_text);
        EditText descriptionEditText = rootView.findViewById(R.id.mushroom_description_edit_text);
        RadioGroup seasonRadioGroup = rootView.findViewById(R.id.radio_group_season);
        CheckBox toxicityCheckBox = rootView.findViewById(R.id.toxicity_check_box);
        EditText typeEditText = rootView.findViewById(R.id.mushroom_type_edit_text);
        Button addButton = rootView.findViewById(R.id.add_mushroom_button);
        Button button = rootView.findViewById(R.id.btnCapture);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), CameraActivity.class);
            someActivityResultLauncher.launch(intent);
        });

        getLastLocation();

        addButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String location = locationEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();
            RadioButton checkedRadioButton = rootView.findViewById(seasonRadioGroup.getCheckedRadioButtonId());
            String season = checkedRadioButton != null ? checkedRadioButton.getText().toString() : "";
            boolean isToxic = toxicityCheckBox.isChecked();
            String type = typeEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();

            if (name.isEmpty() || location.isEmpty() || date.isEmpty() || season.isEmpty() || type.isEmpty() || description.isEmpty() || imageBytes == null) {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Mushroom mushroom = new Mushroom(name, description, imageBytes, location, date, season, isToxic, type);

            MushroomDatabase db = Room.databaseBuilder(requireContext(), MushroomDatabase.class, "my-db").allowMainThreadQueries().build();
            // Create a call back for when the database is created and then insert the mushroom
            executorService.execute(() -> {
                db.mushroomDao().insert(mushroom);
                requireActivity().runOnUiThread(() -> {
                    nameEditText.setText("");
                    dateEditText.setText("");
                    seasonRadioGroup.clearCheck();
                    toxicityCheckBox.setChecked(false);
                    typeEditText.setText("");
                    descriptionEditText.setText("");
                    imageBytes = null;
                    ImageView imageView = requireActivity().findViewById(R.id.imageView);
                    imageView.setImageResource(0);

                    Toast.makeText(getContext(), "Mushroom added successfully", Toast.LENGTH_SHORT).show();
                });
            });
        });

        return rootView;
    }

    private void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        // https://developer.android.com/training/location/retrieve-current.html
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askPermission();
        }
        Task<Location> task = fusedLocationClient.getLastLocation();
        task.addOnFailureListener(e -> Toast.makeText(requireActivity(), "Failed to get location", Toast.LENGTH_SHORT).show());
        task.addOnSuccessListener(location -> {
            if (location != null) {
                TextView textView = requireActivity().findViewById(R.id.mushroom_location_edit_text);
                textView.setText(String.format("%s, %s", location.getLatitude(), location.getLongitude()));
                Toast.makeText(requireActivity(), "Location found", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireActivity(), "Location is null", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10001);
    }
}