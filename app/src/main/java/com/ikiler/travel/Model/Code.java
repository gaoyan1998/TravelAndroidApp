package com.ikiler.travel.Model;

/**
 * Created by ikiler on 2019/2/22.
 * Email : ikiler@126.com
 * 请求状态 code：状态码
 * msg：状态信息
 * data：数据
 */
public class Code {

    /**
     * code : 200
     * msg :
     * data : f2d0f545548949f28cbe8101bfc9ee1a
     */

    private int code;
    private String msg;
    private String data;

    public Code(int code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Code() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
