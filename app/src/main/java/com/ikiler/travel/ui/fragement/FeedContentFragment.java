package com.ikiler.travel.ui.fragement;


import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.ikiler.travel.Model.FeedLiveDataModel;
import com.ikiler.travel.Model.RssItem;
import com.ikiler.travel.R;

import java.net.URL;

public class FeedContentFragment extends Fragment {

    TextView title,time;
    TextView content;
    Handler handler;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        content = view.findViewById(R.id.feed_text);
        time = view.findViewById(R.id.time);
        title = view.findViewById(R.id.title);
        handler = new Handler();
        FeedLiveDataModel.instance().getMutableLiveData().observe(this, new Observer<RssItem>() {
            @Override
            public void onChanged(RssItem rssItem) {
                loadContent(rssItem.getDescription());
//                content.loadData(rssItem.getDescription(),null,null);
//                textView.setText(Html.fromHtml(rssItem.getDescription()));
                title.setText(rssItem.getTitle());
                time.setText(rssItem.getPubdate());
            }
        });
    }

    private void loadContent(final String string){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Spanned text = Html.fromHtml(string, imgGetter, null);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        content.setText(text);
                    }
                });
            }
        }).start();
    }
}
