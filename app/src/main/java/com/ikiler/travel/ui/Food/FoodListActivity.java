package com.ikiler.travel.ui.Food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ikiler.travel.APIconfig;
import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Model.Code;
import com.ikiler.travel.Model.Food;
import com.ikiler.travel.Model.OnListFragmentInteractionListener;
import com.ikiler.travel.R;
import com.ikiler.travel.Adapter.MyfooditemRecyclerViewAdapter;
import com.ikiler.travel.util.GsonUtil;
import com.ikiler.travel.util.OkHttpUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FoodListActivity extends BaseActivity {

    RecyclerView recyclerView;
    private OnListFragmentInteractionListener mListener = new OnListFragmentInteractionListener() {
        @Override
        public void onListFragmentInteraction(Food item) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        getData();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),FoodEditActivity.class));
            }
        });
    }

    public void getData() {
        Map<String,String> map = new HashMap<>();
        map.put("action","select");
        OkHttpUtil.post(APIconfig.Food, map, new OkHttpUtil.DataCallBack() {
            @Override
            public void calback(String data, boolean flage) {
                if (flage){
                    Code code = GsonUtil.GsonToBean(data,Code.class);
                    List<Food> foodList = GsonUtil.jsonToList(code.getData().replaceAll("\\\\",""),Food.class);
                    recyclerView.setAdapter(new MyfooditemRecyclerViewAdapter(FoodListActivity.this,foodList,mListener));
                }else {
                    showToast("获取美食信息失败");
                }
            }
        });
    }

}
