/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Model.EmployeesForList;
import cococay_final.Repository;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLEmployeesListController implements Initializable {

    @FXML TableView<EmployeesForList> employeesTable;
    //
    @FXML TableColumn<EmployeesForList, String> idCol;
    @FXML TableColumn<EmployeesForList, String> nameCol;
    @FXML TableColumn<EmployeesForList, String> startDateCol;
    @FXML TableColumn<EmployeesForList, String> departmentCol;
    //
    @FXML Button btnDisableEmployee;
    @FXML Button btnEnableEmployee;
    @FXML Button btnNewEmployee;
    
    private ObservableList<EmployeesForList> employeesObservableList;
    private EmployeesForList currentSelectedEmployee = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.initializeTable();
    }    

    private void initializeTable() {
        this.idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        this.departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        //
        List<EmployeesForList> employeesList = Repository.getSingleton().getEmployeesList();
        this.employeesObservableList =  FXCollections.observableArrayList(employeesList);
        this.employeesTable.setItems(employeesObservableList);
        
        this.employeesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.currentSelectedEmployee = newValue;
        });
    }
    
    // 
    public void btnDisableClicked(){
        //Check if disabled employee is the current logged employee
        /*
        
        */
        if(this.currentSelectedEmployee.getStartDate().equals("Disabled Employee")){
            this.showAlert("Error", "The selected employee is already Disabled");
            return;
        }
        if(this.currentSelectedEmployee != null)
            try {
                this.showConfirmation("Confirm Disable","Are you sure you want to Disable the selected employee?");
                Repository.getSingleton().disableEmployee(this.currentSelectedEmployee);
                this.currentSelectedEmployee.setStartDate("Disabled Employee");
                this.employeesObservableList.set(this.employeesObservableList.indexOf(this.currentSelectedEmployee), this.currentSelectedEmployee);
        } catch (Exception ex) {}
    }
    //
    public void btnEnableClicked(){
        if(!this.currentSelectedEmployee.getStartDate().equals("Disabled Employee") && !this.currentSelectedEmployee.getStartDate().isEmpty()){
            this.showAlert("Error", "The selected employee is already Enabled");
            return;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.now();
        if(this.currentSelectedEmployee != null)
            try {
                this.showConfirmation("Confirm Disable","Are you sure you want to Enable the selected employee?");
                Repository.getSingleton().enableEmployee(this.currentSelectedEmployee);
                this.currentSelectedEmployee.setStartDate(dtf.format(date));
                this.employeesObservableList.set(this.employeesObservableList.indexOf(this.currentSelectedEmployee), this.currentSelectedEmployee);
        } catch (Exception ex) {}
    }
    
    //Show alert box
    private void showConfirmation(String title, String header) throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        alert.setHeaderText(header);
        Optional<ButtonType> option = alert.showAndWait();
        if((option.get() == ButtonType.NO)){
            throw new Exception("");
        }
    }
    //Show alert box
    private void showAlert(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.CLOSE);
        alert.setTitle(title);
        alert.setHeaderText(header);
        Optional<ButtonType> option = alert.showAndWait();
    }
    
}
