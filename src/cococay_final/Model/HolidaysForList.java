/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.Model;

/**
 *
 * @author joaocosta-ipvc
 */
public class HolidaysForList {
    private Long id;
    private String beginDate;
    private String endDate;
    private String name;
    private String departments;

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public HolidaysForList() {
    }

    public HolidaysForList(String beginDate, String endDate, String name) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
}
