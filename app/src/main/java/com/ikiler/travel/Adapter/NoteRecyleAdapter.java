package com.ikiler.travel.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ikiler.travel.Base.BaseRecyleAdapter;
import com.ikiler.travel.Model.bean.Note;
import com.ikiler.travel.R;

import androidx.annotation.NonNull;

public class NoteRecyleAdapter extends BaseRecyleAdapter<Note, NoteRecyleAdapter.ViewHolder> {


    @Override
    public void onMyBindViewHolder(ViewHolder holder, int position) {
        position = getList().size()-position-1;
        Note note = getList().get(position);
        holder.note = note;
        holder.title_tv.setText(note.getContent());
    }

    @Override
    public ViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.biji_list_item, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseRecyleAdapter.ViewHolder{

        private Note note;
        private TextView title_tv;
        private TextView date_tv;
        private TextView time_tv;

        public ViewHolder(@NonNull View view) {
            super(view);
            title_tv =(TextView) view.findViewById(R.id.biji_title);
            date_tv =(TextView) view.findViewById(R.id.biji_date);
            time_tv =(TextView) view.findViewById(R.id.biji_time);
        }

        @Override
        public Object getObject() {
            return note;
        }
    }
}
