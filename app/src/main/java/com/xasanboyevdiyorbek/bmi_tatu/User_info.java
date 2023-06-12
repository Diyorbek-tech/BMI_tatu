package com.xasanboyevdiyorbek.bmi_tatu;

public class User_info {

    public static String name = "0";
    public static String email = "0";
    public static String tel = "0";

    public static String getTel() {
        return tel;
    }

    public static void setTel(String tel) {
        User_info.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
