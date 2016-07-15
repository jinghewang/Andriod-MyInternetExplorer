package com.hbdworld.myinternetexplorer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private WebView mWebView;
    EditText editText =null;
    private static final String TAG = "hbd";
    String homeUrl = "http://www.hbdfood.com/company/restaurant/shopInfo?shop_id=2902&table=2216";
    private final int REQUEST_CODE = 0xa1;



    private class JsToJava
    {
        @JavascriptInterface
        public void jsMethod(String paramFromJS)
        {
            //Log.i("CDH", paramFromJS);
            System.out.println("js返回结果" + paramFromJS);//处理返回的结果
        }
    }


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
        mWebView.addJavascriptInterface(new JsToJava(),"stub");
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


        Button jsBtn = (Button)this.findViewById(R.id.js_btn);
        jsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebView.loadUrl("javascript:orderPayStatus('2016071315511013522637',5)");
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
                Intent intent = new Intent();
                //隐式指定
                intent.setAction("com.google.zxing.client.android.SCAN");
                //启动ZXing已经写好、且我们做小量修改后的CaptureActivity。
                startActivityForResult(intent, REQUEST_CODE);

                Toast.makeText(MainActivity.this, "scan", Toast.LENGTH_SHORT).show();
            }
        });

        Button viewBtn = (Button)this.findViewById(R.id.view_btn);
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                TextView textView = new TextView(MainActivity.this);
                textView.setText(editText.getText());
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                dialog.addContentView(textView,layoutParams);
                dialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //我们需要的结果返回
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //result就是二维码扫描的结果。
            String result = data.getStringExtra("scan_result");
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            editText.setText(result);
        }
    }

    private void afterExcute() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


}
