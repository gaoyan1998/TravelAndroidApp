package com.ikiler.travel.Base;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import com.ikiler.travel.util.DialogUtil;
import com.tencent.mmkv.MMKV;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragement extends Fragment {

    private MMKV mmkv;
    private DialogUtil dialogUtil = new DialogUtil();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mmkv = MMKV.defaultMMKV();
    }

    /**
     * Toast提示
     *
     * @param text
     */
    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public DialogUtil getDialogUtil() {
        return dialogUtil;
    }

    public MMKV getMmkv() {
        return mmkv;
    }
}
