package com.fhict.studentsquareapp;

import android.os.Parcel;
import android.os.Parcelable;

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

    Announcement(String name, String description, int nrOfPoints, String type)
    {
        this.name = name;
        this.description = description;
        this.nrOfPoints = nrOfPoints;
        this.type = type;
    }

//    public Announcement(Parcel in) {
//        name = in.readString();
//        description = in.readString();
//        nrOfPoints = in.readInt();
//        type = in.readString();
//    }

    @Override
    public String toString()
    {
        return name + " \n " + description;
    }


//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(name);
//        parcel.writeString(description);
//        parcel.writeInt(nrOfPoints);
//        parcel.writeString(type);
//    }
//
//    public static final Parcelable.Creator<Announcement> CREATOR = new Parcelable.Creator<Announcement>()
//    {
//
//        @Override
//        public Announcement createFromParcel(Parcel parcel) {
//            return new Announcement(parcel);
//        }
//
//        @Override
//        public Announcement[] newArray(int i) {
//            return new Announcement[i];
//        }
//    };


}
