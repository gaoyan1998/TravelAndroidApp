package com.ikiler.travel;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MeishiActivity extends AppCompatActivity {

    private List<MeishiIcons > iconses = new ArrayList<MeishiIcons >();
    public static  int[] meishi_tu ={
            R.drawable.ms1,  R.drawable.ms2, R.drawable.ms3,  R.drawable.ms4, R.drawable.ms5,
            R.drawable.ms6,  R.drawable.ms7, R.drawable.ms8,  R.drawable.ms9, R.drawable.ms10,
            R.drawable.ms11,  R.drawable.ms12, R.drawable.ms13,  R.drawable.ms14, R.drawable.ms15,
    };
    private  String [] meishi_name ={
            "糊汤米粉","糯米包油条","豆腐脑","糖糍粑","万氏米酒",
            "三鲜豆皮","热干面","水饺","面窝","武昌清蒸鱼",
            "长春太极饼","武汉鸭脖","吉祥混沌","龙虾","螃蟹"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meishi);
      //  getSupportActionBar().hide();
        GridView gridView = (GridView) findViewById(R.id.mygridview);
        getData();
        gridView.setAdapter(new MyBaseAdapter()  );
        final Resources res = this.getResources();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MeishiActivity.this,MeishiDemo.class);
                intent.putExtra("meishi_info",i);
                startActivity(intent);
            }
        });
    }
    public void getData(){
        for (int i=0;i<meishi_name.length;i++){
            MeishiIcons  icons = new MeishiIcons ();
            icons.setIcon(meishi_tu[i]);
            icons.setIcon_name(meishi_name[i]);
            iconses.add(icons);
        }
    }


    class MyBaseAdapter extends BaseAdapter {
        class ViewHolder{
            ImageView imageView;
            TextView textView;
        }
        @Override
        public int getCount() {
            return iconses.size();
        }

        @Override
        public Object getItem(int i) {
            return iconses.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 =view;
            MeishiIcons item = iconses.get(i);
            ViewHolder viewHolder = null;
            TextView textView =(TextView) findViewById(R.id.meishi_tx);
            if (view1==null){

                view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.meishi_item,viewGroup,false);

                viewHolder = new ViewHolder();
                viewHolder.imageView =(ImageView) view1.findViewById(R.id.meishi_tv);
                viewHolder.textView =(TextView) view1.findViewById(R.id.meishi_tx);

                view1.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)view1.getTag();
            }
            viewHolder.imageView.setImageResource(item.getIcon());
            viewHolder.textView.setText(item.getIcon_name());
            return view1;
        }
    }
}

