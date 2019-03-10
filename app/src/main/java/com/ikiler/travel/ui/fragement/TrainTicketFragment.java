package com.ikiler.travel.ui.fragement;

 import android.content.Intent;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ikiler.travel.util.APIconfig;
import com.ikiler.travel.Base.BaseFragement;
 import com.ikiler.travel.Model.CallBack;
 import com.ikiler.travel.Model.TrainTicketLiveModel;
 import com.ikiler.travel.Model.bean.Addr;
 import com.ikiler.travel.R;
 import com.ikiler.travel.ui.TicketShowActivity;

 import org.feezu.liuli.timeselector.TimeSelector;

 import java.text.SimpleDateFormat;
 import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
 import androidx.lifecycle.Observer;
 import androidx.lifecycle.ViewModelProviders;
 import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TrainTicketFragment extends BaseFragement {

    @BindView(R.id.from)
    TextView from;
    @BindView(R.id.to)
    TextView to;
    @BindView(R.id.time_select)
    LinearLayout timeSelect;
    @BindView(R.id.search_ticket)
    Button searchTicket;
    @BindView(R.id.travel_time)
    TextView travel_time;

    private TrainTicketLiveModel liveData;
    private Addr addrFrom,adrTo;
    List<Addr> list;

    private int SELECT_FROM = 0;
    private int SELECT_TO = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_train_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        liveData = ViewModelProviders.of(getActivity()).get(TrainTicketLiveModel.class);
        liveData.getMutableLiveDatas().observe(this, new Observer<List<Addr>>() {
            @Override
            public void onChanged(List<Addr> addrs) {
                list = addrs;
                getDialogUtil().cancelNetDialog();
            }
        });
        getDialogUtil().showProgress(getActivity(),"加载中....");
        APIconfig.getCity(liveData);
    }

    @OnClick({R.id.from, R.id.to, R.id.time_select, R.id.search_ticket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.from:
                showSelectCity(SELECT_FROM);
                break;
            case R.id.to:
                showSelectCity(SELECT_TO);
                break;
            case R.id.time_select:
               showDataPicker();
                break;
            case R.id.search_ticket:
                if (addrFrom == null || adrTo == null){
                    showToast("请选择城市");
                    return;
                }
                Intent  intent = new Intent(getActivity(), TicketShowActivity.class);
                intent.putExtra("from",addrFrom.getId());
                intent.putExtra("to",adrTo.getId());
                intent.putExtra("fromName",addrFrom.getName());
                intent.putExtra("toName",adrTo.getName());
                startActivity(intent);
                break;
        }
    }

    public void showDataPicker(){
        String fromDate = new SimpleDateFormat("yyyy-MM-dd  hh:mm")
                .format(new Date());
        TimeSelector timeSelector = new TimeSelector(getActivity(), new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                travel_time.setText(time);
            }
        }, fromDate, "2050-12-31 23:59:59");
        timeSelector.setMode(TimeSelector.MODE.YMD);
        timeSelector.show();
    }

    public void showSelectCity(int flage){
        if (null == list){
            showToast("数据未下载");
            return;
        }
        String[] items = new String[list.size()];
        for (int i = 0; i<list.size();i++){
            items[i] = list.get(i).getName();
        }
        getDialogUtil().dialogList(getActivity(), items, "选择城市", new CallBack() {
            @Override
            public void calBack(boolean isSuccess, int code) {
                if (isSuccess){
                    if (flage == SELECT_FROM){
                        addrFrom = list.get(code);
                        from.setText(addrFrom.getName());
                    } else{
                        adrTo = list.get(code);
                        to.setText(adrTo.getName());
                    }
                }
            }
        });
    }
}
