
package com.otex.ekrar.startApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataChangePassword {

    @SerializedName("password_confirmation")
    @Expose
    private String password_confirmation;

    @SerializedName("password")
    @Expose
    private String password;

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
