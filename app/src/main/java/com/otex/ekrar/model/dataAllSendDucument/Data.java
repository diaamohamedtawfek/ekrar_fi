
package com.otex.ekrar.model.dataAllSendDucument;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("documents")
    @Expose
    private List<DocumentsSent> documentsSent = null;

    public List<DocumentsSent> getDocumentsSent() {
        return documentsSent;
    }

    public void setDocumentsSent(List<DocumentsSent> documentsSent) {
        this.documentsSent = documentsSent;
    }

}
