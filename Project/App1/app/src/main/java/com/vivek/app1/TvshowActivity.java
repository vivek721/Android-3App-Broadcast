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
        setContentView(R.layout.activity_tvshow);
        Intent intent = getIntent();
        String url = intent.getExtras().getString("url");

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