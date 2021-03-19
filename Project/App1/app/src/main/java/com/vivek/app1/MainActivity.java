package com.vivek.app1;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private static final String INTENT_ACTION = "com.vivek.app.showWiki";
    private static final String MY_PERMISSION = "edu.uic.cs478.s19.kaboom";
    private static final String TAG = "App1 MainActivity";
    private MyBroadcastReceiver MyReceiver;
    private IntentFilter intentFilter;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //handling Actionbar Icon and title
        getSupportActionBar().setTitle(" Application 1");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_action_name);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Check For permission if not granted the ask
        if (ActivityCompat.checkSelfPermission(this, MY_PERMISSION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{MY_PERMISSION}, 0);
        }

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener((View v) -> {
            checkPermissionAndBroadcast();
        });

    }

    private void checkPermissionAndBroadcast() {
        if (ActivityCompat.checkSelfPermission(this, MY_PERMISSION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "checkPermissionAndBroadcast: Permission Granted");
            Intent intent = new Intent();
            intent.setClassName("com.vivek.app2", "com.vivek.app2.MainActivity");
            startActivity(intent);
        } else {
            Toast.makeText(this, "Cant open App2 No permission", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        if (results.length > 0) {
            if (results[0] == PackageManager.PERMISSION_GRANTED) {
                // setting Intent Filter action and priority
                Log.i(TAG, "onRequestPermissionsResult: Broadcastreveiver registerd");
                MyReceiver = new MyBroadcastReceiver();
                intentFilter = new IntentFilter(INTENT_ACTION);
                intentFilter.setPriority(1);
                registerReceiver(MyReceiver, intentFilter);
            } else {
                Log.i(TAG, "onRequestPermissionsResult: Broadcastreveiver not registered");
                Toast.makeText(this, "Bummer: No Permission Granted", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (MyReceiver != null)
            unregisterReceiver(MyReceiver);
    }
}