
package com.otex.ekrar.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataSendConfirmReceved {

    @SerializedName("document_id")
    @Expose
    private Integer documentId;
    @SerializedName("status")
    @Expose
    private Integer status;

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
