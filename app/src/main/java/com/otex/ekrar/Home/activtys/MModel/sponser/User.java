
package com.otex.ekrar.Home.activtys.MModel.sponser;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("national_id")
    @Expose
    private String nationalId;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
