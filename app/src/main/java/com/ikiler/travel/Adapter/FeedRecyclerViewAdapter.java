package com.ikiler.travel.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ikiler.travel.R;
import com.ikiler.travel.ui.fragement.dummy.DummyContent.DummyItem;

public class FeedRecyclerViewAdapter extends BaseRecyleAdapter<DummyItem, FeedRecyclerViewAdapter.ViewHolder> {

    @Override
    public ViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onMyBindViewHolder(ViewHolder holder, int position) {
        holder.mItem = getList().get(position);
        holder.mIdView.setText(getList().get(position).id);
        holder.mContentView.setText(getList().get(position).content);
    }

    public class ViewHolder extends BaseRecyleAdapter.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mIdView = view.findViewById(R.id.item_number);
            mContentView = view.findViewById(R.id.content);
        }
        @Override
        public Object getObject() {
            return mItem;
        }
    }
}
