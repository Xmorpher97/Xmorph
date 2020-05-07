package com.rottenmach.vivek.xmorpher;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseInstanceIdService extends FirebaseMessagingService {
    private static final String REG_TOKEN = "REG_TOKEN";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("NEW_TOKEN", s);
    }
}
