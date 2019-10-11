/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Repository;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLListDataController implements Initializable {

    @FXML BorderPane container;
    @FXML CheckBox checkBoxRequests;
    @FXML CheckBox checkBoxEmployees;
    @FXML CheckBox checkBoxDepartments;
    @FXML CheckBox checkBoxAllHolidays;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void checkBoxRequestsSelected(ActionEvent event){
        if(this.checkBoxRequests.isSelected()){
            this.checkBoxDepartments.setSelected(false);
            this.checkBoxEmployees.setSelected(false);
            this.checkBoxAllHolidays.setSelected(false);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(Repository.getAppName() + " - List Requests");
            this.ChangePane(getClass().getResource("FXMLValidateRequests.fxml"));
        }else{
            this.checkBoxRequests.setSelected(true);
        }
    }
    //
    public void checkBoxEmployeesSelected(ActionEvent event){
        if(this.checkBoxEmployees.isSelected()){
            this.checkBoxDepartments.setSelected(false);
            this.checkBoxRequests.setSelected(false);
            this.checkBoxAllHolidays.setSelected(false);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(Repository.getAppName() + " - List Employees");
            this.ChangePane(getClass().getResource("FXMLEmployeesList.fxml"));
        }else{
            this.checkBoxEmployees.setSelected(true);
        }
    }
    //
    public void checkBoxDepartmentsSelected(ActionEvent event){
        if(this.checkBoxDepartments.isSelected()){
            this.checkBoxEmployees.setSelected(false);
            this.checkBoxRequests.setSelected(false);
            this.checkBoxAllHolidays.setSelected(false);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(Repository.getAppName() + " - List Departments");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDepartments.fxml"));
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FXMLDepartmentsController departmentsController = loader.getController();
            this.container.setVisible(true);
            this.container.setTop(parent);
            this.container.setCenter(null);
            departmentsController.setBottom(false);
        }else{
            this.checkBoxDepartments.setSelected(true);
        }
    }
    //
    public void checkBoxAllHolidaysSelected(ActionEvent event){
        if(this.checkBoxAllHolidays.isSelected()){
            this.checkBoxDepartments.setSelected(false);
            this.checkBoxRequests.setSelected(false);
            this.checkBoxEmployees.setSelected(false);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(Repository.getAppName() + " - List All Holidays");
            this.ChangePane(getClass().getResource("FXMLHolidaysList.fxml"));
        }else{
            this.checkBoxAllHolidays.setSelected(true);
        }
    }
    private void ChangePane(URL u){
        this.container.setTop(null);
        FXMLLoader loader = new FXMLLoader(u);
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.container.setVisible(true);
        this.container.setCenter(parent);
    }
}
