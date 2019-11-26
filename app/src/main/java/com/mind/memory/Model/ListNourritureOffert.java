package com.mind.memory.Model;


public class ListNourritureOffert{
    private String id;
    private String des;
    private String type;
    private String pro;
    private String adr;
    private String qu;
    private String num;
    private String img;
    private String jj;
    private String donRandomKey;

    public ListNourritureOffert(String donRandomKey,String des, String pro, String adr, String num,String img,String jj) {
        this.des = des;
        this.pro = pro;
        this.adr = adr;
        this.num = num;
        this.img = img;
        this.jj = jj;

        this.donRandomKey = donRandomKey;
    }

    public String getDes() {
        return des;
    }

    public String getPro() {
        return pro;
    }

    public String getAdr() {
        return adr;
    }

    public String getNum() {
        return num;
    }

    public String getImg() {
        return img;
    }

    public String getJj() {
        return jj;
    }

    public String getId() {
        return id;
    }

    public String getRanomKey() {
        return donRandomKey;
    }
}
