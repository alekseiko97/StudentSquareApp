package com.fhict.studentsquareapp;

import android.annotation.SuppressLint;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Request extends Announcement implements Serializable {

    int points;
    boolean isAccepted;
    boolean isCompleted;
    //FirebaseUser acceptedBy;

    Request()
    {

    }

    @SuppressLint("SimpleDateFormat")
    Request(String name, String description, int points) {
        super(name, description);
        this.points = points;
        this.isAccepted = false;
        this.isCompleted = false;
        //this.acceptedBy = null;

    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

//    public FirebaseUser getAcceptedBy()
//    {
//        return acceptedBy;
//    }
//
//    public void setAcceptedBy(FirebaseUser firebaseUser)
//    {
//        this.acceptedBy = firebaseUser;
//    }
}
