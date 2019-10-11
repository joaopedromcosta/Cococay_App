/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Repository;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLEmployeesListOptionsController implements Initializable {

    @FXML BorderPane employeesOptionsBorderPane;
    
    private FXMLEmployeesListController employeesController;
    
    @FXML Button btnDisableEmployee;
    @FXML Button btnEnableEmployee;
    @FXML Button btnNewEmployee;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLEmployeesList.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.employeesController = loader.getController();
        this.employeesOptionsBorderPane.setCenter(parent);
    }    
    
    
    // 
    public void btnDisableClicked(ActionEvent event){
        this.employeesController.btnDisableClicked();
    }
    //
    public void btnEnableClicked(ActionEvent event){
        this.employeesController.btnEnableClicked();
    }
    //
    public void btnNewEmployeeClicked(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLNewEmployee.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FXMLNewEmployeeController controller = loader.getController();
        controller.setBackHandler(new IBackEvent() {
            @Override
            public void goBack(ActionEvent backEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLEmployeesList.fxml"));
                Parent parent = null;
                try {
                    parent = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                employeesController = loader.getController();
                employeesOptionsBorderPane.setCenter(parent);
                employeesOptionsBorderPane.getBottom().setVisible(true);
                stage.setTitle(Repository.getAppName() + " - Employees");
            }
        });
        stage.setTitle(Repository.getAppName() + " - New Employee");
        this.employeesOptionsBorderPane.setCenter(parent);
        this.employeesOptionsBorderPane.getBottom().setVisible(false);
    }
    
}
