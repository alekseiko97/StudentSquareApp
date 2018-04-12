package com.fhict.studentsquareapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestOverviewActivity extends AppCompatActivity {

    private Request r;
    private TextView name;
    private TextView description;
    private TextView createdBy;
    private TextView createdOn;
    private TextView points;
    private Button accept;
    private Button makeBet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_overview);

        name = (TextView)findViewById(R.id.requestNameOverview);
        description = (TextView)findViewById(R.id.requestDescriptionOverview);
        createdBy = (TextView)findViewById(R.id.requestCreatedByOverview);
        createdOn = (TextView)findViewById(R.id.requestCreatedOnOverview);
        points = (TextView)findViewById(R.id.requestPointsOverview);
        accept = (Button)findViewById(R.id.acceptButton);
        makeBet = (Button)findViewById(R.id.betButton);
        r = (Request) getIntent().getExtras().getSerializable("requestA");
        setData();

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (!r.isAccepted())
               {

                   if(setAccepted())
                   {
                       AlertDialog.Builder builder = new AlertDialog.Builder(RequestOverviewActivity.this);

                       builder.setMessage("You've successfully accepted this request. The author will receive notification about that!");
                       builder.setTitle("Success!");
                       builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                               finish();
                           }
                       });
                       AlertDialog dialog = builder.create();
                       dialog.show();
                   }


               } else {
                   Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
               }

            }
        });
    }

    private boolean setAccepted()
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Requests").child(r.id);

        r.setAccepted(true);

        databaseReference.setValue(r);
        return true;

    }


    @SuppressLint("SetTextI18n")
    private void setData()
    {
        try
        {
            name.setText(r.name);
            description.setText(r.description);
            createdBy.setText(r.createdBy);
            createdOn.setText(r.createdAt);
            Integer i = r.points;
            points.setText(points.getText() + " " + i.toString());

        } catch (NullPointerException npe)
        {
            Toast.makeText(getApplicationContext(), "Oops, " + npe.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}
