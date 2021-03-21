package com.vivek.app1;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TvshowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //handling Actionbar Icon and title
        getSupportActionBar().setTitle(" Application 1");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_action_name);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.activity_tvshow);

        Intent intent = getIntent();
        String url = intent.getExtras().getString("url");

        // Show web view for the url
        if(url != null){
            WebView myWebView = (WebView) findViewById(R.id.webpage);
            myWebView.setWebViewClient(new WebViewClient());
            myWebView.loadUrl(url);
        }else{
            Toast.makeText(getApplicationContext(), "No Show Selected" , Toast.LENGTH_LONG).show();
            this.finish();
        }
    }
}