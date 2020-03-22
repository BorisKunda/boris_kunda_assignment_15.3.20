package com.assignment.boris_kunda_assignment_15320.ui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
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

public class QRActivity extends AppCompatActivity {


    private SurfaceView mSurfaceView;
    private CameraSource mCameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private MovieViewModel mMovieViewModel;
    private BarcodeDetector mBarcodeDetector;
    private SurfaceHolder mSurfaceHolder;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r);

        initViews();
        initMainComponents();
        listenToSurfaceUpdates();
        receiveBarcodeDetections();
        checkCameraPermission();

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
                Log.e("surfaceCreated", "surfaceCreated");
                mSurfaceHolder = holder;


            }

            @Override
            public void surfaceChanged (SurfaceHolder holder, int format, int width, int height) {
                Log.e("surface changed", "surfaceChanged");
            }

            @Override
            public void surfaceDestroyed (SurfaceHolder holder) {
                Log.e("surfaceDestroyed", "surfaceDestroyed");
                mSurfaceHolder = null;
                //   mCameraSource.stop();
            }
        });

    }

    private void receiveBarcodeDetections () {


        mBarcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            public void release () {
                Log.e("release", "release");
            }

            @Override
            public void receiveDetections (Detector.Detections<Barcode> detections) {
                Log.e("release", "release2");

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
        Log.e("onPause", "onPause");
        mCameraSource.release();
    }

    @Override
    protected void onResume () {
        super.onResume();

        //        if (isCameraPerGranted() && mSurfaceHolder != null) {
        //            try {
        //                mCameraSource.start(mSurfaceHolder);
        //            } catch (IOException iE) {
        //                iE.printStackTrace();
        //            }
        //        }

        Log.e("onResume", "onResume");
    }

    private void checkCameraPermission () {

        if (ActivityCompat.checkSelfPermission(QRActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            //    perGranted = true;
        } else {
            ActivityCompat.requestPermissions(QRActivity.this, new
                    String[]{ Manifest.permission.CAMERA }, REQUEST_CAMERA_PERMISSION);
            // perGranted = false;
        }

    }

    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.e("onRequestPermissionsResult", "onRequestPermissionsResult");

        if (requestCode == REQUEST_CAMERA_PERMISSION) {


            //            if (grantResults.length > 0 && grantResults[ 0 ] == PackageManager.PERMISSION_GRANTED) {
            //                try {
            //                   // mCameraSource.start();
            //                } catch (IOException iE) {
            //                    iE.printStackTrace();
            //                }
            //            }
        } else {

        }

    }

}
