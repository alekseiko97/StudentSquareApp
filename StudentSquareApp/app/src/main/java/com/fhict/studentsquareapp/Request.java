package com.fhict.studentsquareapp;

import android.annotation.SuppressLint;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Request extends Announcement implements Serializable {

    String id;
    int points;
    boolean isAccepted;
    boolean isCompleted;
    //String token;

    Request()
    {

    }

    @SuppressLint("SimpleDateFormat")
    Request(String id, String name, String description, int points) {
        super(name, description);
        this.id = id;
        this.points = points;
        this.isAccepted = false;
        this.isCompleted = false;
        //this.token = FirebaseInstanceId.getInstance().getToken();
        //this.acceptedBy = null;

    }

    @SuppressLint("SimpleDateFormat")
    Request(String id, String name, String description, String photoKey, int points) {
        super(name, description, photoKey);
        this.id = id;
        this.points = points;
        this.isAccepted = false;
        this.isCompleted = false;
        //this.token = FirebaseInstanceId.getInstance().getToken();

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

}
