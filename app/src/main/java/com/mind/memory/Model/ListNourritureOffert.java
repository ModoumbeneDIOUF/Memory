package com.mind.memory.Model;

public class ListNourritureOffert {
    String listType,listDesc;

    public ListNourritureOffert(String listType, String listDesc) {
        this.listType = listType;
        this.listDesc = listDesc;
    }

    public ListNourritureOffert(){}

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
