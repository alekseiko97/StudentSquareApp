package com.fhict.studentsquareapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RequestDeskFragment extends Fragment {

    public static RequestDeskFragment newInstance() {
        return new RequestDeskFragment();
    }

    private RecyclerView recyclerView;
    private RequestAdapter requestAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addRequestBtn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.activity_request, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.requestRV);
        addRequestBtn = (FloatingActionButton) rootView.findViewById(R.id.addRequestButton);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Listen for requests
        requestAdapter = new RequestAdapter(this.getContext());
        recyclerView.setAdapter(requestAdapter);

        addRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateRequestActivity.class);
                startActivity(intent);
            }
        });

        return rootView;

    }
}
