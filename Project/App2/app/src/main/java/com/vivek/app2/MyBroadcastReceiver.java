package com.vivek.app2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String phoo1 = intent.getStringExtra("url");
        System.out.println(phoo1 + " " + intent);
        Toast.makeText(context , "App2 Receiver active: ", Toast.LENGTH_LONG).show();
    }
}
