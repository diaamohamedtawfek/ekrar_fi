
package com.otex.ekrar.model.addDataDoc;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDataDocFromBootomSheet {

    @SerializedName("document_type")
    @Expose
    private Integer documentType;
    @SerializedName("national_id")
    @Expose
    private String nationalId;
    @SerializedName("currency_id")
    @Expose
    private Integer currencyId;
    @SerializedName("doc_type")
    @Expose
    private Integer docType;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("products")
    @Expose
    private String products;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("pay_type")
    @Expose
    private Integer payType;
    @SerializedName("payments")
    @Expose
    private List<Payment> payments = null;

    public Integer getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Integer documentType) {
        this.documentType = documentType;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }
    public void setDocType(Integer docType) {
        this.docType = docType;
    }

    public int getDocType() {
        return docType;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

}
