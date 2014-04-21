package com.cse3310.phms.ui.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.*;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.actionbarsherlock.app.SherlockFragment;
import com.cse3310.phms.R;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.widget.Toast;


public class WebViewActivity extends Activity implements FragmentManager.OnBackStackChangedListener {

    private WebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_web_view);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        Intent intent = getIntent();
        url = intent.getExtras().getString("urlpass");  //the url passed from the tab fragments

        WebViewClient webClient = new WebViewClient()
        {

            @Override
            public void onPageFinished(WebView webView, String url)
            {
                super.onPageFinished(webView, url);
                setProgressBarIndeterminateVisibility(false);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url)
            {
                webView.loadUrl(url);
                return false;
            }
        };
        webView.setWebViewClient(webClient);
        setTitle(intent.getExtras().getString("newLabel"));

        webView.loadUrl(url);
        setProgressBarIndeterminateVisibility(true);


        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if((keyCode == KeyEvent.KEYCODE_BACK) && !getActionBar().isShowing())
        {
            getActionBar().show();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack())
        {
            webView.goBack();
            setProgressBarIndeterminateVisibility(true);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.web_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_settings :
                return true;

            case R.id.open_browser:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webView.getOriginalUrl()));
                startActivity(browserIntent);
               // finish();
            return true;

            case R.id.copy_url:
                String urlText = webView.getOriginalUrl();
                ClipData urlClip = ClipData.newPlainText("urlClip", urlText);
                ClipboardManager urlClipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                urlClipBoard.setPrimaryClip(urlClip);
                Toast.makeText(getApplicationContext(),"Copied to Clipboard",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_fullscreen:
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getActionBar().hide();

                return true;

            case R.id.action_refresh:
                webView.reload();
                setProgressBarIndeterminateVisibility(true);

                return true;

            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                                    // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;

           // case android.R.id.

            default:
               return super.onOptionsItemSelected(item);

        }}

    @Override
    public void onBackStackChanged() {

        shouldDisplayHomeUp();

    }

    public void shouldDisplayHomeUp() {
        //Enable Up button only  if there are entries in the back stack
        boolean canback = getFragmentManager().getBackStackEntryCount()>0;
        getActionBar().setDisplayHomeAsUpEnabled(canback);
    }


    public boolean  onSupportNavigateUp()
    {
        //This method is called when the up button is pressed. Just the pop back stack.
        getFragmentManager().popBackStack();
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends SherlockFragment {

        FragmentTabHost mTabHost;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_web_view, container, false);

            return rootView;
        }
    }

}
