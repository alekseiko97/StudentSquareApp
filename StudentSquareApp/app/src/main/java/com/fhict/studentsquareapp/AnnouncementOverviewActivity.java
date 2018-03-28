package com.fhict.studentsquareapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AnnouncementOverviewActivity extends AppCompatActivity {

    private TextView name;
    private TextView description;
    private TextView createdBy;
    private TextView createdOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_overview);

        name = (TextView)findViewById(R.id.nameOverview);
        description = (TextView)findViewById(R.id.descriptionOverview);
        createdBy = (TextView)findViewById(R.id.createdByOverview);
        createdOn = (TextView)findViewById(R.id.createdOnOverview);

        setData();
    }

    private void setData()
    {
        try
        {
            Intent intent = getIntent();
            Announcement a = (Announcement)intent.getExtras().getSerializable("announcement");
            name.setText(a.name);
            description.setText(a.description);
            createdBy.setText(a.createdBy);
            createdOn.setText(a.createdAt);
        } catch (NullPointerException npe)
        {
            Toast.makeText(getApplicationContext(), npe.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


}
