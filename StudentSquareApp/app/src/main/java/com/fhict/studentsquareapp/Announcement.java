package com.fhict.studentsquareapp;


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

public class Announcement implements Serializable {

     String name;
     String description;
     int nrOfPoints;
     String type;
     DateFormat dateFormat;
     String createdAt;
     String createdBy;

    Announcement(String name, String description, int nrOfPoints, String type)
    {
        this.name = name;
        this.description = description;
        this.nrOfPoints = nrOfPoints;
        this.type = type;
        this.dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        this.createdAt = dateFormat.format(new Date());
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




}
