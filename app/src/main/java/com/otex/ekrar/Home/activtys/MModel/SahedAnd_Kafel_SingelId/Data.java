
package com.otex.ekrar.Home.activtys.MModel.SahedAnd_Kafel_SingelId;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Data {

    @SerializedName("witness")
    @Expose
    private List<Witness> witness = null;

    public List<Witness> getWitness() {
        return witness;
    }

    public void setWitness(List<Witness> witness) {
        this.witness = witness;
    }

}
