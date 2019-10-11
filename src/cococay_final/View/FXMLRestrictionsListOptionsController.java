/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Model.Restrictions;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import cococay_final.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import javafx.event.Event;



/**
 * FXML Controller class
 *
 * @author Pedro
 */

public class FXMLRestrictionsListOptionsController implements Initializable {
    @FXML BorderPane restrictionsOptionsContainer;
    @FXML DatePicker datePickerBeginDate;
    @FXML DatePicker datePickerEndDate;
    @FXML TextArea textAreaReason;
    @FXML Label lblWarming;
    @FXML Button btnCancel;
    @FXML Button btnAdd_delete;
    
     FXMLLoader loader = new FXMLLoader();
     
    
    private FXMLRestrictionsListController restrictionListController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        loader = new FXMLLoader(getClass().getResource("FXMLRestrictionsList.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
            System.out.println("--- "+parent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.restrictionListController = loader.getController();
        this.restrictionsOptionsContainer.setTop(parent);
        //Define calendar pickers values
        this.datePickerBeginDate.setValue(LocalDate.now());
        this.datePickerEndDate.setValue(LocalDate.now().plusDays(1));
        //Change pattern of calendar
        String pattern = "dd/MM/yyyy";
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
        @Override
        public String toString(LocalDate date) {
          if (date != null) {
            return dateFormatter.format(date);
          } else {
            return "";
          }
        }
        @Override
        public LocalDate fromString(String string) {
          if (string != null && !string.isEmpty()) {
            return LocalDate.parse(string, dateFormatter);
          } else {
            return null;
          }
        }
      };
        
        this.lblWarming.setText("");
     
                
    }    

       
    // btnAdd CLicked
    @FXML
    private void btnAddCliked(ActionEvent event){
        List<Restrictions> listRestrictionstemp ;
        Restrictions restriction = new Restrictions();
        String beginDateAux,endDateAux;
        beginDateAux=this.datePickerBeginDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        endDateAux=this.datePickerEndDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        if(this.textAreaReason.getText().isEmpty()){
            this.lblWarming.setText("Necessary insert a reason");
        }else{
            if(Repository.getSingleton(). checkDateRestriction(beginDateAux, endDateAux)){
                try {
                  
                    this.showConfirmation("Confirm New Restriction","Are you sure you want to add a new restriction?");
                    listRestrictionstemp=this.restrictionListController.getRestrictionsList();
                    Repository.getSingleton().addRestriction(this.datePickerBeginDate, this.datePickerEndDate, this.textAreaReason.getText());
                    Repository.getSingleton().update("commit");  
                    //select max id for new restriction 
                    Repository.getSingleton().select("select MAX(id_restricao) from RESTRICAO_FERIAS");
                    
                    restriction.setBeginDate(this.datePickerBeginDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    restriction.setEndDate(this.datePickerEndDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    restriction.setReason(this.textAreaReason.getText());
                    restriction.setId( Repository.getSingleton().getMAxIdRestriction());
                    listRestrictionstemp.add(restriction);
                    this.restrictionListController.getRestrictionsObservableList().add(restriction);
                    this.restrictionListController.setRestrictionsList(listRestrictionstemp);
                      this.resetImputs(event);
                } catch (Exception ex) {
                   this.lblWarming.setText(ex.getMessage());
                }
            }else{
                this.lblWarming.setText("Error: There is already a restriction in this period");
            }
        }
      
    }
    
    // clear inputs
     @FXML
    private void resetImputs(ActionEvent event){
          this.datePickerBeginDate.setValue(LocalDate.now());
        this.datePickerEndDate.setValue(LocalDate.now().plusDays(1));
        this.textAreaReason.setText("");
    }
    
    // btnDelete Clicked
    @FXML private void btnDelete(ActionEvent event){
        List<Restrictions> listRestrictionstemp ;
        Restrictions restrictionTemp;
        long aux=0;
        int selectedIndex= this.restrictionListController.getTableRestriction().getSelectionModel().getSelectedIndex();
      //  System.out.println("selectedindex "+ selectedIndex);
        if(selectedIndex == -1){
            this.lblWarming.setText("It's necessary to select some restriction");
            return;
        }else{
            this.lblWarming.setText("");
        }
        try{
            System.out.println("tamanho antes " + this.restrictionListController.getRestrictionsObservableList().size());
            System.out.println("tamanho antes lista " + this.restrictionListController.getRestrictionsList().size());
            this.showConfirmation("Delete Restriction", "Are you sure you want to delete the restriction");
            restrictionTemp= this.restrictionListController.getTableRestriction().getSelectionModel().getSelectedItem();
            listRestrictionstemp = this.restrictionListController.getRestrictionsList();
            aux=restrictionTemp.getId();
            System.out.println("este é o id "+ aux);
            System.out.println("nome "+restrictionTemp.getReason()+ "id "+ restrictionTemp.getId());
            String sql_statement = "delete from restricao_ferias where id_restricao='" + aux + "' ";
            Repository.getSingleton().update(sql_statement);
            Repository.getSingleton().update("commit");
            listRestrictionstemp.remove(restrictionTemp);
            this.restrictionListController.removeRestrictionList(restrictionTemp);
            this.restrictionListController.getRestrictionsObservableList().remove(selectedIndex);
            this.restrictionListController.setRestrictionsList(listRestrictionstemp);
            System.out.println("tamanho depois "+this.restrictionListController.getRestrictionsObservableList().size());
              System.out.println("tamanho depois lista " + this.restrictionListController.getRestrictionsList().size());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    //Check if date selected is prior to the current date
    @FXML
    public void beginDatePickerChanged(ActionEvent event){
        try{
            this.checkTemporaryDates(this.datePickerBeginDate.getValue());
            
        }catch(Exception e){
            this.lblWarming.setText(e.getMessage());
        }
    }
    //Check if date selected is prior to the current date
    @FXML
    public void endDatePickerChanged(ActionEvent event){
        try{
            this.checkTemporaryDates(this.datePickerEndDate.getValue());
        }catch(Exception e){
            this.lblWarming.setText(e.getMessage());
        }
    }
    
    private void checkTemporaryDates(LocalDate date) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.lblWarming.setText("");
        Date dt = Date.valueOf(date);
        //Check if date argument is the same as the  begin date picker
        if(date.isEqual(this.datePickerBeginDate.getValue())){
            if(date.isAfter(this.datePickerEndDate.getValue())){//If it is checks if the begin date is later than the end date
                this.datePickerEndDate.setValue(this.datePickerBeginDate.getValue().plusDays(1));//If it is the end date must be changed to later than the begin date
            }
        }else{//The oposite must be checked
            if(date.isBefore(this.datePickerBeginDate.getValue())){
                this.datePickerBeginDate.setValue(this.datePickerEndDate.getValue().minusDays(1));
            }
        }
        //
        if(this.datePickerBeginDate.getValue().isBefore(LocalDate.now())){
            throw new Exception("Warning: Selected days must be later than the current day!");
        }
    }
    
    private void showConfirmation(String title, String header) throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        alert.setHeaderText(header);
        Optional<ButtonType> option = alert.showAndWait();
        if((option.get() == ButtonType.NO)){
            throw new Exception("");
        }
    }
}

