/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Model.Login;
import cococay_final.Repository;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLRecoverPasswordController implements Initializable {

    @FXML private Button btnBack;
    @FXML private Button btnCheckFinish;
    @FXML Label securityQuestion;
    @FXML Label lblWarnings;
    @FXML TextField txtAnswer;
    @FXML PasswordField txtNewPassword;
    @FXML PasswordField txtConfirmNewPassword;
    
    private Login log;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.txtNewPassword.setVisible(false);
        this.txtConfirmNewPassword.setVisible(false);
    }    

    public void setLog(Login log) {
        this.log = log;
        this.securityQuestion.setText("Security Question: " + log.getPerguntaSeguranca1());
    }
    
    public void btnCheckClicked(ActionEvent event){
        if(this.btnCheckFinish.getText().equals("Check Answer")){
            if(this.txtAnswer.getText().isEmpty()){
                this.lblWarnings.setText("Insert your answer!");
                return;
            }
            if(this.txtAnswer.getText().equals(log.getRespostaSeguranca1())){
                this.txtNewPassword.setVisible(true);
                this.txtConfirmNewPassword.setVisible(true);
                this.btnCheckFinish.setText("Change Password");
            }else{
                this.showWarning("Recover Failed", "Sorry but that's not the right answer!");
                this.btnBack.fire();
            }
        }else{
            if(!this.txtNewPassword.getText().equals(this.txtConfirmNewPassword.getText())){
                this.lblWarnings.setText("The two passwords inserted are different!");
            }else{
                try {
                    this.showConfirmation("Confirm Password Change", "Are you sure that you want to change your password?");
                    Repository.getSingleton().changeUserPassword(this.log.getIdFuncionario().getIdFuncionario(), this.txtNewPassword.getText());
                    this.showInformation("Success", "Password was changed with success");
                    this.btnBack.fire();
                } catch (Exception ex) { 
                    this.showInformation("Failed", "Password change failed!");
                }
            }
        }
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
    private void showWarning(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.CLOSE);
        alert.setTitle(title);
        alert.setHeaderText(header);
        Optional<ButtonType> option = alert.showAndWait();
    }
    //Show alert box
    private void showInformation(String title, String header){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.FINISH);
        alert.setTitle(title);
        alert.setHeaderText(header);
        Optional<ButtonType> option = alert.showAndWait(); 
    }
    public void setBackHandler(IBackEvent event){
        this.btnBack.setOnAction(e -> event.goBack(e));
    }
}
