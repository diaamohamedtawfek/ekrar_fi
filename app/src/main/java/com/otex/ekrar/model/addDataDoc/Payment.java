
package com.otex.ekrar.model.addDataDoc;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payment {

    @SerializedName("payment")
    @Expose
    private Integer payment;
    @SerializedName("date")
    @Expose
    private String date;
    public Payment(){

    }
    public Payment(Integer payment, String date) {
        this.payment = payment;
        this.date = date;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
