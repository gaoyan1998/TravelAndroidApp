package com.ikiler.travel.Bean;

/**
 * Created by ikiler on 2019/2/26.
 * Email : ikiler@126.com
 */
public class Ticket {
    private int id;
    private int from;
    private int to;
    private int rest;
    public Ticket() {
    }

    public Ticket(int id, int from, int to, int rest) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.rest = rest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }
}
