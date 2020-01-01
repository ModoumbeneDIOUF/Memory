package com.mind.memory.Model;

public class ListDonRecupe {
    private String des;
    private String adresse;
    private String randomKey;
    public ListDonRecupe(String des,String adresse,String randomKey){
        this.des = des;
        this.adresse =adresse;
        this.randomKey = randomKey;
    }

    public String getDes() {
        return des;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getRandomKey() {
        return randomKey;
    }
}
