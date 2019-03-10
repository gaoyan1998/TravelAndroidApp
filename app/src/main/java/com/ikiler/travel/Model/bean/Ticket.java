package com.ikiler.travel.Model.bean;

import java.util.List;

/**
 * Created by ikiler on 2019/2/26.
 * Email : ikiler@126.com
 */
public class Ticket {
    private int id;
    private int from;
    private int to;
    private int rest;
    private int price;
    private String fromName,toName;
    private List<Ticket> lists;

    public Ticket() {
    }

    public Ticket(int id, int from, int to, int rest) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.rest = rest;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public Ticket(List<Ticket> lists) {
        this.lists = lists;
    }

    public List<Ticket> getLists() {
        return lists;
    }

    public void setLists(List<Ticket> lists) {
        this.lists = lists;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }
    private String timeFrom;
    private String timeTo;

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
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
