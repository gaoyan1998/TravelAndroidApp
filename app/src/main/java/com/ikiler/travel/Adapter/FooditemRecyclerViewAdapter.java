package com.ikiler.travel.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ikiler.travel.Model.bean.Food;
import com.ikiler.travel.R;

public class FooditemRecyclerViewAdapter extends BaseRecyleAdapter<Food, FooditemRecyclerViewAdapter.ViewHolder> {

    @Override
    public ViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_fooditem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onMyBindViewHolder(ViewHolder holder, int position) {
        position = getItemCount()-position-1;
        holder.mItem = getList().get(position);
        holder.mIdView.setText(getList().get(position).getName());
        Glide.with(holder.itemView.getContext()).load(holder.mItem.getImagePath())
                .into(holder.mImageView);
    }

    public class ViewHolder extends BaseRecyleAdapter.ViewHolder {
        public final TextView mIdView;
        public final ImageView mImageView;
        public Food mItem;

        public ViewHolder(View view) {
            super(view);
            mIdView = view.findViewById(R.id.food_name);
            mImageView = view.findViewById(R.id.food_img);
        }

        @Override
        public Food getObject() {
            return mItem;
        }

    }
}
