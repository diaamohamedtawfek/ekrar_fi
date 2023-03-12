package com.otex.ekrar.model.dataAllSendDucument;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataAllSendDucumentFinal {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sender_id")
    @Expose
    private Integer senderId;
    @SerializedName("receiver_id")
    @Expose
    private Integer receiverId;
    @SerializedName("currency_id")
    @Expose
    private Integer currencyId;
    @SerializedName("products")
    @Expose
    private String products;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("date")
    @Expose
    private Object date;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("pay_type")
    @Expose
    private Integer payType;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("confirmed")
    @Expose
    private Integer confirmed;
    @SerializedName("document_type")
    @Expose
    private Integer documentType;
    @SerializedName("doc_type")
    @Expose
    private Integer docType;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("receiver")
    @Expose
    private Receiver receiver;
    @SerializedName("sender")
    @Expose
    private Sender sender;
    @SerializedName("currency")
    @Expose
    private Currency currency;


    public DataAllSendDucumentFinal(Integer id, Integer senderId, Integer receiverId, Integer currencyId,
                                    String products, String description, String address, Object date, String total,
                                    Integer payType, String code, Integer confirmed, Integer documentType,
                                    Integer docType, Integer status, String createdAt, String updatedAt,
                                    Receiver receiver, Sender sender,
                                    Currency currency) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.currencyId = currencyId;
        this.products = products;
        this.description = description;
        this.address = address;
        this.date = date;
        this.total = total;
        this.payType = payType;
        this.code = code;
        this.confirmed = confirmed;
        this.documentType = documentType;
        this.docType = docType;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.receiver = receiver;
        this.currency = currency;
        this.sender = sender;
    }


    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public Integer getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Integer documentType) {
        this.documentType = documentType;
    }

    public Integer getDocType() {
        return docType;
    }

    public void setDocType(Integer docType) {
        this.docType = docType;
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

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

}
