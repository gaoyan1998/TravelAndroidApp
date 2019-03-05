package com.ikiler.travel.Bean;

/**
 * Created by ikiler on 2019/2/24.
 * Email : ikiler@126.com
 */
public class Food {
    private int id;
    private String name;
    private String imagePath;
    private String text;

    public Food() {
    }

    public Food(int id, String name, String imagePath, String text) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.text = text;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
