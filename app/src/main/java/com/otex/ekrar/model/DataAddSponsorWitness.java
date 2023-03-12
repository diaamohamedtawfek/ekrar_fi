
package com.otex.ekrar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataAddSponsorWitness {

    @SerializedName("national_id")
    @Expose
    private Integer nationalId;
    @SerializedName("document_id")
    @Expose
    private Integer documentId;

    public int getNationalId() {
        return nationalId;
    }

    public void setNationalId(int nationalId) {
        this.nationalId = nationalId;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

}
