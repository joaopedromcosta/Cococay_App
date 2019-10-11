/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.Model;

import java.util.Date;

/**
 *
 * @author Pedro
 */
public class Restrictions {
    private long id;
    private String beginDate;
    private String endDate;
    private String reason;
    private long idFunc;

    public Restrictions() {
    }

    public Restrictions(long id, String beginDate, String endDate, String reason) {
        this.id = id;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.reason = reason;
    }

    
    public Restrictions(long id, String beginDate, String endDate, String reason, long idFunc) {
        this.id = id;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.reason = reason;
        this.idFunc=idFunc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getIdFunc() {
        return idFunc;
    }

    public void setIdFunc(long idFunc) {
        this.idFunc = idFunc;
    }

    
}
