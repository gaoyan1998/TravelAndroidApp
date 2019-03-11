package com.ikiler.travel.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Model.bean.Phone;
import com.ikiler.travel.R;
import com.ikiler.travel.util.APIconfig;
import com.ikiler.travel.util.LiveBus;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPhoneActivity extends BaseActivity {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.number)
    EditText number;
    @BindView(R.id.time_select)
    LinearLayout timeSelect;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.save)
    public void onViewClicked() {
        String mName = name.getText().toString();
        String mNumber = number.getText().toString();
        if (TextUtils.isEmpty(mName) || TextUtils.isEmpty(mNumber)) {
            showToast("请输入完整");
            return;
        }
        Phone phone = new Phone();
        phone.setName(mName);
        phone.setNumber(mNumber);
        APIconfig.addPhone(phone);
        LiveBus.getDefault().subscribe("Net").observe(AddPhoneActivity.this, new Observer<Object>() {
            @Override
            public void onChanged(Object b) {
                boolean flage = (boolean) b;
                cancelNetDialog();
                if (flage) {
                    showToast("成功");
                } else {
                    showToast("失败");
                }
                APIconfig.refeshPhone();
                finish();
            }
        });

    }
}
