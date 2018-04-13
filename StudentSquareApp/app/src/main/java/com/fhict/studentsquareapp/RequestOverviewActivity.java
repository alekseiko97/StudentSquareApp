package com.fhict.studentsquareapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private Bitmap bitmap;
    private ImageView imageView;


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
        imageView = (ImageView)findViewById(R.id.requestClip);
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
                   Toast.makeText(getApplicationContext(), "This request has been already accepted!", Toast.LENGTH_SHORT).show();
               }

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmap != null)
                {
                    Intent intent = new Intent();
                    String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap, "Image", null);
                    Uri uri = Uri.parse(path);
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    startActivity(intent);
                } else
                {
                    Toast.makeText(getApplicationContext(), "No attachments were found", Toast.LENGTH_SHORT).show();
                }

            }
        });

        makeBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(RequestOverviewActivity.this);

                final EditText input = new EditText(getApplicationContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);


                builder.setMessage("Please enter a new value for points. Note that it has to be less than the current one");
                builder.setTitle("Make a counter-offer!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        boolean updated = updatePoints(Integer.parseInt(input.getText().toString()));
                        if (updated) {
                            Toast.makeText(getApplicationContext(), "Success! New value is " + input.getText().toString(), Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                        }

                    }
                });

                builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog;
                dialog = builder.create();
                dialog.setView(input);
                dialog.show();
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

    private boolean updatePoints(int newValue) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Requests").child(r.id);

        if (r.points > newValue) {
            r.setPoints(newValue);
            databaseReference.setValue(r);
            return true;
        }

        return false;


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
            if (r.photoKey != null) {
                byte[] bytes = android.util.Base64.decode(r.photoKey, android.util.Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }

        } catch (NullPointerException npe)
        {
            Toast.makeText(getApplicationContext(), "Oops, " + npe.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}
