package com.example.myapplication.mainC;

public class ToDoListItem {
    private int imageId;
    private String title;
    private String remark;

    public ToDoListItem(int imageId, String title, String remark) {
        this.imageId = imageId;
        this.title = title;
        this.remark = remark;
    }

    public int getImageId() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }

    public String getRemark() {
        return remark;
    }
}
