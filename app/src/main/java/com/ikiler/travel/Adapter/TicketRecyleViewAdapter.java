package com.ikiler.travel.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikiler.travel.Base.BaseRecyleAdapter;
import com.ikiler.travel.Model.bean.Food;
import com.ikiler.travel.Model.bean.Ticket;
import com.ikiler.travel.R;
import com.ikiler.travel.util.DateUtil;

import java.util.Calendar;
import java.util.Date;


public class TicketRecyleViewAdapter extends BaseRecyleAdapter<Ticket, TicketRecyleViewAdapter.ViewHolder> {
    @Override
    public void onMyBindViewHolder(ViewHolder holder, int position) {
        Date start = DateUtil.StrToDate(getList().get(position).getTimeFrom());
        Date end = DateUtil.StrToDate(getList().get(position).getTimeTo());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        holder.mStart.setText(calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE));
        calendar.setTime(end);
        holder.mEnd.setText(calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE));
        holder.mRest.setText(getList().get(position).getRest()+"");
        holder.mPrice.setText(getList().get(position).getPrice()+"");
        holder.mItem = getList().get(position);
//        holder.mStartName.setText(getList().get(position).getFromName());
//        holder.mEndName.setText(getList().get(position).getToName());
    }

    @Override
    public ViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticket_list, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseRecyleAdapter.ViewHolder {
        public TextView mStart,mEnd,mPrice,mRest,mStartName,mEndName;
        public Ticket mItem;

        public ViewHolder(View view) {
            super(view);
            mStart = view.findViewById(R.id.start);
            mEnd = view.findViewById(R.id.end);
            mPrice = view.findViewById(R.id.price);
            mRest = view.findViewById(R.id.rest);
            mStartName = view.findViewById(R.id.startName);
            mEndName = view.findViewById(R.id.endName);
        }

        @Override
        public Ticket getObject() {
            return mItem;
        }

    }
}
