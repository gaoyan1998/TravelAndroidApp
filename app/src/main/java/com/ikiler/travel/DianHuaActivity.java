package com.ikiler.travel;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class DianHuaActivity extends AppCompatActivity {


    ListView listView;
    List<Map<String, String>> maps;
    EditText editText;
    DianHuaSQLUtil sqlUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dian_hua);
     //   getSupportActionBar().hide();
        listView = (ListView) findViewById(R.id.mylistview);
        editText = (EditText) findViewById(R.id.search_edit);
        sqlUtil = new DianHuaSQLUtil(DianHuaActivity.this);

        SharedPreferences sharedPreferences = getSharedPreferences("sqled", MODE_APPEND);
        boolean b = sharedPreferences.getBoolean("boolean", false);
        if (b==false) {
            sqlUtil.add();
            sharedPreferences.edit().putBoolean("boolean", true).apply();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                maps = sqlUtil.select(editText.getText().toString());
                listView.setAdapter(new ListAdaapter());
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                TextView textView = (TextView) view.findViewById(R.id.dianhua_number);
                                String s= textView .getText().toString().trim();
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:"+s));
                                startActivity(intent);
                    }
                });

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        maps = sqlUtil.select("");
        for (int i = 0; i < maps.size(); i++) {
            Log.i("TAG", "onCreate: " + maps.get(i).get("name"));
            Log.i("TAG", "onCreate: " + maps.get(i).get("number"));
            Log.i("TAG", "--------------------------------");
        }
        listView.setAdapter(new ListAdaapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view.findViewById(R.id.dianhua_number);
                String s= textView .getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+s));
                startActivity(intent);
            }
        });
    }
    class ListAdaapter extends BaseAdapter{
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
            if (view==null){
                viewHolder = new ViewHolder();
                view = getLayoutInflater().inflate(R.layout.dianhua_item, null);
                viewHolder.name_tv =(TextView) view.findViewById(R.id.dianhua_text);
                viewHolder.number_tv = (TextView) view.findViewById(R.id.dianhua_number);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.name_tv.setText(maps.get(i).get("name"));
            viewHolder.number_tv.setText(maps.get(i).get("number"));
            return view;

        }
        class ViewHolder {
            TextView name_tv;
            TextView number_tv;
        }
    }
}
