package com.tactlab.cloudamendsample.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.tactlab.cloudamend.Amend;
import com.tactlab.cloudamend.AmendListener;
import com.tactlab.cloudamend.AmendOptions;
import com.tactlab.cloudamend.Transform;
import com.tactlab.cloudamendsample.R;
import com.tactlab.cloudamendsample.utils.SharedPreference;
import com.tactlab.cloudamendsample.utils.Support;
import com.trend.progress.ProgressDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import in.trend.crop.CropImage;


public class Dashboard extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static final int CAMERA_REQUEST = 11;
    private static final int GALLERY_REQUEST = 12;
    private static final int CROP_REQUEST = 13;
    ImageView ivMasterImage;
    String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    private ProgressDialog pd;
    private SharedPreference sp;
    private File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Add your amend key here
        //Amend.setAmendName("your-amendName");
        //Amend.setCredentials("your-amendKey", "your-amendSecret");

        setContentView(R.layout.dashboard);

        pd = new ProgressDialog(Dashboard.this);
        pd.setTranslucent();
        sp = new SharedPreference(Dashboard.this);
        initToolBar("Amend Sample");

        findViews();
        checkPermissions();
    }

    // initializing the toolbar
    private void initToolBar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        setTitle(title);
    }


    private void findViews() {
        ivMasterImage = (ImageView) findViewById(R.id.ivMasterImage);


        Button bCamera = (Button) findViewById(R.id.bCamera);
        Button bGallery = (Button) findViewById(R.id.bGallery);
        Button bContinueLastFile = (Button) findViewById(R.id.bContinueLastFile);
        bCamera.setOnClickListener(this);
        bGallery.setOnClickListener(this);
        bContinueLastFile.setOnClickListener(this);

        if (!sp.getValueString("KEY_IMAGEID").isEmpty()) {
            final AmendOptions options = new AmendOptions().transform(new Transform().width(500).height(500).fit(Amend.FIT_FILL));
            Amend.with(this).load(sp.getValueString("KEY_IMAGEID"), options).into(ivMasterImage);
            bContinueLastFile.setVisibility(View.VISIBLE);
        } else {
            bContinueLastFile.setVisibility(View.GONE);
        }

    }


    private void openAmendOptions() {
        startActivity(new Intent(Dashboard.this, MyAmendOptions.class));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bContinueLastFile:
                openAmendOptions();

                break;
            case R.id.bCamera:
                Intent intent;
                if (checkPermissions()) {
                    try {
                        tempFile = Support.getTempFile(this, Support.TEMP_PROFILE_PIC);
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                        startActivityForResult(intent, CAMERA_REQUEST);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Dashboard.this, "Need Permission", Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.bGallery:
                if (checkPermissions()) {
                    try {
                        tempFile = Support.getTempFile(this, Support.TEMP_PROFILE_PIC);
                        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(intent, GALLERY_REQUEST);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Dashboard.this, "Need Permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GALLERY_REQUEST:
                    try {
                        InputStream is = getContentResolver().openInputStream(data.getData());
                        FileOutputStream fos = new FileOutputStream(tempFile);
                        Support.copyStream(is, fos);
                        fos.close();
                        is.close();
                        Intent intent = new Intent(this, CropImage.class);
                        intent.putExtra(CropImage.IMAGE_PATH, tempFile.getPath());
                        intent.putExtra(CropImage.SCALE, true);
                        intent.putExtra(CropImage.ASPECT_X, 1);
                        intent.putExtra(CropImage.ASPECT_Y, 1);
                        startActivityForResult(intent, CROP_REQUEST);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case CAMERA_REQUEST:
                    Intent intent = new Intent(this, CropImage.class);
                    intent.putExtra(CropImage.IMAGE_PATH, tempFile.getPath());
                    intent.putExtra(CropImage.SCALE, true);
                    intent.putExtra(CropImage.ASPECT_X, 1);
                    intent.putExtra(CropImage.ASPECT_Y, 1);
                    startActivityForResult(intent, CROP_REQUEST);
                    break;

                case CROP_REQUEST:
                    String imagePathNew = data.getStringExtra(CropImage.IMAGE_PATH);
                    if (imagePathNew == null) {
                        return;
                    }

                    ivMasterImage.setImageURI(null);
                    ivMasterImage.setImageURI(Uri.fromFile(new File(imagePathNew)));
                    pd.show();

                    uploadImageToAmend(new File(imagePathNew));


            }
        }

    }


    //common Functions for image processings
    public void uploadImageToAmend(File f) {

        Amend.with(this).upload(1, f, new AmendListener() {
            @Override
            public void onSuccess(int statusCode, int reqCode, String imageId) {
                sp.setValueString("KEY_IMAGEID", imageId);
                findViewById(R.id.bContinueLastFile).setVisibility(View.VISIBLE);
                openAmendOptions();
                pd.dismiss();

            }

            @Override
            public void onError(int statusCode, int reqCode, Exception e) {
                e.printStackTrace();
                Support.showAlertDialog(Dashboard.this, e.toString());
                pd.dismiss();
            }
        });

    }


    //Function used to check runtime permissions are granted to the user or not ,for call and saving contact
    public boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }
    }


}
