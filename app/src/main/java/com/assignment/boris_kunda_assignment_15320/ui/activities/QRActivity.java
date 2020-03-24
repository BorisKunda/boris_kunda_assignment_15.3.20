package com.assignment.boris_kunda_assignment_15320.ui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.assignment.boris_kunda_assignment_15320.AppUtils;
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
    private CameraSource mCameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private MovieViewModel mMovieViewModel;
    private BarcodeDetector mBarcodeDetector;
    private SurfaceHolder mSurfaceHolder;
    private boolean mIsPermissionGranted;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r);

        initViews();
        initMainComponents();
        listenToSurfaceUpdates();
        receiveBarcodeDetections();
        checkCameraPermission();
        getSupportActionBar().hide();
        AppUtils.setStatusBarColor(getWindow(), getResources());
    }

    private void initViews () {
        mSurfaceView = findViewById(R.id.surfaceView);
    }

    private void initMainComponents () {

        mBarcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        mCameraSource = new CameraSource.Builder(this, mBarcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        mMovieViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MovieViewModel.class);

    }

    private void listenToSurfaceUpdates () {
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated (SurfaceHolder holder) {
                mSurfaceHolder = holder;

                if (mIsPermissionGranted) {
                    try {
                        mCameraSource.start(holder);
                    } catch (IOException iE) {
                        iE.printStackTrace();
                    }
                }


            }

            @Override
            public void surfaceChanged (SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed (SurfaceHolder holder) {
                mSurfaceHolder = null;
                mCameraSource.stop();
            }
        });

    }

    private void receiveBarcodeDetections () {


        mBarcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            public void release () {
            }

            @Override
            public void receiveDetections (Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    Gson gson = new Gson();
                    Movie qrMovie = gson.fromJson(barcodes.valueAt(0).displayValue, Movie.class);
                    mMovieViewModel.updateDbOrDisplayPopUp(qrMovie);
                    mBarcodeDetector.release();
                    finish();
                }
            }
        });
    }


    @Override
    protected void onPause () {
        super.onPause();
    }

    @Override
    protected void onResume () {
        super.onResume();
    }

    private void checkCameraPermission () {

        if (ActivityCompat.checkSelfPermission(QRActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            mIsPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(QRActivity.this, new
                    String[]{ Manifest.permission.CAMERA }, REQUEST_CAMERA_PERMISSION);
            mIsPermissionGranted = false;
        }

    }

    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {

            if (grantResults.length > 0 && grantResults[ 0 ] == PackageManager.PERMISSION_GRANTED) {

                try {
                    mCameraSource.start(mSurfaceHolder);
                } catch (IOException iE) {
                    iE.printStackTrace();
                }

            } else {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

                    new AlertDialog.Builder(this).
                            setIcon(R.drawable.alert_ic).
                            setTitle("Permission Required").
                            setMessage("Current feature is not available without Camera Permission").
                            setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(QRActivity.this, new
                                    String[]{ Manifest.permission.CAMERA }, REQUEST_CAMERA_PERMISSION)).
                            setNegativeButton("CANCEL", (dialog, which) -> finish()).create().show();

                }


            }

        }
    }

}
