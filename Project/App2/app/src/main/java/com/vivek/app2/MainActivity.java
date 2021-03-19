package com.vivek.app2;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String INTENT_ACTION =
            "com.vivek.app.showWiki";
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

        // setting Intent Filter action and priority
        intentFilter = new IntentFilter(INTENT_ACTION);
        intentFilter.setPriority(9);

        //Registering the broadcaster
        MyReceiver = new MyBroadcastReceiver();
        registerReceiver(MyReceiver, intentFilter);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener((View v) -> {
            Intent intent = new Intent();
            intent.setClassName("com.vivek.app3", "com.vivek.app3.MainActivity");
            startActivity(intent);

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (MyReceiver != null)
            unregisterReceiver(MyReceiver);
    }
}