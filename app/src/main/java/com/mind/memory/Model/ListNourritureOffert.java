package com.mind.memory.Model;

import java.util.ArrayList;

public class ListNourritureOffert {
    String listType,listDesc,listLieu,listProvenance;
    private int imageId;

    public ListNourritureOffert(int imageId,String listType,String listProvenance,String lieu, String listDesc) {
        this.imageId = imageId;
        this.listType = listType;
        this.listLieu = lieu;
        this.listDesc = listDesc;
        this.listProvenance = listProvenance;
    }

    public ListNourritureOffert(){}

    public String getListProvenance() {
        return listProvenance;
    }

    public void setListProvenance(String listProvenance) {
        this.listProvenance = listProvenance;
    }

    public int getImageId() {
        return imageId;
    }

    public String getListLieu() {
        return listLieu;
    }

    public void setListLieu(String listLieu) {
        this.listLieu = listLieu;
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
