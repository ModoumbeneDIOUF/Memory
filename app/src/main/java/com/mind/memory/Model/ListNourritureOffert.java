package com.mind.memory.Model;

import java.util.ArrayList;

public class ListNourritureOffert {
    String listType,listDesc;
    private int imageId;

    public ListNourritureOffert(int imageId,String listType, String listDesc) {
        this.imageId = imageId;
        this.listType = listType;
        this.listDesc = listDesc;
    }

    public ListNourritureOffert(){}

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public String getListDesc() {
        return listDesc;
    }

    public void setListDesc(String listDesc) {
        this.listDesc = listDesc;
    }
}
