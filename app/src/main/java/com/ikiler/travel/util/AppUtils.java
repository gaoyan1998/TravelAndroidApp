package com.ikiler.travel.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AppUtils {

    //隐藏输入法
    public static void hideInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public static String getDate(Date date) {
        return df.format(date);
    }

    /**
     * 手机号隐藏中间4位
     *
     * @param mobile
     * @return
     */
    public static String formatPhoneNumber(String mobile) {
        StringBuilder maskNumber = new StringBuilder(11);
        maskNumber.append(mobile.substring(0, 3));
        maskNumber.append("****");
        maskNumber.append(mobile.substring(7, mobile.length()));
        return maskNumber.toString();
    }
}
