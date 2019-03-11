package com.ikiler.travel.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ikiler.travel.Adapter.MyTicketRecyleAdapter;
import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Base.BaseRecyleAdapter;
import com.ikiler.travel.Model.CallBack;
import com.ikiler.travel.Model.bean.PersonTicket;
import com.ikiler.travel.Model.bean.Ticket;
import com.ikiler.travel.R;
import com.ikiler.travel.util.APIconfig;
import com.ikiler.travel.util.DialogUtil;
import com.ikiler.travel.util.LiveBus;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTicketActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list)
    RecyclerView list;

    MyTicketRecyleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        init();
        initListener();
    }

    private void init() {
        adapter = new MyTicketRecyleAdapter();
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        APIconfig.getMyTicket();
        LiveBus.getDefault().subscribe("MyTicket", PersonTicket.class).observe(this, new Observer<PersonTicket>() {
            @Override
            public void onChanged(PersonTicket personTicket) {
                Log.e("ml",personTicket.getList().get(0).getFromName());
                adapter.setList(personTicket.getList());
            }
        });
    }
    private void initListener() {
        adapter.setOnRecyclerItemClickLitener(new BaseRecyleAdapter.onRecyclerItemClickLitener() {
            @Override
            public void onRecyclerItemClick(Object object, int position) {

            }
        });
        adapter.setOnRecyclerItemLongClicjk(new BaseRecyleAdapter.onRecyclerItemLongClicjk() {
            @Override
            public void onRecyclerItemLongClick(Object object, int position) {
                PersonTicket ticket = (PersonTicket) object;
                DialogUtil dialogUtil = new DialogUtil();
                dialogUtil.showSureDialog(MyTicketActivity.this, "确定退票吗？", new CallBack() {
                    @Override
                    public void calBack(boolean flage, int code) {
                        if (flage){
                            dialogUtil.showProgress(MyTicketActivity.this,"正在退票....");
                            APIconfig.delTicket(ticket);
                            LiveBus.getDefault().subscribe("Net").observe(MyTicketActivity.this, new Observer<Object>() {
                                @Override
                                public void onChanged(Object b) {
                                    boolean flage = (boolean) b;
                                    dialogUtil.cancelNetDialog();
                                    if (flage){
                                        showToast("退票成功");
                                    }else {
                                        showToast("退票失败");
                                    }
                                    APIconfig.getMyTicket();
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
