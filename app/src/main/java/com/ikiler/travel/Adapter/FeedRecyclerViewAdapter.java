package com.ikiler.travel.Adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ikiler.travel.Base.BaseRecyleAdapter;
import com.ikiler.travel.Model.RssItem;
import com.ikiler.travel.R;

public class FeedRecyclerViewAdapter extends BaseRecyleAdapter<RssItem, FeedRecyclerViewAdapter.ViewHolder> {

    @Override
    public ViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onMyBindViewHolder(ViewHolder holder, int position) {
        holder.mItem = getList().get(position);
        holder.mIdView.setText(getList().get(position).getTitle());
        holder.mContentView.setText(Html.fromHtml(getList().get(position).getDescription()));
        holder.time.setText(getList().get(position).getPubdate());
    }

    public class ViewHolder extends BaseRecyleAdapter.ViewHolder {
        public TextView mIdView;
        public TextView mContentView;
        public TextView time;
        public RssItem mItem;

        public ViewHolder(View view) {
            super(view);
            mIdView = view.findViewById(R.id.item_title);
            mContentView = view.findViewById(R.id.content);
            time = view.findViewById(R.id.time);
        }

        @Override
        public Object getObject() {
            return mItem;
        }
    }
}
