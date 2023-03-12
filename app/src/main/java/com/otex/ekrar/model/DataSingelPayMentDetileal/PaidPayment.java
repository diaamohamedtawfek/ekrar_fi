
package com.otex.ekrar.model.DataSingelPayMentDetileal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaidPayment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("financial_id")
    @Expose
    private Integer financialId;
    @SerializedName("financial_payment_id")
    @Expose
    private Integer financialPaymentId;
    @SerializedName("payment")
    @Expose
    private String payment;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFinancialId() {
        return financialId;
    }

    public void setFinancialId(Integer financialId) {
        this.financialId = financialId;
    }

    public Integer getFinancialPaymentId() {
        return financialPaymentId;
    }

    public void setFinancialPaymentId(Integer financialPaymentId) {
        this.financialPaymentId = financialPaymentId;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
