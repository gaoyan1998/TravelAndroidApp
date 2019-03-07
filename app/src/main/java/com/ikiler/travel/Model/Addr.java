package com.ikiler.travel.Model;

/**
 * Created by ikiler on 2019/2/26.
 * Email : ikiler@126.com
 */
public class Addr {
    private int id;
    private String name;
    public Addr() {
    }

    public Addr(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
