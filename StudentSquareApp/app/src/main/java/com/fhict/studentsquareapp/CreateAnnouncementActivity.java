package com.fhict.studentsquareapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateAnnouncementActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 111;
    private static final int WRITE_STORAGE_REQUEST = 222;
    private TextInputEditText titleField;
    private EditText descriptionField;
    private DatabaseReference databaseReference;
    private FloatingActionButton attachButton;
    private Button createButton;
    private String mImageFileLocation;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_announcement);

        databaseReference = FirebaseDatabase.getInstance().getReference("Announcements");

        createButton = (Button) findViewById(R.id.createBtn);
        titleField = (TextInputEditText)findViewById(R.id.titleField);
        descriptionField = (EditText)findViewById(R.id.descriptionField);
        attachButton = (FloatingActionButton) findViewById(R.id.addAttachmentAnnouncement);



        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAnnouncement();
            }
        });

        attachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    dispatchTakePictureIntent();
                }
                else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                            Toast.makeText(getApplicationContext(), "Permission Needed", Toast.LENGTH_LONG).show();
                        }
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //requestPermissions(new String[]{android.Manifest.permission.CAMERA}, CAMERA_REQUEST);
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_REQUEST);
                    }
                }
            }
        });


    }



    public void createAnnouncement() {
        try
        {
            String title = titleField.getText().toString();
            String description = descriptionField.getText().toString();

            if(TextUtils.isEmpty(title)) {
                titleField.setError(getString(R.string.error_field_required));
                return;
            }

            if(TextUtils.isEmpty(description)) {
                descriptionField.setError(getString(R.string.error_field_required));
                return;
            }

            if (this.imageBitmap != null) {
                ByteArrayOutputStream pic = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100,pic);
                byte[] imageBytes = pic.toByteArray();
                String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                //String photoKey = databaseReference.push().getKey();
                //photos.child(photoKey).setValue(imageString);
                Announcement announcement = new Announcement(title, description, imageString);
                databaseReference.push().setValue(announcement);
                finish();


            } else {

                Announcement announcement = new Announcement(title, description);
                databaseReference.push().setValue(announcement);
                finish();
            }



        } catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
//            case CAMERA_REQUEST:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    //dispatchTakePictureIntent();
//                    Toast.makeText(this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(this, "Permission Canceled, your application cannot access CAMERA.", Toast.LENGTH_LONG).show();
//                }
//                break;
            case WRITE_STORAGE_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                    Toast.makeText(this, "Permission Granted, Now your application can write to external storage.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permission Canceled, your application cannot write to external storage.", Toast.LENGTH_LONG).show();
                }
                break;
            default: super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent();
        takePictureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, WRITE_STORAGE_REQUEST);
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//
//            }

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == WRITE_STORAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
        }
    }

//    private File createImageFile() throws IOException {
//
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "IMAGE_" + timeStamp + "_";
//        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//
//        File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);
//        mImageFileLocation = image.getAbsolutePath();
//
//        return image;
//
//    }


}
