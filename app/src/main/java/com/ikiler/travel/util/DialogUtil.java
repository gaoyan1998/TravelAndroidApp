package com.ikiler.travel.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.ikiler.travel.Base.BaseActivity;
import com.ikiler.travel.MainActivity;
import com.ikiler.travel.Model.CallBack;
import com.ikiler.travel.R;

public class DialogUtil {

    protected ProgressDialog dialog;
    public void showProgress(Context context,String msg){
        if (dialog == null){
            dialog = new ProgressDialog(context);
            dialog.setMessage(msg);
            dialog.setCancelable(false);
        }
        if (dialog.isShowing()){
            dialog.cancel();
        }
        dialog.show();
    }
    public void cancelNetDialog(){
        if (dialog!=null && dialog.isShowing()){
            dialog.cancel();
        }
    }
    /**
     * 列表
     */
    public void dialogList(Context context, String items[], String title, CallBack callBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,3);
        builder.setTitle(title);
//         builder.setMessage(msg); //设置内容
        // 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                callBack.calBack(true,which);
            }
        });
        builder.create().show();
    }
}
