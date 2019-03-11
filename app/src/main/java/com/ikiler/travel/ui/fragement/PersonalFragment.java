package com.ikiler.travel.ui.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Base.BaseFragement;
import com.ikiler.travel.ui.PhoneActivity;
import com.ikiler.travel.Model.CallBack;
import com.ikiler.travel.R;
import com.ikiler.travel.ui.LoginActivity;
import com.ikiler.travel.ui.MyTicketActivity;
import com.ikiler.travel.ui.NoteActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PersonalFragment extends BaseFragement {

    @BindView(R.id.imageView_userIcon)
    ImageView imageViewUserIcon;
    @BindView(R.id.username)
    TextView username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        username.setText(((BaseActivity) getActivity()).getUser().getName());
    }

    @OnClick({R.id.layout_setting,R.id.layout_logout,R.id.layout_phone,R.id.layout_note,R.id.layout_ticket})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.layout_ticket:
                startActivity(new Intent(getActivity(), MyTicketActivity.class));
                break;
            case R.id.layout_logout:
                BaseActivity activity = (BaseActivity) getActivity();
                activity.showDialog("确定注销吗", new CallBack() {
                    @Override
                    public void calBack(boolean flage, int code) {
                        if (flage){
                            getMmkv().clearAll();
                            getMmkv().commit();
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            getActivity().finish();
                        }
                    }
                });
                break;
            case R.id.layout_note:
                startActivity(new Intent(getActivity(), NoteActivity.class));
                break;
            case R.id.layout_phone:
                startActivity(new Intent(getActivity(), PhoneActivity.class));
                break;
            case R.id.layout_setting:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
