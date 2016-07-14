package com.hbdworld.myinternetexplorer;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private WebView mWebView;
    EditText editText =null;
    private static final String TAG = "hbd";
    String homeUrl = "http://www.hbdfood.com/company/restaurant/shopInfo?shop_id=2902&table=2216";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //--
        //String url = "file:///android_asset/index.html";
        String url = "http://www.hbdfood.com/company/restaurant/shopInfo?shop_id=2902&table=2216";
        //String url = "http://www.baidu.com/";

        editText = (EditText)this.findViewById(R.id.url);
        editText.setText(url);

        //--
        final WebView mWebView = (WebView) findViewById(R.id.webview);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });


        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG,"onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG,"onPageFinished");
            }
        });


        Button homeBtn = (Button)this.findViewById(R.id.home_btn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(homeUrl);
                mWebView.loadUrl(homeUrl);
                afterExcute();
            }
        });


        Button goBtn = (Button)this.findViewById(R.id.go_btn);
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = editText.getText().toString();
                mWebView.loadUrl(url);
                afterExcute();
            }
        });

        Button preBtn = (Button)this.findViewById(R.id.pre_btn);
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWebView.canGoForward())
                    mWebView.goForward();
                afterExcute();
            }
        });

        Button backBtn = (Button)this.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWebView.canGoBack())
                    mWebView.goBack();
                afterExcute();
            }
        });


        Button scanBtn = (Button)this.findViewById(R.id.scan_btn);
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "scan", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void afterExcute() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


}
