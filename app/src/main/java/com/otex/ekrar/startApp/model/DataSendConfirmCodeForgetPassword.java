
package com.otex.ekrar.startApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataSendConfirmCodeForgetPassword {

    @SerializedName("reset_code")
    @Expose
    private String reset_code;


    public String getReset_code() {
        return reset_code;
    }

    public void setReset_code(String reset_code) {
        this.reset_code = reset_code;
    }
}
