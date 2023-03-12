package com.otex.ekrar.model;

import com.google.gson.annotations.SerializedName;

public class Rowitem_cash {



    @SerializedName("date")
    private String date;
    @SerializedName("payment")
    private String payment;
    Rowitem_cash rowitem_cash;

    public Rowitem_cash(String date, String payment) {
        this.date = date;
        this.payment = payment;
        this.rowitem_cash = rowitem_cash;
    }

    public Rowitem_cash() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setProduct(Rowitem_cash product) {
        this.rowitem_cash = product;
    }
}
