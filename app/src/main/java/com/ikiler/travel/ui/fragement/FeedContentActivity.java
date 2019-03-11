package com.ikiler.travel.ui.fragement;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Model.FeedLiveDataModel;
import com.ikiler.travel.Model.RssItem;
import com.ikiler.travel.R;

import java.net.URL;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedContentActivity extends BaseActivity {

    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            URL url = null;
            try {
                url = new URL(source);
                drawable = Drawable.createFromStream(url.openStream(), "img");
            } catch (Exception e) {
                return null;
            }
            drawable.setBounds(0, 0, 500, 500);
            return drawable;
        }
    };
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.feed_text)
    TextView feedText;

    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_feed_content);
        ButterKnife.bind(this);
        handler = new Handler();
        Intent intent = getIntent();
        String mtitle = intent.getStringExtra("title");
        String mtime = intent.getStringExtra("time");
        String mcontent = intent.getStringExtra("content");
        title.setText(mtitle);
        time.setText(mtime);
        loadContent(mcontent);
    }

    private void loadContent(final String string) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Spanned text = Html.fromHtml(string, imgGetter, null);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        feedText.setText(text);
                    }
                });
            }
        }).start();
    }
}
