package com.assignment.boris_kunda_assignment_15320.ui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.assignment.boris_kunda_assignment_15320.R;
import com.assignment.boris_kunda_assignment_15320.model.Movie;
import com.assignment.boris_kunda_assignment_15320.viewmodel.MovieViewModel;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.Gson;

import java.io.IOException;

public class QRActivity extends AppCompatActivity {


    private SurfaceView mSurfaceView;
    private BarcodeDetector mBarcodeDetector;
    private CameraSource mCameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private MovieViewModel mMovieViewModel;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r);

        initViews();
        initialiseDetectorsAndSources();
        initViewModel();

    }

    private void initViews () {
        mSurfaceView = findViewById(R.id.surfaceView);
    }

    private void initialiseDetectorsAndSources () {

        mBarcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        mCameraSource = new CameraSource.Builder(this, mBarcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated (SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(QRActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        mCameraSource.start(mSurfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(QRActivity.this, new
                                String[]{ Manifest.permission.CAMERA }, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged (SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed (SurfaceHolder holder) {
                mCameraSource.stop();
            }
        });

        mBarcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            public void release () {
                //Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections (Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    Gson gson = new Gson();
                    Movie qrMovie = gson.fromJson(barcodes.valueAt(0).displayValue, Movie.class);
                    mMovieViewModel.updateDbOrDisplayPopUp(qrMovie);
                    finish();
                }
            }
        });
    }


    @Override
    protected void onPause () {
        super.onPause();
        mCameraSource.release();
    }

    @Override
    protected void onResume () {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    private void initViewModel () {
        mMovieViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MovieViewModel.class);
    }

}
