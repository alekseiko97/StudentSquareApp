package com.fhict.studentsquareapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequestActivity extends AppCompatActivity {

    private static final String TAG = "RequestActivity";

    private RecyclerView recyclerView;
    private RequestAdapter requestAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addRequestBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        recyclerView = (RecyclerView) findViewById(R.id.requestRV);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        addRequestBtn = (FloatingActionButton) findViewById(R.id.addRequestButton);
        addRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateRequestActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // Listen for requests
        requestAdapter = new RequestAdapter(this);
        recyclerView.setAdapter(requestAdapter);

    }

    @Override
    public void onStop()
    {
        super.onStop();
        requestAdapter.cleanupListener();
    }
}
