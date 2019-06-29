package id.ac.petra.informatika.museum.Information;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import id.ac.petra.informatika.museum.R;

public class Video extends AppCompatActivity {
    private String video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent intent = getIntent();
        video = intent.getStringExtra("videourl");
        Uri uri = Uri.parse(video);

        getWindow().setFormat(PixelFormat.UNKNOWN);

        WebView mVideoView = (WebView) findViewById(R.id.video);
        mVideoView.getSettings().setJavaScriptEnabled(true);
        mVideoView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mVideoView.loadUrl(video);
        mVideoView.setWebChromeClient(new WebChromeClient());
        mVideoView.requestFocus();
        finish();
    }
}
