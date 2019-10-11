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
public class AbsencesForList {
    private Long id;
    private String date;
    private String name;
    private String justified;
    private String justification;

    public AbsencesForList(String date, String name, String justified, String justification) {
        this.date = date;
        this.name = name;
        this.justified = justified;
        this.justification = justification;
    }

    public AbsencesForList() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJustified() {
        return justified;
    }

    public void setJustified(String justified) {
        this.justified = justified;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
}
