/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Model.Funcionario;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLNewEmployeeController implements Initializable {

    @FXML Button btnGoBack;
    @FXML Button btnNext;
    
    @FXML Label lblCurrentStep;
    @FXML BorderPane container;
    
    private FXMLNewEmployeePersonalDataController personalDataController;

    private boolean goBackWithoutAsking = false;
    
    Funcionario newEmployee;
    List<String> chosenDepartments;//
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Set the center pane
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLNewEmployeePersonalData.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.personalDataController = loader.getController();
        this.container.setCenter(parent);
    }    
    
    //Go back to login menu method when btnLogOut is clicked
    public void setBackHandler(IBackEvent event){
        this.btnGoBack.setOnAction(e -> {
            try {
                if(!this.goBackWithoutAsking)
                    this.showConfirmation("Confirm Return", "Are you sure that you want to return to the previous screen? All unsaved data will be lost!");
                event.goBack(e);
            } catch (Exception ex) { }
        });
    }
    
    //
    public void btnNextClicked(ActionEvent event){
        try {
            this.personalDataController.addEmployee();
            this.showInformation("New Employed Added", "New employed added with sucess to the company!");
            this.goBackWithoutAsking = true;
            this.btnGoBack.fire();
        } catch (Exception ex) { }
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
    private void showInformation(String title, String header) throws Exception{
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.FINISH);
        alert.setTitle(title);
        alert.setHeaderText(header);
        Optional<ButtonType> option = alert.showAndWait();
    }
}
