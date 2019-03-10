package com.ikiler.travel.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ikiler.travel.Adapter.TicketRecyleViewAdapter;
import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Base.BaseRecyleAdapter;
import com.ikiler.travel.Model.CallBack;
import com.ikiler.travel.Model.bean.Ticket;
import com.ikiler.travel.R;
import com.ikiler.travel.util.APIconfig;
import com.ikiler.travel.util.DialogUtil;
import com.ikiler.travel.util.LiveBus;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TicketShowActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list)
    RecyclerView list;

    private TicketRecyleViewAdapter adapter;
    private Ticket ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_show);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        init();
        initLiveDate();
        initListener();
        APIconfig.getTickets(ticket);
    }

    private void initListener() {
        adapter.setOnRecyclerItemClickLitener(new BaseRecyleAdapter.onRecyclerItemClickLitener() {
            @Override
            public void onRecyclerItemClick(Object object, int position) {
                Ticket ticket = (Ticket) object;
                DialogUtil dialogUtil = new DialogUtil();
                dialogUtil.showSureDialog(TicketShowActivity.this, "确定购买车票吗", new CallBack() {
                    @Override
                    public void calBack(boolean flage, int code) {
                        if (flage){
                            dialogUtil.showProgress(TicketShowActivity.this,"正在购买....");
                            APIconfig.buyTicket(ticket.getId()+"");
                            LiveBus.getDefault().subscribe("Net").observe(TicketShowActivity.this, new Observer<Object>() {
                                @Override
                                public void onChanged(Object b) {
                                    boolean flage = (boolean) b;
                                    dialogUtil.cancelNetDialog();
                                    if (flage){
                                        showToast("购买成功");
                                    }else {
                                        showToast("购买失败");
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void init() {
        ticket = new Ticket();
        Intent intent = getIntent();
        ticket.setTo(intent.getIntExtra("to", 0));
        ticket.setFrom(intent.getIntExtra("from", 0));
        ticket.setFromName(intent.getStringExtra("fromName"));
        ticket.setToName(intent.getStringExtra("tomName"));
        title.setText("购买车票");
        adapter = new TicketRecyleViewAdapter();
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list.setAdapter(adapter);
    }

    private void initLiveDate() {
        LiveBus.getDefault().subscribe("Ticket", Ticket.class).observe(this, new Observer<Ticket>() {
            @Override
            public void onChanged(Ticket ticket) {
                adapter.setList(ticket.getLists());
                adapter.notifyDataSetChanged();
            }
        });
    }
}
