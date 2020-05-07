package com.rottenmach.vivek.xmorpher;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION = 10;
    private int STORAGE_PERMISSION_CODE = 23;
    private AdView mAdView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshWebView;
    String url = "https://rottenmach.com";
    private WebView webView;

    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            this.context = context;
        }
    }

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 10);
        }

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6668658714002179~5829349630");
        this.mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.mAdView.loadAd(new Builder().build());
        this.mAdView.setAdListener(new AdListener() {
            public void onAdLeftApplication() {
            }

            public void onAdLoaded() {
                Log.i("Ads", "onAdLoaded");
            }

            public void onAdFailedToLoad(int i) {
                Log.i("Ads", "onAdFailedToLoad");
            }

            public void onAdOpened() {
                Log.i("Ads", "onAdOpened");
            }

            public void onAdClosed() {
                Log.i("Ads", "onAdClosed");
            }
        });




        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6668658714002179~5829349630");
        mAdView = (AdView) findViewById(R.id.adView);



        mAdView.setAdListener(new AdListener() {
                                  @Override
                                  public void onAdLoaded() {
                                      Log.i("Ads", "onAdLoaded");
                                  }
                              });




        /**init toolbar**/

        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        refreshWebView = (SwipeRefreshLayout) findViewById(R.id.swipe);


        /**init webview with configuration**/
        initWebView();

        webView.loadUrl(url);

        refreshWebView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshWebView.setRefreshing(true);
                webView.reload();
                refreshWebView.setRefreshing(false);
            }
        });

        webView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimeType,
                                        long contentLength) {

                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(url));

                request.setMimeType(mimeType);
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition,
                        mimeType));

                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(
                                url, contentDisposition, mimeType));
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading File",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void initWebView() {
        this.webView.getSettings().setSupportZoom(true);
        this.webView.getSettings().setBuiltInZoomControls(true);
        this.webView.setWebChromeClient(new MyWebChromeClient(this));
        this.webView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                super.onPageStarted(webView, str, bitmap);
                MainActivity.this.progressBar.setVisibility(View.VISIBLE);
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                MainActivity.this.webView.loadUrl(str);
                MainActivity.this.url = str;
                return true;
            }

            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                MainActivity.this.progressBar.setVisibility(View.GONE);
            }

            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
                MainActivity.this.progressBar.setVisibility(View.GONE);
                MainActivity mainActivity = MainActivity.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error Loading ");
                stringBuilder.append(MainActivity.this.url);
                Toast.makeText(mainActivity, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        this.webView.clearCache(true);
        this.webView.clearHistory();
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.setHorizontalScrollBarEnabled(true);
    }

    public void onBackPressed() {
        if (this.webView.canGoBack()) {
            this.webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.about) {
            startActivity(new Intent(this, AboutActivity.class));
        } else if (itemId == R.id.openbrowser) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://rottenmach.com")));
        } else if (itemId == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (itemId == R.id.tools) {
            startActivity(new Intent(this, ToolsActivity.class));
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
