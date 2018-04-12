package com.fhict.studentsquareapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class PushNotification extends FirebaseInstanceIdService {

    private static final String TAG = "TAG";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Refreshed token: " + refreshedToken);

    }
}
