package com.example.testwebview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity
{
    private final static String TAG = MainActivity.class.getSimpleName();

    private TextView titleTv;
    private ProgressBar progressBar;
    private WebView mWebView;
    private WebSettings mWebSettings;
    private ImageButton backBtn;
    private ImageButton forwardBtn;
    private ImageButton refreshBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView()
    {
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebSettings.setDefaultTextEncodingName("utf-8");

        mWebView.loadUrl("http://wap.baidu.com");

        mWebView.setWebChromeClient(new WebChromeClient()
        {

            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                if (newProgress == 100)
                    progressBar.setVisibility(View.GONE);
                else
                    progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);

                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title)
            {
                Log.i(TAG, "ChromeClient title = " + title);
                titleTv.setText(title);
                super.onReceivedTitle(view, title);
            }

        });

        mWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                final WebView webview = view;
                refreshBtn.setImageDrawable(getResources().getDrawable(R.drawable.reload));
                refreshBtn.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        webview.reload();
                    }
                });
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                final WebView webview = view;
                refreshBtn.setImageDrawable(getResources().getDrawable(R.drawable.cancel));
                refreshBtn.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        webview.stopLoading();
                    }
                });
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description,
                    String failingUrl)
            {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
            {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView()
    {
        titleTv = (TextView) findViewById(R.id.title);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mWebView = (WebView) findViewById(R.id.webView);
        backBtn = (ImageButton) findViewById(R.id.back);
        forwardBtn = (ImageButton) findViewById(R.id.forward);
        refreshBtn = (ImageButton) findViewById(R.id.refresh);

        backBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mWebView.goBack();
            }
        });
        forwardBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mWebView.goForward();
            }
        });
        refreshBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mWebView.reload();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (mWebView != null)
                mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_settings:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

}
