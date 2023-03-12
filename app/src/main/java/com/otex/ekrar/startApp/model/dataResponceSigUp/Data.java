
package com.otex.ekrar.startApp.model.dataResponceSigUp;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("national_id")
    @Expose
    private List<String> nationalId = null;
    @SerializedName("device_id")
    @Expose
    private List<String> deviceId = null;
    @SerializedName("mobile")
    @Expose
    private List<String> mobile = null;
    @SerializedName("email")
    @Expose
    private List<String> email = null;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user")
    @Expose
    private User user;

    public List<String> getNationalId() {
        return nationalId;
    }

    public void setNationalId(List<String> nationalId) {
        this.nationalId = nationalId;
    }

    public List<String> getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(List<String> deviceId) {
        this.deviceId = deviceId;
    }

    public List<String> getMobile() {
        return mobile;
    }

    public void setMobile(List<String> mobile) {
        this.mobile = mobile;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
