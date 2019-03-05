package com.ikiler.travel.Bean;

/**
 * Created by ikiler on 2019/2/27.
 * Email : ikiler@126.com
 */
public class PersonTicket {
    private int id;
    private int userId;
    private int ticketId;

    public PersonTicket() {
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
