package com.example.myapplication.mainC;

public class ToDoListItem {
    private int sId;
    private int imageId;
    private String title;
    private String remark;

    public ToDoListItem(int sId,int imageId, String title, String remark) {
        this.sId=sId;
        this.imageId = imageId;
        this.title = title;
        this.remark = remark;
    }

    public int getsId() {
        return sId;
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
