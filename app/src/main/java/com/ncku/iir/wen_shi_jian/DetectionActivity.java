package com.ncku.iir.wen_shi_jian;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.contract.Emotion;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FacialHair;
import com.microsoft.projectoxford.face.contract.HeadPose;
import com.microsoft.projectoxford.face.contract.Accessory;
import com.microsoft.projectoxford.face.contract.Blur;
import com.microsoft.projectoxford.face.contract.Exposure;
import com.microsoft.projectoxford.face.contract.Hair;
import com.microsoft.projectoxford.face.contract.Makeup;
import com.microsoft.projectoxford.face.contract.Noise;
import com.microsoft.projectoxford.face.contract.Occlusion;

import com.ncku.iir.wen_shi_jian.helper.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetectionActivity extends AppCompatActivity {
    // Background task of face detection.
    private class DetectionTask extends AsyncTask<InputStream, String, Face[]> {
        private boolean mSucceed = true;

        @Override
        protected Face[] doInBackground(InputStream... params) {
            // Get an instance of face service client to detect faces in image.
            FaceServiceClient faceServiceClient = SampleApp.getFaceServiceClient();

            if(faceServiceClient != null) {
                Log.d("DetectionActivity", "faceServiceClient not null");
            } else {
                Log.d("DetectionActivity", "faceServiceClient is null");
            }

            try {
                publishProgress("Detecting...");
                Log.d("DetectionActivity", "back service detecting...");
                Log.d("DetectionActivity", "back service inputStream: " + params[0]);

                // Start detection.
                return faceServiceClient.detect(
                        params[0],  /* Input stream of image to detect */
                        true,       /* Whether to return face ID */
                        true,       /* Whether to return face landmarks */
                        /* Which face attributes to analyze, currently we support:
                           age,gender,headPose,smile,facialHair */
                        new FaceServiceClient.FaceAttributeType[] {
                                FaceServiceClient.FaceAttributeType.Age,
                                FaceServiceClient.FaceAttributeType.Gender,
                                FaceServiceClient.FaceAttributeType.Smile,
                                FaceServiceClient.FaceAttributeType.Glasses,
                                FaceServiceClient.FaceAttributeType.FacialHair,
                                FaceServiceClient.FaceAttributeType.Emotion,
                                FaceServiceClient.FaceAttributeType.HeadPose,
                                FaceServiceClient.FaceAttributeType.Accessories,
                                FaceServiceClient.FaceAttributeType.Blur,
                                FaceServiceClient.FaceAttributeType.Exposure,
                                FaceServiceClient.FaceAttributeType.Hair,
                                FaceServiceClient.FaceAttributeType.Makeup,
                                FaceServiceClient.FaceAttributeType.Noise,
                                FaceServiceClient.FaceAttributeType.Occlusion
                        });
            } catch (Exception e) {
                mSucceed = false;
                publishProgress(e.getMessage());
//                addLog(e.getMessage());
                Log.d("DetectionActivity", "back service error: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            mProgressDialog.show();
//            addLog("Request: Detecting in image " + mImageUri);
            Log.d("DetectionActivity", "Request: Detecting in image " + mImageUri);
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            mProgressDialog.setMessage(progress[0]);
//            setInfo(progress[0]);
            Log.d("DetectionActivity", "Progress update: " + progress[0]);
        }

        @Override
        protected void onPostExecute(Face[] result) {
            Log.d("DetectionActivity", "onPostExecute: " + mSucceed);
            if (mSucceed) {
//                addLog("Response: Success. Detected " + (result == null ? 0 : result.length)
//                        + " face(s) in " + mImageUri);
                Log.d("DetectionActivity", "Response: Success. Detected " + (result == null ? 0 : result.length)
                        + " face(s) in " + mImageUri);
            }

            // Show the result on screen when detection is done.
            setUiAfterDetection(result, mSucceed);
        }
    }

    // Flag to indicate which task is to be performed.
    private static final int REQUEST_SELECT_IMAGE = 0;

    // The URI of the image selected to detect.
    private Uri mImageUri;

    // The image selected to detect.
    private Bitmap mBitmap;

    // Progress dialog popped up when communicating with server.
    ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("progress_dialog_title");

        selectImage(); // Take picture from user
    }

    // Called when the "Select Image" button is clicked.
    public void selectImage() {
        Intent intent = new Intent(this, SelectImageActivity.class);
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }

    // Called when image selection is done.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_SELECT_IMAGE:
                if (resultCode == RESULT_OK) {
                    Log.d("DetectionActivity", "get result code from SelectImageActivity");
                    // If image is selected successfully, set the image URI and bitmap.
                    mImageUri = data.getData(); // Exactly get image from other Activity --> checked
                    Log.d("DetectionActivity", "mImageUri: " + mImageUri);
                    Log.d("DetectionActivity", "external-path: " + Environment.getExternalStorageDirectory());

                    mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
                            mImageUri, getContentResolver());

                    if (mBitmap != null) {
                        Log.d("DetectionActivity", "mBitmap is not null --> correct");
                        // Show the image on screen.
                        ImageView imageView = (ImageView) findViewById(R.id.image);
                        imageView.setImageBitmap(mBitmap);

                        // Add detection log.
//                        addLog("Image: " + mImageUri + " resized to " + mBitmap.getWidth()
//                                + "x" + mBitmap.getHeight());
                        Log.d("DetectionActivity", "Image: " + mImageUri + " resized to " + mBitmap.getWidth()
                                + "x" + mBitmap.getHeight());
                    } else {
                        Log.d("DetectionActivity", "mBitmap is null --> error");
                    }

//                    // Clear the detection result.
//                    FaceListAdapter faceListAdapter = new FaceListAdapter(null);
//                    ListView listView = (ListView) findViewById(R.id.list_detected_faces);
//                    listView.setAdapter(faceListAdapter);
//
//                    // Clear the information panel.
//                    setInfo("");
//
//                    // Enable button "detect" as the image is selected and not detected.
//                    setDetectButtonEnabledStatus(true);
                }
                break;
            default:
                break;
        }
    }

    // Save the activity state when it's going to stop.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("ImageUri", mImageUri);
    }

    // Recover the saved state when the activity is recreated.
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mImageUri = savedInstanceState.getParcelable("ImageUri");
        if (mImageUri != null) {
            mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
                    mImageUri, getContentResolver());
        }
    }

    // Called when the "Detect" button is clicked.
    public void detect(View view) {
        Log.d("DetectionActivity", "backend start");
        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        // Start a background task to detect faces in the image.
        new DetectionTask().execute(inputStream);

        // Prevent button click during detecting.
//        setAllButtonsEnabledStatus(false);
    }

    // Show the result on screen when detection is done.
    private void setUiAfterDetection(Face[] result, boolean succeed) {
        // Detection is done, hide the progress dialog.
        mProgressDialog.dismiss();

        // Enable all the buttons.
//        setAllButtonsEnabledStatus(true);

        // Disable button "detect" as the image has already been detected.
//        setDetectButtonEnabledStatus(false);

        if (succeed) {
            // The information about the detection result.
            String detectionResult;
            if (result != null) {
                detectionResult = result.length + " face"
                        + (result.length != 1 ? "s" : "") + " detected";

                // Show the detected faces on original image.
                ImageView imageView = (ImageView) findViewById(R.id.image);
                imageView.setImageBitmap(ImageHelper.drawFaceRectanglesOnBitmap(
                        mBitmap, result, true));

                // Set the adapter of the ListView which contains the details of the detected faces.
//                FaceListAdapter faceListAdapter = new FaceListAdapter(result);
//
//                // Show the detailed list of detected faces.
//                ListView listView = (ListView) findViewById(R.id.list_detected_faces);
//                listView.setAdapter(faceListAdapter);
            } else {
                detectionResult = "0 face detected";
            }
//            setInfo(detectionResult);
        }

        mImageUri = null;
        mBitmap = null;
    }

    public static String getFilePathFromContentUri(Uri selectedVideoUri,
                                                   ContentResolver contentResolver) {
        String filePath;
        String[] filePathColumn = {MediaStore.Images.ImageColumns.ORIENTATION};

        Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null);
//      也可用下面的方法拿到cursor
//      Cursor cursor = this.context.managedQuery(selectedVideoUri, filePathColumn, null, null, null);

        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        Log.d("test", filePath);
        return filePath;
    }

}
