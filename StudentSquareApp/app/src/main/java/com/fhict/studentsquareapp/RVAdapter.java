package com.fhict.studentsquareapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    private static final String TAG = "TAG";

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView announcementName;
        TextView announcementDesc;
        TextView createdAt;
        public ViewHolder(View itemView) {
            super(itemView);
            announcementName = (TextView)itemView.findViewById(R.id.announcementName);
            announcementDesc = (TextView)itemView.findViewById(R.id.announcementDesc);
            createdAt = (TextView)itemView.findViewById(R.id.createdAt);

        }
    }

    private Context mContext;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private List<Announcement> announcementList = new ArrayList<>();
    public RVAdapter(final Context context){
        mContext = context;

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Announcements");

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Announcement announcement = dataSnapshot.getValue(Announcement.class);
                if (!announcementList.contains(announcement)) {
                    announcementList.add(announcement);
                    Collections.sort(announcementList);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadAnnouncement:onCancelled", databaseError.toException());
            }
        };

        mDatabaseReference.addChildEventListener(childEventListener);
        mChildEventListener = childEventListener;
    }
    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RVAdapter.ViewHolder holder, int position) {
            holder.announcementName.setText(announcementList.get(position).name);
            if (announcementList.get(position).description.length() >= 40)
            {
                holder.announcementDesc.setText(announcementList.get(position).description.substring(0, 40) + "...");
            }
            else {
                holder.announcementDesc.setText(announcementList.get(position).description);
            }

            holder.createdAt.setText("Created at: " + announcementList.get(position).createdAt);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AnnouncementOverviewActivity.class);
                    intent.putExtra("announcement", announcementList.get(holder.getAdapterPosition()));

                    view.getContext().startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }

    public void cleanupListener()
    {
        if (mChildEventListener != null) {
            mDatabaseReference.removeEventListener(mChildEventListener);
        }

    }
}
