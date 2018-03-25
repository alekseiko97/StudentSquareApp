package com.fhict.studentsquareapp;

import android.content.Intent;
import android.nfc.FormatException;
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

import java.io.Serializable;
import java.util.ArrayList;

public class CreateAnnouncementActivity extends AppCompatActivity {

    private NumberPicker numberPicker;
    private Spinner spinner;
    private TextInputEditText titleField;
    private EditText descriptionField;
    public ArrayList<Announcement> announcementList = new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_announcement);

        numberPicker = (NumberPicker)findViewById(R.id.numberPicker);
        spinner = (Spinner)findViewById(R.id.spinner);
        Button createBtn = (Button) findViewById(R.id.createBtn);
        titleField = (TextInputEditText)findViewById(R.id.titleField);
        descriptionField = (EditText)findViewById(R.id.descriptionField);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(200);
        numberPicker.setWrapSelectorWheel(false);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CreateAnnouncementActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

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
            Integer nrOfPoints = numberPicker.getValue();
            String type = spinner.getSelectedItem().toString();

            Announcement announcement = new Announcement(title, description, nrOfPoints, type);
            announcementList.add(announcement);



            Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
            intent.putExtra("announcement", announcement);
            setResult(RESULT_OK, intent);
            finish();

        } catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


}
