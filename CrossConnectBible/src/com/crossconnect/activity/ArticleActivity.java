package com.crossconnect.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crossconnect.actions.R;


public class ArticleActivity extends Activity {

	WebView webView;
	
	private String url;
	private String verse;
	
	ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		url = getIntent().getExtras().getString("url");
		verse = getIntent().getExtras().getString("verse");

		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.article);
		setProgressBarVisibility(true);
		webView = (WebView) findViewById(R.id.webView);
		ImageView up = (ImageView) findViewById(R.id.title_bar_icon);
		up.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
  	  	        Intent i = new Intent(ArticleActivity.this, MainActivity.class);
  	  	        startActivity(i);
			}

		});

		//Set title
		((TextView) findViewById(R.id.header_title)).setText(verse) ;

		webView.getSettings().setJavaScriptEnabled(true);

		progressBar = (ProgressBar) findViewById(R.id.progress);
		
		//Update progres of webview
		final Activity activity = this;
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				// Activities and WebViews measure progress with different scales.
				// The progress meter will automatically disappear when we reach 100%
				progressBar.setProgress(progress);
				if (progress == 100) {
					progressBar.setVisibility(View.GONE);
				}
			}
		});
		webView.setWebViewClient(new WebViewClient() {
			   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			     Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
			   }
			 });

		//		String htmlFormat = ArticleFormatter.getArticleHTML("http://www.desiringgod.org/ResourceLibrary/RecentlyAdded/4631_Encouraging_Each_Other_at_the_End_of_the_Age_Spanish/");
		//		String htmldata = "http://www.desiringgod.org/ResourceLibrary/RecentlyAdded/4631_Encouraging_Each_Other_at_the_End_of_the_Age_Spanish/";
		//		webView.loadData(htmlFormat, "text/html","UTF-8");


		webView.loadUrl(getIntent().getExtras().getString("url"));
	}
	
    /**
     * Inflate options menu from resource
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.webview, menu);
        return true;
    }

    /**
     * When a menu item is selected determines what to do with it
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//        case R.id.lookup_share: {
//            share();
//            return true;
//        }}
        return false;
    }    
    
    
    
	
    /**
     * Share the selected verse by intent
     */
    private void share() {
        Intent sendMailIntent = new Intent(Intent.ACTION_SEND);
        sendMailIntent.putExtra(Intent.EXTRA_SUBJECT, "CrossConnect Bible - Article");
        sendMailIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendMailIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendMailIntent, "Share using..."));
    }


}
