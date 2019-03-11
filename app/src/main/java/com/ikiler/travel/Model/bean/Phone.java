package com.ikiler.travel.Model.bean;

import java.util.List;

/**
 * Created by ikiler on 2019/2/23.
 * Email : ikiler@126.com
 */
public class Phone {

    private int id;
    private int userId;
    private String name;
    private String number;
    private List<Phone>list;

    public Phone(int id, int userId, String name, String number) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.number = number;
    }

    public Phone() {
    }

    public Phone(List<Phone> list) {
        this.list = list;
    }

    public List<Phone> getList() {
        return list;
    }

    public void setList(List<Phone> list) {
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
