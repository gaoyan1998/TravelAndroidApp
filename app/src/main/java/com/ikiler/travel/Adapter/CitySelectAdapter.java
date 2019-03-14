package com.ikiler.travel.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ikiler.travel.Base.BaseRecyleAdapter;
import com.ikiler.travel.Model.bean.HotCity;
import com.ikiler.travel.R;

import androidx.annotation.NonNull;

public class CitySelectAdapter extends BaseRecyleAdapter<HotCity.HeWeather6Bean.BasicBean, CitySelectAdapter.ViewHolder> {


    @Override
    public void onMyBindViewHolder(ViewHolder holder, int position) {
        holder.hotCity = getList().get(position);
        holder.title_tv.setText(holder.hotCity.getLocation());
    }

    @Override
    public ViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.biji_list_item, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseRecyleAdapter.ViewHolder{

        HotCity.HeWeather6Bean.BasicBean hotCity;
        TextView title_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_tv =(TextView) itemView.findViewById(R.id.biji_title);
        }

        @Override
        public Object getObject() {
            return hotCity;
        }
    }
}
