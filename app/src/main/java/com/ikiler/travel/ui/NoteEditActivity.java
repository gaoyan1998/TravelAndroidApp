package com.ikiler.travel.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Model.bean.Note;
import com.ikiler.travel.R;
import com.ikiler.travel.util.APIconfig;
import com.ikiler.travel.util.LiveBus;

import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteEditActivity extends BaseActivity {

    @BindView(R.id.biji_ok)
    ImageView bijiOk;
    @BindView(R.id.biji_neirong)
    EditText bijiNeirong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biji_bianji);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.biji_ok)
    public void onViewClicked() {
        Note note = new Note();
        note.setContent(bijiNeirong.getText().toString());
        showNetProgress();
        APIconfig.addNote(note);
        LiveBus.getDefault().subscribe("Net").observe(NoteEditActivity.this, new Observer<Object>() {
            @Override
            public void onChanged(Object b) {
                boolean flage = (boolean) b;
                cancelNetDialog();
                if (flage){
                    showToast("成功");
                }else {
                    showToast("失败");
                }
                APIconfig.refeshNote();
                finish();
            }
        });
    }
}
