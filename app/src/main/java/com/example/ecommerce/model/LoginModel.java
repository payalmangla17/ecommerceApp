package com.example.ecommerce.model;

import android.util.Patterns;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
//    public LoginModel(String email, String password) {
//        mEmail=email;
//        mPassword = password;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
//    public boolean isEmailValid() {
//        return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
//    }
//
//
//    public boolean isPasswordLengthGreaterThan5() {
//        return getPassword().length() > 5;
//    }

}
