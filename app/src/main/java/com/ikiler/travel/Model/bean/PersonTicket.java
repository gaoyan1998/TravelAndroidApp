package com.ikiler.travel.Model.bean;

import com.ikiler.travel.util.LiveBus;

import java.util.List;

/**
 * Created by ikiler on 2019/2/27.
 * Email : ikiler@126.com
 */

public class PersonTicket {
    private int id;
    private int userId;
    private int ticketId;
    private String timeFrom;
    private String timeTo;
    private String fromName, toName;
    private List<PersonTicket> list;

    public PersonTicket() {
    }

    public List<PersonTicket> getList() {
        return list;
    }

    public void setList(List<PersonTicket> list) {
        this.list = list;
    }

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

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }
}
