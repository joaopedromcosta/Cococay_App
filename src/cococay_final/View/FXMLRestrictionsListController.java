/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import cococay_final.Model.Restrictions;
import cococay_final.Repository;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Pedro
 */
public class FXMLRestrictionsListController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private BorderPane restrictionsContainer;
    @FXML private TableView<Restrictions> tableRestriction;
    @FXML private TableColumn<Restrictions, Date> columnRestrictionBeginDate;
    @FXML private TableColumn<Restrictions, Date> columnRestrictionEndDate;
    @FXML private TableColumn<Restrictions, String> columnRestrictionReason; 
    
     private ObservableList<Restrictions> restrictionsObservableList;
     private Restrictions currentSelectedRestrictions = null;
     
     private Restrictions restriction=new Restrictions();
     private Restrictions aux=new Restrictions();
     private  List<Restrictions> restrictionsList;

  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.initializeTable();
        
    }    
    
      private void initializeTable() {
        this.columnRestrictionBeginDate.setCellValueFactory(new PropertyValueFactory<>("BeginDate"));
        this.columnRestrictionEndDate.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
        this.columnRestrictionReason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
        //
         restrictionsList = Repository.getSingleton().getRestrictionsList();
          System.out.println("PEDRO "+ restrictionsList.size());
        this.restrictionsObservableList =  FXCollections.observableArrayList(restrictionsList);
        this.tableRestriction.setItems(restrictionsObservableList);
        
        this.tableRestriction.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.currentSelectedRestrictions = newValue;
         
        //this.tableRestriction.getSelectionModel().getSelectedItem();
            aux=newValue;
        });
         this.setRestriction(aux);
    }
     
     public Restrictions getRestriction() {
        return restriction;
    }
     

    public void setRestriction(Restrictions restriction) {
        this.restriction = restriction;
    }

    public ObservableList<Restrictions> getRestrictionsObservableList() {
        return restrictionsObservableList;
    }

    public void setRestrictionsObservableList(ObservableList<Restrictions> restrictionsObservableList) {
        this.restrictionsObservableList = restrictionsObservableList;
    }

    public List<Restrictions> getRestrictionsList() {
        return restrictionsList;
    }

    public void setRestrictionsList(List<Restrictions> restrictionsList) {
        this.restrictionsList = restrictionsList;
    }

    public TableView<Restrictions> getTableRestriction() {
        return tableRestriction;
    }

    public void setTableRestriction(TableView<Restrictions> tableRestriction) {
        this.tableRestriction = tableRestriction;
    }
    
    public void removeRestrictionList(Restrictions r){
         for(Restrictions rest : this.restrictionsList){
            if(rest.equals(r)){  
                this.restrictionsList.remove(rest);          
            }
        }
    }
    
    public boolean checkDateRestriction(String beginDate, String endDate){
        boolean check=true, check2=true, check3=true;
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date auxBegin=null, auxEnd=null,newBegin=null,newEnd= null;
        try{
            newBegin=format.parse(beginDate);
            newEnd=format.parse(endDate);
        }catch(ParseException ex){
            ex.printStackTrace();
        }
        System.out.println("d1 "+ beginDate + " d2 "+ endDate);
     
        for(Restrictions r : this.restrictionsObservableList){
            
            try {
                auxBegin=format.parse(r.getBeginDate());
                auxEnd=format.parse(r.getEndDate());
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
             System.out.println("e1 "+r.getBeginDate() + " e2 "+ r.getEndDate());
            if(newBegin.equals(auxBegin) || newBegin.after(auxBegin)){
                if(newBegin.equals(auxEnd) || newBegin.before(auxEnd)){
                    check=false;
                    break;
                    
                }
            }
            if(newEnd.equals(auxBegin) || newEnd.after(auxBegin)){
                if(newEnd.equals(auxEnd) || newEnd.before(auxEnd)){
                    check=false;
                }
            }
       }
    return check;
}
}
