package com.ikiler.travel.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.Model.CallBack;
import com.ikiler.travel.Model.bean.Phone;
import com.ikiler.travel.R;
import com.ikiler.travel.util.APIconfig;
import com.ikiler.travel.util.DialogUtil;
import com.ikiler.travel.util.LiveBus;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_edit)
    EditText editText;
    @BindView(R.id.mylistview)
    ListView listView;
    ListAdaapter adaapter = new ListAdaapter();
    List<Phone> lists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dian_hua);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        APIconfig.refeshPhone();
        LiveBus.getDefault().subscribe("Phone", Phone.class).observe(PhoneActivity.this, new Observer<Phone>() {
            @Override
            public void onChanged(Phone phone) {
                lists = phone.getList();
                adaapter.setList(lists);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<Phone> newList = new ArrayList<>();
                for (Phone phone:lists){
                    if (phone.getNumber().contains(charSequence)){
                        newList.add(phone);
                    }
                }
                adaapter.setList(newList);

//                listView.setAdapter(new ListAdaapter());
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        TextView textView = (TextView) view.findViewById(R.id.dianhua_number);
//                        String s = textView.getText().toString().trim();
//                        Intent intent = new Intent(Intent.ACTION_DIAL);
//                        intent.setData(Uri.parse("tel:" + s));
//                        startActivity(intent);
//                    }
//                });

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listView.setAdapter(adaapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view.findViewById(R.id.dianhua_number);
                String s = textView.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + s));
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Phone phone = lists.get(position);
                DialogUtil dialogUtil = new DialogUtil();
                dialogUtil.showSureDialog(PhoneActivity.this, "确定删除吗？", new CallBack() {
                    @Override
                    public void calBack(boolean flage, int code) {
                        if (flage){
                            dialogUtil.showProgress(PhoneActivity.this,"正在删除....");
                            APIconfig.delPhone(phone);
                            LiveBus.getDefault().subscribe("Net").observe(PhoneActivity.this, new Observer<Object>() {
                                @Override
                                public void onChanged(Object b) {
                                    boolean flage = (boolean) b;
                                    dialogUtil.cancelNetDialog();
                                    if (flage){
                                        showToast("删除成功");
                                    }else {
                                        showToast("删除失败");
                                    }
                                    APIconfig.refeshPhone();
                                }
                            });
                        }
                    }
                });
                return true;
            }
        });
    }

    class ListAdaapter extends BaseAdapter {

        List<Phone> list = new ArrayList<>();

        public void setList(List<Phone> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = getLayoutInflater().inflate(R.layout.dianhua_item, null);
                viewHolder.name_tv = (TextView) view.findViewById(R.id.dianhua_text);
                viewHolder.number_tv = (TextView) view.findViewById(R.id.dianhua_number);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.name_tv.setText(list.get(i).getName());
            viewHolder.number_tv.setText(list.get(i).getNumber());
            return view;

        }

        class ViewHolder {
            TextView name_tv;
            TextView number_tv;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(getApplicationContext(), AddPhoneActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
