package com.mind.memory.Model;

public class UpdatePassword {
    private String upNumber,upPasswod;

    public UpdatePassword() {
    }

    public UpdatePassword(String upNumber, String upPasswod) {
        this.upNumber = upNumber;
        this.upPasswod = upPasswod;
    }

    public String getUpNumber() {
        return upNumber;
    }

    public void setUpNumber(String upNumber) {
        this.upNumber = upNumber;
    }

    public String getUpPasswod() {
        return upPasswod;
    }

    public void setUpPasswod(String upPasswod) {
        this.upPasswod = upPasswod;
    }
}
