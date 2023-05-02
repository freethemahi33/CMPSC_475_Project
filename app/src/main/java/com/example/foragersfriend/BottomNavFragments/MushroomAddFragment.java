package com.example.foragersfriend.BottomNavFragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.foragersfriend.AppDatabase;
import com.example.foragersfriend.MainActivity;
import com.example.foragersfriend.Mushroom;
import com.example.foragersfriend.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.hardware.camera2.TotalCaptureResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MushroomAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MushroomAddFragment extends Fragment implements TextureView.SurfaceTextureListener {

    private TextureView textureView;

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);


    //Check state orientation of output image
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0,90);
        ORIENTATIONS.append(Surface.ROTATION_90,0);
        ORIENTATIONS.append(Surface.ROTATION_180,270);
        ORIENTATIONS.append(Surface.ROTATION_270,180);
    }

    private CameraDevice cameraDevice;
    private CameraCaptureSession cameraCaptureSessions;
    private CaptureRequest.Builder captureRequestBuilder;
    private ImageReader imageReader;

    private static final int REQUEST_STORAGE_PERMISSION = 2;

    //Save to FILE
    private File file;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private boolean mFlashSupported;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;

    CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            cameraDevice.close();
        }
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_mushroom_add, container, false);

        textureView = rootView.findViewById(R.id.textureView);
        assert textureView != null;
        textureView.setSurfaceTextureListener(this);
        Button btnCapture = rootView.findViewById(R.id.btnCapture);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        // Request permissions
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        }

        EditText nameEditText = rootView.findViewById(R.id.mushroom_name_edit_text);
        EditText locationEditText = rootView.findViewById(R.id.mushroom_location_edit_text);
        EditText dateEditText = rootView.findViewById(R.id.mushroom_date_edit_text);
        EditText descriptionEditText = rootView.findViewById(R.id.mushroom_description_edit_text);
        RadioGroup seasonRadioGroup = rootView.findViewById(R.id.radio_group_season);
        CheckBox toxicityCheckBox = rootView.findViewById(R.id.toxicity_check_box);
        EditText typeEditText = rootView.findViewById(R.id.mushroom_type_edit_text);
        Button addButton = rootView.findViewById(R.id.add_mushroom_button);

        addButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String location = locationEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();
            RadioButton checkedRadioButton = rootView.findViewById(seasonRadioGroup.getCheckedRadioButtonId());
            String season = checkedRadioButton != null ? checkedRadioButton.getText().toString() : "";
            boolean isToxic = toxicityCheckBox.isChecked();
            String type = typeEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();

            if (name.isEmpty() || location.isEmpty() || date.isEmpty() || season.isEmpty() || type.isEmpty() || description.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Mushroom mushroom = new Mushroom(name, description, location, date, season, isToxic, type);

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
                    descriptionEditText.setText("");

                    Toast.makeText(getContext(), "Mushroom added successfully", Toast.LENGTH_SHORT).show();
                });
            });
        });

        return rootView;
    }

    private void takePicture() {
        if(cameraDevice == null)
            return;
        CameraManager manager = (CameraManager)requireActivity().getSystemService(Context.CAMERA_SERVICE);
        try{
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());
            Size[] jpegSizes = null;
            if(characteristics != null)
                jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                        .getOutputSizes(ImageFormat.JPEG);

            //Capture image with custom size
            int width = 640;
            int height = 480;
            if(jpegSizes != null && jpegSizes.length > 0)
            {
                width = jpegSizes[0].getWidth();
                height = jpegSizes[0].getHeight();
            }
            final ImageReader reader = ImageReader.newInstance(width,height,ImageFormat.JPEG,1);
            List<Surface> outputSurface = new ArrayList<>(2);
            outputSurface.add(reader.getSurface());
            outputSurface.add(new Surface(textureView.getSurfaceTexture()));

            final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(reader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);

            //Check orientation base on device
            int rotation = requireActivity().getWindowManager().getDefaultDisplay().getRotation();
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION,ORIENTATIONS.get(rotation));

            file = new File(Environment.getExternalStorageDirectory()+"/"+ UUID.randomUUID().toString()+".jpg");
            ImageReader.OnImageAvailableListener readerListener = new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader imageReader) {
                    Image image = null;
                    try{
                        image = reader.acquireLatestImage();
                        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                        byte[] bytes = new byte[buffer.capacity()];
                        buffer.get(bytes);
                        save(bytes);

                    }
                    catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    finally {
                        {
                            if(image != null)
                                image.close();
                        }
                    }
                }
                private void save(byte[] bytes) throws IOException {
                    OutputStream outputStream = null;
                    try{
                        outputStream = new FileOutputStream(file);
                        outputStream.write(bytes);
                    }finally {
                        if(outputStream != null)
                            outputStream.close();
                    }
                }
            };

            reader.setOnImageAvailableListener(readerListener,mBackgroundHandler);
            final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                    Toast.makeText(requireContext(), "Saved "+file, Toast.LENGTH_SHORT).show();
                    createCameraPreview();
                }
            };

            cameraDevice.createCaptureSession(outputSurface, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    try{
                        cameraCaptureSession.capture(captureBuilder.build(),captureListener,mBackgroundHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                }
            },mBackgroundHandler);


        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void createCameraPreview() {
        try{
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert  texture != null;

            Surface surface = new Surface(texture);
//            texture.setDefaultBufferSize(imageDimension.getWidth(),imageDimension.getHeight());
//            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Collections.singletonList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    if(cameraDevice == null)
                        return;
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(getActivity(), "Changed", Toast.LENGTH_SHORT).show();
                }
            },null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void updatePreview() {
        if(cameraDevice == null)
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
        try{
            captureRequestBuilder.set(CaptureRequest.CONTROL_MODE,CaptureRequest.CONTROL_MODE_AUTO);
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(),null,mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        CameraManager manager = (CameraManager)requireActivity().getSystemService(Context.CAMERA_SERVICE);
        try{
            String cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            Size imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            //Check realtime permission if run higher API 23
            if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(requireActivity(),new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraId,stateCallback,null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == REQUEST_CAMERA_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, initialize camera and texture view
//                openCamera();
//            } else {
//                // Permission denied, show a toast or a dialog explaining why you need the permission
//                Toast.makeText(getActivity(), "Camera permission is required to take pictures", Toast.LENGTH_SHORT).show();
//            }
//        }
//        if (requestCode == REQUEST_STORAGE_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, continue with your code
//                // ...
//            } else {
//                // Permission denied, show a toast or a dialog explaining why you need the permission
//                Toast.makeText(getActivity(), "Storage permission is required to save pictures", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    }

    @Override
    public void onResume() {
        super.onResume();
        startBackgroundThread();
        if(textureView.isAvailable())
            openCamera();
        else
            textureView.setSurfaceTextureListener(this);
    }

    @Override
    public void onPause() {
        stopBackgroundThread();
        super.onPause();
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try{
            mBackgroundThread.join();
            mBackgroundThread= null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }


    @Override
    public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {
        openCamera();
    }

    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
        // release the CameraPreview resources
        closeCamera();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {

    }

    private void closeCamera() {
        //...
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }

//    @Override
//    public View onCreateMushroomAddFragmentView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_mushroom_add, container, false);
//
//        EditText nameEditText = view.findViewById(R.id.mushroom_name_edit_text);
//        EditText locationEditText = view.findViewById(R.id.mushroom_location_edit_text);
//        EditText dateEditText = view.findViewById(R.id.mushroom_date_edit_text);
//        RadioGroup seasonRadioGroup = view.findViewById(R.id.radio_group_season);
//        CheckBox toxicityCheckBox = view.findViewById(R.id.toxicity_check_box);
//        EditText typeEditText = view.findViewById(R.id.mushroom_type_edit_text);
//        Button addButton = view.findViewById(R.id.add_mushroom_button);
//
//        addButton.setOnClickListener(v -> {
//            String name = nameEditText.getText().toString().trim();
//            String location = locationEditText.getText().toString().trim();
//            String date = dateEditText.getText().toString().trim();
//            RadioButton checkedRadioButton = view.findViewById(seasonRadioGroup.getCheckedRadioButtonId());
//            String season = checkedRadioButton != null ? checkedRadioButton.getText().toString() : "";
//            boolean isToxic = toxicityCheckBox.isChecked();
//            String type = typeEditText.getText().toString().trim();
//
//            if (name.isEmpty() || location.isEmpty() || date.isEmpty() || season.isEmpty() || type.isEmpty()) {
//                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            Mushroom mushroom = new Mushroom(name, location, date, season, isToxic, type);
//
//            AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "my-db").allowMainThreadQueries().build();
//            // Create a call back for when the database is created and then insert the mushroom
//            executorService.execute(() -> {
//                db.mushroomDao().insert(mushroom);
//                List<Mushroom> mushrooms = Arrays.asList(db.mushroomDao().getAll());
//
//                for (Mushroom m : mushrooms) {
//                    Log.d("Database Contents", m.toString());
//                }
//                getActivity().runOnUiThread(() -> {
//                    nameEditText.setText("");
//                    locationEditText.setText("");
//                    dateEditText.setText("");
//                    seasonRadioGroup.clearCheck();
//                    toxicityCheckBox.setChecked(false);
//                    typeEditText.setText("");
//
//                    Toast.makeText(getContext(), "Mushroom added successfully", Toast.LENGTH_SHORT).show();
//                });
//            });
//        });
//        return view;
//    }
}