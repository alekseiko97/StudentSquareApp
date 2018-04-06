package com.fhict.studentsquareapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAnnouncementActivity extends AppCompatActivity {

    private TextInputEditText titleField;
    private EditText descriptionField;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_announcement);

        databaseReference = FirebaseDatabase.getInstance().getReference("Announcements");

        Button createBtn = (Button) findViewById(R.id.createBtn);
        titleField = (TextInputEditText)findViewById(R.id.titleField);
        descriptionField = (EditText)findViewById(R.id.descriptionField);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAnnouncement();
            }
        });


    }

    public void createAnnouncement() {
        try
        {
            String title = titleField.getText().toString();
            String description = descriptionField.getText().toString();

            Announcement announcement = new Announcement(title, description);
            databaseReference.push().setValue(announcement);
            finish();

        } catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


}
