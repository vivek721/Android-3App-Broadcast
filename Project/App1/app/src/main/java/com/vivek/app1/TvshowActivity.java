package com.vivek.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TvshowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow);
        WebView myWebView = (WebView) findViewById(R.id.webpage);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("http://www.tutorialspoint.com");
    }
}