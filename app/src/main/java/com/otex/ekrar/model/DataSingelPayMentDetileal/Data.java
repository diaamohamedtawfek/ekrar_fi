
package com.otex.ekrar.model.DataSingelPayMentDetileal;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("financial_payments")
    @Expose
    private List<FinancialPayment> financialPayments = null;

    public List<FinancialPayment> getFinancialPayments() {
        return financialPayments;
    }

    public void setFinancialPayments(List<FinancialPayment> financialPayments) {
        this.financialPayments = financialPayments;
    }

}
