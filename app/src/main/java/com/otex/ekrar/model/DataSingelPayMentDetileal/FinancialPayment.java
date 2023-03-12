
package com.otex.ekrar.model.DataSingelPayMentDetileal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FinancialPayment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("financial_document_id")
    @Expose
    private Integer financialDocumentId;
    @SerializedName("payment")
    @Expose
    private String payment;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("document_type")
    @Expose
    private Integer documentType;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("paid")
    @Expose
    private Integer paid;
    @SerializedName("rest")
    @Expose
    private Integer rest;
    @SerializedName("paid_payments")
    @Expose
    private List<PaidPayment> paidPayments = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFinancialDocumentId() {
        return financialDocumentId;
    }

    public void setFinancialDocumentId(Integer financialDocumentId) {
        this.financialDocumentId = financialDocumentId;
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

    public Integer getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Integer documentType) {
        this.documentType = documentType;
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

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Integer getRest() {
        return rest;
    }

    public void setRest(Integer rest) {
        this.rest = rest;
    }

    public List<PaidPayment> getPaidPayments() {
        return paidPayments;
    }

    public void setPaidPayments(List<PaidPayment> paidPayments) {
        this.paidPayments = paidPayments;
    }

}
