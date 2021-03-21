package com.vivek.app2;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private static final String INTENT_ACTION = "com.vivek.app.showWiki";
    private static final String MY_PERMISSION = "edu.uic.cs478.s19.kaboom";
    private static final String TAG = "App2_MainActivity";
    private MyBroadcastReceiver MyReceiver;
    private IntentFilter intentFilter;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //handling Actionbar Icon and title
        getSupportActionBar().setTitle(" Application 2");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_action_name);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //handling button click
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener((View v) -> {
            checkPermissionAndBroadcast();
        });
    }

    //check permission and broadcast the intent and register
    private void checkPermissionAndBroadcast() {
        ActivityCompat.requestPermissions(this, new String[]{MY_PERMISSION},
                0);
        if (ActivityCompat.checkSelfPermission(this, MY_PERMISSION)
                == PackageManager.PERMISSION_GRANTED) {
            registerBroadcast();
            Log.i(TAG, "checkPermissionAndBroadcast: Permission Granted");
            startIntentApp3();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onRequestPermissionsResult: ");
        if (grantResults.length > 0) {
            // Register Broadcast
            Log.i(TAG, "onRequestPermissionsResult: Granted");
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                registerBroadcast();
                startIntentApp3();
            } else {
                Log.i(TAG, "onRequestPermissionsResult: denied");
                Toast.makeText(this, "Permission Denied",
                        Toast.LENGTH_SHORT).show();
                this.finish();
                System.exit(0);
            }
        }
    }

    //Registering for broadcast sent by app3
    public void registerBroadcast() {
        Log.i(TAG, "registerBroadcast: Registering broadcast receiver");
        intentFilter = new IntentFilter(INTENT_ACTION);
        intentFilter.setPriority(9);
        MyReceiver = new MyBroadcastReceiver();
        registerReceiver(MyReceiver, intentFilter);
    }

    public void startIntentApp3() {
        Intent intent = new Intent();
        intent.setClassName("com.vivek.app3",
                "com.vivek.app3.MainActivity");
        startActivity(intent);
    }

    //Unregister broadcast when activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (MyReceiver != null)
            unregisterReceiver(MyReceiver);
    }
}

