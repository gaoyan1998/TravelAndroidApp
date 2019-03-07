package com.ikiler.travel.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ikiler.travel.Model.OnListLongClickListener;
import com.ikiler.travel.Model.bean.Food;
import com.ikiler.travel.Model.OnListFragmentInteractionListener;
import com.ikiler.travel.R;

import java.util.ArrayList;
import java.util.List;

/**
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyfooditemRecyclerViewAdapter extends RecyclerView.Adapter<MyfooditemRecyclerViewAdapter.ViewHolder> {

    private List<Food> mValues = new ArrayList<>();
    private OnListFragmentInteractionListener mListener;
    private OnListLongClickListener mLongClick;

    public MyfooditemRecyclerViewAdapter() {}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_fooditem, parent, false);
        return new ViewHolder(view);
    }

    public void upDate(List<Food> list){
        this.mValues = list;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getName());
        Glide.with(holder.mView.getContext()).load(holder.mItem.getImagePath())
                .into(holder.mImageView);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null !=mLongClick){
                    mLongClick.onLongClick(holder.mItem);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final ImageView mImageView;
        public Food mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.food_name);
            mImageView = view.findViewById(R.id.food_img);
        }

    }

    public OnListFragmentInteractionListener getmListener() {
        return mListener;
    }

    public void setmListener(OnListFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public OnListLongClickListener getmLongClick() {
        return mLongClick;
    }

    public void setmLongClick(OnListLongClickListener mLongClick) {
        this.mLongClick = mLongClick;
    }
}
