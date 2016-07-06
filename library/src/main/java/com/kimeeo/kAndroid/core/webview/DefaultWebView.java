package com.kimeeo.kAndroid.core.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.kimeeo.kAndroid.core.R;
import com.kimeeo.kAndroid.core.fragment.BaseFragment;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bhavinpadhiyar on 11/4/15.
 */
abstract public class DefaultWebView extends BaseFragment {
    private ProgressBar mProgressBar;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout._default_webview_fragment_layout, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        WebView webView = (WebView) view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(getURL());
        webView.setWebViewClient(new WebViewClient());
        webView.setBackgroundColor(0x00000000);

        WebChromeClient webChromeClient = createWebChromeClient();
        if (webChromeClient != null) {
            configWebChromeClient(webChromeClient);
            webView.setWebChromeClient(webChromeClient);
        }

        WebViewClient webViewClient = createWebViewClient();
        if (webViewClient != null) {
            configWebViewClient(webViewClient);
            webView.setWebViewClient(webViewClient);
        }
        return view;
    }
    protected WebChromeClient createWebChromeClient() {
        return new DefaultWebChromeClient(mProgressBar);
    }
    protected void configWebChromeClient(WebChromeClient client) {

    }
    protected WebViewClient createWebViewClient() {
        return new DefaultWebViewClient(mProgressBar);
    }
    protected void configWebViewClient(WebViewClient client) {

    }
    protected void garbageCollectorCall() {

    }
    abstract public String getURL();
    public class DefaultWebChromeClient extends WebChromeClient {
        private ProgressBar mProgressBar;
        public DefaultWebChromeClient(ProgressBar mProgressBar)
        {
            this.mProgressBar=mProgressBar;
        }
        @Override
        public void onProgressChanged(WebView view, int progress) {
            if (progress == 100)
                progress = 0;

            if(mProgressBar!=null) {
                mProgressBar.setProgress(progress);
                mProgressBar.setIndeterminate(false);
                if(progress==0)
                    mProgressBar.setVisibility(View.GONE);
                else
                    mProgressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    public class DefaultWebViewClient extends WebViewClient {
        private ProgressBar mProgressBar;
        public DefaultWebViewClient(ProgressBar mProgressBar)
        {
            this.mProgressBar=mProgressBar;
        }

            @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if(mProgressBar!=null) {
                    mProgressBar.setIndeterminate(true);
                    mProgressBar.setProgress(10);
                    mProgressBar.setVisibility(View.VISIBLE);
                }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if(mProgressBar!=null)
            {
                mProgressBar.setVisibility(View.GONE);
                mProgressBar.setIndeterminate(false);
                mProgressBar.setProgress(0);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.endsWith(".mp4")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(url), "video/*");
                view.getContext().startActivity(intent);
                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }
    }
    public static String getHost(String url) {
        try {
            return new URL(url).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}