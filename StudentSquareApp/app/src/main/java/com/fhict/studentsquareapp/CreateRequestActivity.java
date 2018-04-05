package com.fhict.studentsquareapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateRequestActivity extends AppCompatActivity {

    private TextInputEditText titleField;
    private EditText descriptionField;
    private NumberPicker numberPicker;
    private Button createButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        titleField = (TextInputEditText)findViewById(R.id.requestTitleField);
        descriptionField = (EditText)findViewById(R.id.requestDescField);
        numberPicker = (NumberPicker)findViewById(R.id.numberPicker);
        createButton = (Button)findViewById(R.id.requestCreateBtn);

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
    }

    private void createRequest() {
        try {
            String name = titleField.getText().toString();
            String description = descriptionField.getText().toString();
            Integer points = numberPicker.getValue();

            Request request = new Request(name, description, points);
            databaseReference.push().setValue(request);

            finish();
        } catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }



    }
}
