package com.xasanboyevdiyorbek.bmi_tatu;

public class User {
    User() {


    }

    String ismi;
    String email;
    String login;
    String parol;
    String phone;


    public User(String ismi, String email, String login, String parol, String phone) {
        this.ismi = ismi;
        this.email = email;
        this.login = login;
        this.parol = parol;
        this.phone = phone;
    }

    public String getParol() {
        return parol;
    }

    public String getIsmi() {
        return ismi;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPhone() {
        return phone;
    }
}
