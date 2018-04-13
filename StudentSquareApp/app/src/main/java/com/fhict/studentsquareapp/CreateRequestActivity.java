package com.fhict.studentsquareapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class CreateRequestActivity extends AppCompatActivity {

    private static final int WRITE_STORAGE_REQUEST = 222;
    private TextInputEditText titleField;
    private EditText descriptionField;
    private NumberPicker numberPicker;
    private Button createButton;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingActionButton;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        titleField = (TextInputEditText)findViewById(R.id.requestTitleField);
        descriptionField = (EditText)findViewById(R.id.requestDescField);
        numberPicker = (NumberPicker)findViewById(R.id.numberPicker);
        createButton = (Button)findViewById(R.id.requestCreateBtn);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton2);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(200);
        numberPicker.setWrapSelectorWheel(false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Requests");

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRequest();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    dispatchTakePictureIntent();
                }
                else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
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

    private void createRequest() {
        try {
            String id = databaseReference.push().getKey();
            String name = titleField.getText().toString();
            String description = descriptionField.getText().toString();
            Integer points = numberPicker.getValue();

            if(TextUtils.isEmpty(name)) {
                titleField.setError(getString(R.string.error_field_required));
                return;
            }

            if(TextUtils.isEmpty(description)) {
                descriptionField.setError(getString(R.string.error_field_required));
                return;
            }

            if (this.bitmap != null) {
                ByteArrayOutputStream pic = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100,pic);
                byte[] imageBytes = pic.toByteArray();
                String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                Request request = new Request(id, name, description, imageString, points);
                databaseReference.push().setValue(request);
                finish();


            } else {

                Request request = new Request(id, name, description, points);
                databaseReference.child(id).setValue(request);
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

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == WRITE_STORAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
        }
    }
}
