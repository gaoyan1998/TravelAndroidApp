package com.ikiler.travel.ui.Food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ikiler.travel.APIconfig;
import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Model.FoodViewModel;
import com.ikiler.travel.Model.OnListLongClickListener;
import com.ikiler.travel.Model.bean.Food;
import com.ikiler.travel.Model.OnListFragmentInteractionListener;
import com.ikiler.travel.R;
import com.ikiler.travel.Adapter.MyfooditemRecyclerViewAdapter;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FoodListActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private FoodViewModel model;
    private MyfooditemRecyclerViewAdapter adapter = new MyfooditemRecyclerViewAdapter();


    private OnListFragmentInteractionListener mListener = new OnListFragmentInteractionListener() {
        @Override
        public void onListFragmentInteraction(Food item) {
            startActivity(new Intent(getApplicationContext(), FoodEditActivity.class));
            model.getMutableLiveData().setValue(item);
        }
    };
    private OnListLongClickListener mOnListLongClickListener = new OnListLongClickListener() {
        @Override
        public void onLongClick(final Food food) {
            showDialog("确定删除吗", new CallBack() {
                @Override
                public void calBack(boolean flage) {
                    if (flage) {
                        showNetProgress();
                        APIconfig.deleteFood(food.getId());
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
        adapter.setmListener(mListener);
        adapter.setmLongClick(mOnListLongClickListener);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.getMutableLiveData().setValue(null);
                startActivity(new Intent(getApplicationContext(), FoodEditActivity.class));
            }
        });
        initLiveData();
        APIconfig.refershFoods();
    }

    private void initLiveData() {
        model = FoodViewModel.instance();
        model.getMutableLiveDatas().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                adapter.upDate(foods);
                cancelNetDialog();
            }
        });
    }

}
