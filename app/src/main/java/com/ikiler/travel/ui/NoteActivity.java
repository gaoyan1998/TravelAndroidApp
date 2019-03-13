package com.ikiler.travel.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ikiler.travel.Adapter.NoteRecyleAdapter;
import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Base.BaseRecyleAdapter;
import com.ikiler.travel.Model.CallBack;
import com.ikiler.travel.Model.bean.Note;
import com.ikiler.travel.R;
import com.ikiler.travel.util.APIconfig;
import com.ikiler.travel.util.DialogUtil;
import com.ikiler.travel.util.LiveBus;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list)
    RecyclerView list;

    NoteRecyleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        init();
        initListener();
    }

    private void init() {
        adapter = new NoteRecyleAdapter();
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list.setAdapter(adapter);
        APIconfig.refeshNote();

    }
    private void initListener() {
        adapter.setOnRecyclerItemClickLitener(new BaseRecyleAdapter.onRecyclerItemClickLitener() {
            @Override
            public void onRecyclerItemClick(Object object, int position) {
                Note note = (Note) object;
                Intent intent = new Intent(getApplicationContext(),ShowNoteActivity.class);
                intent.putExtra("content",note.getContent());
                startActivity(intent);
            }
        });
        adapter.setOnRecyclerItemLongClicjk(new BaseRecyleAdapter.onRecyclerItemLongClicjk() {
            @Override
            public void onRecyclerItemLongClick(Object object, int position) {
                Note note = (Note) object;
                DialogUtil dialogUtil = new DialogUtil();
                dialogUtil.showSureDialog(NoteActivity.this, "确定删除吗？", new CallBack() {
                    @Override
                    public void calBack(boolean flage, int code) {
                        if (flage){
                            dialogUtil.showProgress(NoteActivity.this,"正在删除....");
                            APIconfig.delNote(note);
                            LiveBus.getDefault().subscribe("Net").observe(NoteActivity.this, new Observer<Object>() {
                                @Override
                                public void onChanged(Object b) {
                                    boolean flage = (boolean) b;
                                    dialogUtil.cancelNetDialog();
                                    if (flage){
                                        showToast("删除成功");
                                    }else {
                                        showToast("删除失败");
                                    }
                                    APIconfig.refeshNote();
                                }
                            });
                        }
                    }
                });
            }
        });

        LiveBus.getDefault().subscribe("Note", Note.class).observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                List<Note> list = note.getList();
                adapter.setList(list);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), NoteEditActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
