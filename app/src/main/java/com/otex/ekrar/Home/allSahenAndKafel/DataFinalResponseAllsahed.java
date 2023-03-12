package com.otex.ekrar.Home.allSahenAndKafel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.otex.ekrar.model.dataAllSendDucument.Receiver;
import com.otex.ekrar.model.dataAllSendDucument.Sender;

public class DataFinalResponseAllsahed {

    int id,sender_id,receiver_id,currency_id,confirmed,document_type,doc_type,status,pay_type;

    String products,description,address,total,code,created_at;
    Object date;


    @SerializedName("receiver")
    @Expose
    private Receiver receiver;
    @SerializedName("sender")
    @Expose
    private Sender sender;

    public DataFinalResponseAllsahed(String created_at, int id, int sender_id, int receiver_id, int currency_id,
                                     int confirmed, int document_type, int doc_type, int status, int pay_type, String products,
                                     String description, String address, Object date, String total, String code,Receiver receiver,Sender sender) {
        this.id = id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.currency_id = currency_id;
        this.confirmed = confirmed;
        this.document_type = document_type;
        this.doc_type = doc_type;
        this.status = status;
        this.pay_type = pay_type;
        this.products = products;
        this.description = description;
        this.address = address;
        this.date = date;
        this.total = total;
        this.code = code;
        this.created_at = created_at;
        this.receiver = receiver;
        this.sender = sender;
    }


    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public int getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(int currency_id) {
        this.currency_id = currency_id;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getDocument_type() {
        return document_type;
    }

    public void setDocument_type(int document_type) {
        this.document_type = document_type;
    }

    public int getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(int doc_type) {
        this.doc_type = doc_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
