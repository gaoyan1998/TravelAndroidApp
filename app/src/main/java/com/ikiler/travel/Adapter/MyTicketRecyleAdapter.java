package com.ikiler.travel.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ikiler.travel.Base.BaseRecyleAdapter;
import com.ikiler.travel.Model.bean.PersonTicket;
import com.ikiler.travel.Model.bean.Ticket;
import com.ikiler.travel.R;
import com.ikiler.travel.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;

public class MyTicketRecyleAdapter extends BaseRecyleAdapter<PersonTicket, MyTicketRecyleAdapter.ViewHolder> {


    @Override
    public void onMyBindViewHolder(ViewHolder holder, int position) {
        PersonTicket ticket = getList().get(position);
        Date start = DateUtil.StrToDate(getList().get(position).getTimeFrom());
        Date end = DateUtil.StrToDate(getList().get(position).getTimeTo());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        holder.mStart.setText(calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE));
        calendar.setTime(end);
        holder.mEnd.setText(calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE));
        holder.mEndName.setText(ticket.getToName());
        holder.mStartName.setText(ticket.getFromName());
        holder.mItem = getList().get(position);
    }

    @Override
    public ViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticket_list, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseRecyleAdapter.ViewHolder {
        public TextView mStart, mEnd, mPrice, mRest, mStartName, mEndName;
        public PersonTicket mItem = null;
        public View layoutCity, layoutTicket;

        public ViewHolder(View view) {
            super(view);
            mStart = view.findViewById(R.id.start);
            mEnd = view.findViewById(R.id.end);
            mPrice = view.findViewById(R.id.price);
            mRest = view.findViewById(R.id.rest);
            mStartName = view.findViewById(R.id.startName);
            mEndName = view.findViewById(R.id.endName);
            layoutCity = view.findViewById(R.id.layout_city);
            layoutCity.setVisibility(View.VISIBLE);
            layoutTicket = view.findViewById(R.id.layout_ticket);
            layoutTicket.setVisibility(View.GONE);
        }

        @Override
        public PersonTicket getObject() {
            return mItem;
        }

    }
}
