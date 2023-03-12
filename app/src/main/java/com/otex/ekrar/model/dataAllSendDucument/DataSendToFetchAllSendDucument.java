
package com.otex.ekrar.model.dataAllSendDucument;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataSendToFetchAllSendDucument {

    @SerializedName("document_type")
    @Expose
    private Integer documentType;

    @SerializedName("status")
    @Expose
    private Integer status;

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
}
