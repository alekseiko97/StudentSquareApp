package com.fhict.studentsquareapp;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

/**
 * Created by ASUS-PC on 22.03.2018.
 */
 enum Type {Announcement, HelpRequest}

public class Announcement implements Serializable {

    private String name;
    private String description;
    private int nrOfPoints;
    private String type;
   // private FirebaseUser firebaseUser;

    Announcement(String name, String description, int nrOfPoints, String type)
    {
        this.name = name;
        this.description = description;
        this.nrOfPoints = nrOfPoints;
        this.type = type;
       // firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
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
