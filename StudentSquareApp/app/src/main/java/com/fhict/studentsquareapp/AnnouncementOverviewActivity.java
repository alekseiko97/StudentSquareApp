package com.fhict.studentsquareapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import iammert.com.expandablelib.ExpandableLayout;


public class AnnouncementOverviewActivity extends AppCompatActivity {

    private TextView name;
    private TextView description;
    private TextView createdBy;
    private TextView createdOn;
    private TextView textView;
    private ImageView imageView;
    private Bitmap bitmap;
   // private ExpandableLayout expandableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_overview);

        name = (TextView)findViewById(R.id.nameOverview);
        description = (TextView)findViewById(R.id.descriptionOverview);
        createdBy = (TextView)findViewById(R.id.createdByOverview);
        createdOn = (TextView)findViewById(R.id.createdOnOverview);
        imageView = (ImageView)findViewById(R.id.imageView3);
        textView = findViewById(R.id.textView7);

        setData();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bitmap != null) {
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


       // expandableLayout = (ExpandableLayout)findViewById(R.id.expandable_layout);



//        expandableLayout.setRenderer(new ExpandableLayout.Renderer() {
//            @Override
//            public void renderParent(View view, Object o, boolean b, int i) {
//                ((TextView)view.findViewById(R.id.attachments)).setText("Attachments");
//            }
//
//            @Override
//            public void renderChild(View view, Object o, int i, int i1) {
//
//                ((ImageView)view.findViewById(R.id.imageAttached)).setImageBitmap(bitmap);
//            }
//        });
       // imageView = (ImageView)findViewById(R.id.announcementImage);


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
            if (a.photoKey != null) {
                byte[] bytes = android.util.Base64.decode(a.photoKey, android.util.Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }

        } catch (NullPointerException npe)
        {
            Toast.makeText(getApplicationContext(), npe.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


}
