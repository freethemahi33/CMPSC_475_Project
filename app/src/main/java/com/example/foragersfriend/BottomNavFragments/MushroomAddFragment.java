package com.example.foragersfriend.BottomNavFragments;

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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foragersfriend.CameraActivity;
import com.example.foragersfriend.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MushroomAddFragment extends Fragment {

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
                        byte[] imageBytes = (byte[]) data.getExtras().get("bytes");
                        if (imageBytes != null) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                            ImageView imageView = requireActivity().findViewById(R.id.imageView);
                            imageView.setImageBitmap(bitmap);
                            imageView.setRotation(90);
                            // Flip the image horizontally
                            imageView.setScaleX(-1);
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_mushroom_add, container, false);


        Button button = rootView.findViewById(R.id.btnCapture);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), CameraActivity.class);
            someActivityResultLauncher.launch(intent);
        });

        getLastLocation();


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