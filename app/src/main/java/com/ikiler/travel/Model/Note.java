package com.ikiler.travel.Model;

/**
 * Created by ikiler on 2019/2/22.
 * Email : ikiler@126.com
 */
public class Note {
    private int id;
    private int userId;
    private String content;
    private String picPath;
    private String voicePath;

    public Note(int userId, String content, String picPath, String voicePath) {
        this.userId = userId;
        this.content = content;
        this.picPath = picPath;
        this.voicePath = voicePath;
    }

    public Note() { }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }
}
