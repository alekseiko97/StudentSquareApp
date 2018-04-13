package com.fhict.studentsquareapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestHolder> {


    public static class RequestHolder extends RecyclerView.ViewHolder {
        TextView requestName;
        TextView requestDesc;
        TextView requestPoints;
        public RequestHolder(View itemView) {
            super(itemView);
            requestName = (TextView)itemView.findViewById(R.id.requestName);
            requestDesc = (TextView)itemView.findViewById(R.id.requestDesc);
            requestPoints = (TextView)itemView.findViewById(R.id.requestPoints);
        }
    }

    private Context mContext;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ValueEventListener mValueEventListener;
    private static final String TAG = "Single";
    private List<Request> requestList = new ArrayList<>();
    public RequestAdapter(final Context context)
    {
        mContext = context;
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Requests");
         //Create child event listener
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Request request = dataSnapshot.getValue(Request.class);
                if (!requestList.contains(request)) {

                    requestList.add(request);
                    Collections.sort(requestList);
                    notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Request request = dataSnapshot.getValue(Request.class);
                for (Request r: requestList) {
                    if (r.id.equals(request.id)) {
                        requestList.remove(r);
                        requestList.add(request);
                        Collections.sort(requestList);
                        notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadRequest:onCancelled", databaseError.toException());
            }
        };


        mDatabaseReference.addChildEventListener(childEventListener);
        mChildEventListener = childEventListener;
//        mDatabaseReference.addValueEventListener(valueEventListener);
//        mValueEventListener = valueEventListener;
    }
    @Override
    public RequestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_request, parent, false);
        return new RequestAdapter.RequestHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final RequestHolder holder, int position) {
        Request request = requestList.get(position);
        holder.requestName.setText(request.name);
        if (requestList.get(position).description.length() >= 40) {
            holder.requestDesc.setText(requestList.get(position).description.substring(0, 40) + "...");
        } else {
            holder.requestDesc.setText(request.description);
            //}
            Integer points = request.points;
            holder.requestPoints.setText(points.toString());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), RequestOverviewActivity.class);
                    intent.putExtra("requestA", requestList.get(holder.getAdapterPosition()));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public void cleanupListener()
    {
        if (mChildEventListener != null) {
            mDatabaseReference.removeEventListener(mChildEventListener);
        }

    }


}
