package com.ikiler.travel.ui.Food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ikiler.travel.util.APIconfig;
import com.ikiler.travel.Base.BaseRecyleAdapter;
import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Model.CallBack;
import com.ikiler.travel.Model.FoodLiveDataModel;
import com.ikiler.travel.Model.bean.Food;
import com.ikiler.travel.R;
import com.ikiler.travel.Adapter.FooditemRecyclerViewAdapter;
import com.ikiler.travel.util.HttpConfig;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FoodListActivity extends BaseActivity {

    private RecyclerView recyclerView;
    protected FoodLiveDataModel model;
    private FooditemRecyclerViewAdapter adapter = new FooditemRecyclerViewAdapter();


    private BaseRecyleAdapter.onRecyclerItemClickLitener mListener = new BaseRecyleAdapter.onRecyclerItemClickLitener() {
        @Override
        public void onRecyclerItemClick(Object object, int position) {
            itemClick((Food) object);
        }
    };
    private BaseRecyleAdapter.onRecyclerItemLongClicjk mOnListLongClickListener = new BaseRecyleAdapter.onRecyclerItemLongClicjk() {

        @Override
        public void onRecyclerItemLongClick(final Object object, int position) {
            showDialog("确定删除吗", new CallBack() {
                @Override
                public void calBack(boolean flage, int code) {
                    cancelNetDialog();
                    switch (code) {
                        case CODE_FROM_BASEACTIVITY:
                            if (flage){
                                showNetProgress();
                                itemDelete((Food) object,this);
                            }
                            break;
                        case HttpConfig.REQUEST_SUCCESS:
                            showToast("删除成功");
                            refersh();
                            break;
                        case HttpConfig.NET_ERR:
                            showToast("网络连接失败！");
                            break;
                        default:
                            showToast("出错");
                    }
                }
            });
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
        recyclerView.setAdapter(adapter);
        adapter.setOnRecyclerItemClickLitener(mListener);
        adapter.setOnRecyclerItemLongClicjk(mOnListLongClickListener);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.getMutableLiveData().setValue(null);
                startActivity(new Intent(getApplicationContext(), FoodEditActivity.class));
            }
        });
        refersh();
        initLiveData();
    }

    private void initLiveData() {
        model = FoodLiveDataModel.instance();
        model.getMutableLiveDatas().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                adapter.setList(foods);
                cancelNetDialog();
            }
        });
    }

    protected void refersh(){
        APIconfig.refershFoods();
    }
    protected void itemClick(Food item){
        model.getMutableLiveData().setValue(item);
        startActivity(new Intent(getApplicationContext(), FoodEditActivity.class));
    }
    protected void itemDelete(Food food,CallBack callBack){
        APIconfig.deleteFood(food.getId(), callBack);
    }
}
