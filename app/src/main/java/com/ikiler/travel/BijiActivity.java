package com.ikiler.travel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ikiler.travel.ui.NoteEditActivity;

import java.util.List;
import java.util.Map;
import androidx.appcompat.app.AppCompatActivity;

public class BijiActivity extends AppCompatActivity {

    ImageView iv_biji;
    ImageView default_img;
    ListView bijiListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biji);
//        getSupportActionBar().hide();
        iv_biji =(ImageView) findViewById(R.id.Biji_bianji);
        default_img = (ImageView) findViewById(R.id.default_img);
        bijiListView = (ListView) findViewById(R.id.Bijilistview);
        iv_biji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BijiActivity.this, NoteEditActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        initListView();

    }

    public void initListView(){
        final List<Map<String,String >> maps = new BijiSQLUtil(BijiActivity.this).select();
        if(maps.size()>0){
            default_img.setVisibility(View.INVISIBLE);
            default_img.setEnabled(false);
        }
        bijiListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return maps.size();
            }

            @Override
            public Object getItem(int i) {
                return maps.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                ViewHolder viewHolder;
                if(view == null){
                    viewHolder = new ViewHolder();
                    view = getLayoutInflater().inflate(R.layout.biji_list_item,null);
                    viewHolder.title_tv =(TextView) view.findViewById(R.id.biji_title);
                    view.setTag(maps.get(i).get("id").toString());
                    viewHolder.date_tv =(TextView) view.findViewById(R.id.biji_date);
                    viewHolder.time_tv =(TextView) view.findViewById(R.id.biji_time);
                    view.setTag(viewHolder);
                }else{
                    viewHolder = (ViewHolder) view.getTag();
                }
                viewHolder.title_tv.setText(maps.get(i).get("title"));
                viewHolder.date_tv.setText(maps.get(i).get("date"));
                viewHolder.time_tv.setText(maps.get(i).get("time"));
                return view;
            }

            class ViewHolder{
                TextView title_tv;
                TextView date_tv;
                TextView time_tv;
            }
        });
        bijiListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new BijiSQLUtil(BijiActivity.this).delete();
                initListView();
                return true;
            }
        });
    }
}
