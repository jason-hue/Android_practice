package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "zy";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "接收到广播");
        Toast.makeText(context, "收到自定义广播", Toast.LENGTH_SHORT).show();
    }
}