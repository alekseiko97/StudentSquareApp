package com.fhict.studentsquareapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.google.android.gms.gcm.GcmListenerService;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

public class GcmBroadcastReceiver extends GcmListenerService {


    @Override
    public void onMessageReceived(String from, Bundle data) {
//        Intent intent = new Intent(this, PushReceiverIntentService.class);
//        intent.putExtras(data);
//        startService(intent);
    }

}
