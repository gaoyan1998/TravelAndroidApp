package com.ikiler.travel.Model;

import com.ikiler.travel.Base.BaseLiveData;
import com.ikiler.travel.Model.bean.Addr;
import com.ikiler.travel.Model.bean.Ticket;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class TrainTicketLiveModel extends BaseLiveData<Addr> {
    List<Addr> list;
    MutableLiveData<Ticket> ticket;

    public MutableLiveData<Ticket> getTicket() {
        if (ticket == null)
            ticket = new MutableLiveData<>();
        return ticket;
    }

    public List<Addr> getList() {
        return list;
    }

    public void setList(List<Addr> list) {
        this.list = list;
    }
}
