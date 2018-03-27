package com.fhict.studentsquareapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
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

    private List<Announcement> announcementList;
    RVAdapter(List<Announcement> announcementList){
        this.announcementList = announcementList;
    }
    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVAdapter.ViewHolder holder, int position) {
            holder.announcementName.setText(announcementList.get(position).name);
            holder.announcementDesc.setText(announcementList.get(position).description);
            holder.createdAt.setText("Created at: " + announcementList.get(position).createdAt.toString());
    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }
}
