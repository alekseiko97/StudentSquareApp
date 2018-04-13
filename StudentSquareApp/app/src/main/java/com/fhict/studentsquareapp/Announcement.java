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
     Date date;
     String createdAt;
     String createdBy;
     String photoKey;


     Announcement()
     {

     }
    @SuppressLint("SimpleDateFormat")
    Announcement(String name, String description)
    {
        this.name = name;
        this.description = description;
        this.date = new Date();
        this.createdAt = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(date);
        this.createdBy = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }

    @SuppressLint("SimpleDateFormat")
    Announcement(String name, String description, String photoKey)
    {
        this.name = name;
        this.description = description;
        this.date = new Date();
        this.createdAt = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(date);
        this.createdBy = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        this.photoKey = photoKey;
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
