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
public class EmployeesForList {
    private Long id;
    private String name;
    private String startDate;
    private String department;

    //
    public EmployeesForList() {
    }
    public EmployeesForList(Long id, String name, String startDate, String department) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.department = department;
    }
    //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    
    
    
}
