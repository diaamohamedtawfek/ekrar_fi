
package com.otex.ekrar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataSendNewPay {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("payment")
    @Expose
    private int payment;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

}
