package com.ikiler.travel.ui.Food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ikiler.travel.APIconfig;
import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Model.FoodViewModel;
import com.ikiler.travel.Model.bean.Code;
import com.ikiler.travel.Model.bean.Food;
import com.ikiler.travel.Model.OnListFragmentInteractionListener;
import com.ikiler.travel.R;
import com.ikiler.travel.Adapter.MyfooditemRecyclerViewAdapter;
import com.ikiler.travel.util.GsonUtil;
import com.ikiler.travel.util.OkHttpUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FoodListActivity extends BaseActivity {

    RecyclerView recyclerView;

    private OnListFragmentInteractionListener mListener = new OnListFragmentInteractionListener() {
        @Override
        public void onListFragmentInteraction(Food item) {
            model.getMutableLiveData().setValue(item);
        }
    };

    private FoodViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),FoodEditActivity.class));
            }
        });
        initLiveData();
        APIconfig.getFoods(model);
    }

    private void initLiveData() {
        model = ViewModelProviders.of(FoodListActivity.this).get(FoodViewModel.class);
        model.getMutableLiveDatas().observe(this,new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                recyclerView.setAdapter(new MyfooditemRecyclerViewAdapter(FoodListActivity.this,foods,mListener));
            }
        });
    }

}
