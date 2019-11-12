package com.mind.memory.Model;

public class LoginResponse {
    private  boolean error;
    private String message;
    private  String profil;

    public LoginResponse(boolean error, String message, String profil) {
        this.error = error;
        this.message = message;
        this.profil = profil;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getProfil() {
        return profil;
    }
}
