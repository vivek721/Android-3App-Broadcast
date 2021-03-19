package com.vivek.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyBroadcastReceiver MyReceiver;
    private IntentFilter intentFilter ;
    private static final String INTENT_ACTION = "com.vivek.app.showWiki";
    private Button button; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setting Intent Filter action and priority
        MyReceiver = new MyBroadcastReceiver() ;
        intentFilter = new IntentFilter(INTENT_ACTION);
        intentFilter.setPriority(1) ;
        registerReceiver(MyReceiver, intentFilter) ;
        
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener((View v) -> {
            Intent intent = new Intent();
            intent.setClassName("com.vivek.app2", "com.vivek.app2.MainActivity");
            startActivity(intent);

        });
        Button button1;
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener((View v) -> {
            Intent intent = new Intent();
            intent.setClassName("com.vivek.app1", "com.vivek.app1.TvshowActivity");
            startActivity(intent);
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(MyReceiver != null)
            unregisterReceiver(MyReceiver);
    }
}