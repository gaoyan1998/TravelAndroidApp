package com.ikiler.travel.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.ikiler.travel.Adapter.CitySelectAdapter;
import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Base.BaseRecyleAdapter;
import com.ikiler.travel.Model.bean.HotCity;
import com.ikiler.travel.R;
import com.ikiler.travel.util.APIconfig;
import com.ikiler.travel.util.LiveBus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CitySelectActivity extends BaseActivity {

    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.list)
    RecyclerView list;

    CitySelectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);
        ButterKnife.bind(this);
        adapter = new CitySelectAdapter();
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        inits();
    }

    private void inits() {
        LiveBus.getDefault().subscribe("City", HotCity.class).observe(this, new Observer<HotCity>() {
            @Override
            public void onChanged(HotCity hotCity) {
                adapter.setList(hotCity.getHeWeather6().get(0).getBasic());
            }
        });
        APIconfig.refershHotCity("");
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                APIconfig.refershHotCity(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        adapter.setOnRecyclerItemClickLitener(new BaseRecyleAdapter.onRecyclerItemClickLitener() {
            @Override
            public void onRecyclerItemClick(Object object, int position) {
                HotCity.HeWeather6Bean.BasicBean city = (HotCity.HeWeather6Bean.BasicBean) object;
                getMmkv().encode("city",city.getCid());
                APIconfig.refershWeather(city.getCid());
                finish();
            }
        });
    }
}
