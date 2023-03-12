package com.otex.ekrar.startApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendForgetPassord {


    @SerializedName("national_id")
    @Expose
    private String national_id;

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }
}
