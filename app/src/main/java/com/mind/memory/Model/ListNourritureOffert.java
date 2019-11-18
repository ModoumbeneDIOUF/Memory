package com.mind.memory.Model;


public class ListNourritureOffert{
    private int id;
    private String des;
    private String type;
    private String pro;
    private String adr;
    private String qu;
    private String num;
    private String img;
    private String jj;

    public ListNourritureOffert(String des, String pro, String adr, String num,String img,String jj) {
        this.des = des;
        this.pro = pro;
        this.adr = adr;
        this.num = num;
        this.img = img;
        this.jj = jj;
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



}
