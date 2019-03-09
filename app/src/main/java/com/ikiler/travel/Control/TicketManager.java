package com.ikiler.travel.Control;

import com.ikiler.travel.Base.BaseLiveData;
import com.ikiler.travel.Model.bean.Addr;
import com.ikiler.travel.ui.fragement.TrainTicketFragment;

import java.util.List;

import androidx.lifecycle.Observer;

public class TicketManager {

    TrainTicketFragment fragment;


    public TicketManager(TrainTicketFragment fragment) {
        this.fragment = fragment;

    }
}
