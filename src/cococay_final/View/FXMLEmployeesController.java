/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Model.EmployeesForList;
import cococay_final.Repository;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLEmployeesController implements Initializable {

    @FXML TableView<EmployeesForList> employeesTable;
    //
    @FXML TableColumn<EmployeesForList, String> idCol;
    @FXML TableColumn<EmployeesForList, String> nameCol;
    @FXML TableColumn<EmployeesForList, String> startDateCol;
    @FXML TableColumn<EmployeesForList, String> departmentCol;
    //
    @FXML Button btnDisableEmployee;
    @FXML Button btnEnableEmployee;
    @FXML Button btnNewRequest;
    
    private ObservableList<EmployeesForList> requestsObservableList;
    
    
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
        this.startDateCol.setCellValueFactory(new PropertyValueFactory<>("starDate"));
        this.departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        //
        List<EmployeesForList> employeesList = Repository.getSingleton().getEmployeesList();
        this.requestsObservableList =  FXCollections.observableArrayList(employeesList);
        this.employeesTable.setItems(requestsObservableList);
        
        this.employeesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.currentSelectedEmployee = newValue;
        });
    }
    
}
