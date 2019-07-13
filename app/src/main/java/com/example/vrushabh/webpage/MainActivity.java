package com.example.vrushabh.webpage;

import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeView;
    private Button button;


    //backPress for webPage
    @Override
    public void onBackPressed(){
        button.setVisibility(View.VISIBLE);
        swipeView.setVisibility(View.GONE);
        webView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(),"Welcome back",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //All id
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.Progress);
        button = findViewById(R.id.Button);
        swipeView = findViewById(R.id.swipe);

        //Button to launch webPage
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setVisibility(View.GONE);
                swipeView.setVisibility(View.VISIBLE);
                webView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Loading...",Toast.LENGTH_SHORT).show();


                //ProgressBar Action before webPage load
                webView.setWebViewClient(new MyWebView());
                //Load link
                webView.loadUrl("https://developer.android.com/reference/packages");
            }
        });

        //for refresh page
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
                swipeView.setRefreshing(true);
            }
        });

    }


    //This codes pop the progress bar and end before the web page load its depend on the speed of the internet
    private class MyWebView extends WebViewClient {
       @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon){
           webView.setVisibility(View.VISIBLE);
           view.setVisibility(View.INVISIBLE);
       }
       @Override
        public void  onPageFinished(WebView view, String url ){
           progressBar.setVisibility(View.GONE);
           view.setVisibility(View.VISIBLE);
           swipeView.setRefreshing(false);
           super.onPageFinished(view , url);
       }
    }

}
