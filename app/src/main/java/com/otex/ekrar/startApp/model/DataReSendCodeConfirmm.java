
package com.otex.ekrar.startApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataReSendCodeConfirmm {

    @SerializedName("national_id")
    @Expose
    private String national_id;

    @SerializedName("status")
    @Expose
    private int status;


    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
