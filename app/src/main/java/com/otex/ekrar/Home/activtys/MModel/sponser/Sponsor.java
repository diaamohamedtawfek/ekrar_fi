
package com.otex.ekrar.Home.activtys.MModel.sponser;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Sponsor {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("document_id")
    @Expose
    private Integer documentId;
    @SerializedName("user")
    @Expose
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
