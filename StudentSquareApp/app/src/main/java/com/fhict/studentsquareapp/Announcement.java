package com.fhict.studentsquareapp;


import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ASUS-PC on 22.03.2018.
 */
 enum Type {Announcement, HelpRequest}

public class Announcement implements Serializable, Comparable<Announcement> {

     String name;
     String description;
     int nrOfPoints;
     String type;
     Date date;
     DateFormat dateFormat;
     String createdAt;
     String createdBy;

    @SuppressLint("SimpleDateFormat")
    Announcement(String name, String description, int nrOfPoints, String type)
    {
        this.name = name;
        this.description = description;
        this.nrOfPoints = nrOfPoints;
        this.type = type;
        this.date = new Date();
        this.dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        this.createdAt = dateFormat.format(date);
        this.createdBy = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }



    @Override
    public String toString()
    {
        if (description.length() >= 40)
        {
            return name + "\n" + description.substring(0, 40) + "...";
        }
        else
        {
            return name + "\n" + description;
        }

    }


    @Override
    public int compareTo(@NonNull Announcement announcement) {
        return announcement.date.compareTo(date);
    }
}
