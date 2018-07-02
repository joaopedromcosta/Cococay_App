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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLLoginController implements Initializable {

    @FXML private Button btnLogIn;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;
    
    @FXML private Hyperlink hlinkRegister, hlinkRecover;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.lblError.setText("");
    }    
    
    @FXML
    private void btnLoginClicked(ActionEvent event){
        System.out.println("User inserted: " + this.txtUsername.getText());
        System.out.println("Password inserted: " + this.txtPassword.getText());
        try {
            Repository.getSingleton().logIn(this.txtUsername.getText().toString().trim(),this.txtPassword.getText().toString().trim());
            System.out.println("Success!");
            this.goToHomeScreen(true, event);
        } catch (Exception ex) {
            this.lblError.setText(ex.getMessage());
        }
    }
    
    @FXML
    private void passwordFieldKeyPressed(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER))
            btnLogIn.fire();
    }
    
    @FXML
    private void btnRegisterClicked(ActionEvent event){
        System.out.println("Register new user");
    }
    @FXML
    private void btnRecoverClicked(ActionEvent event){
        System.out.println("Recover account");
    }

    private void goToHomeScreen(boolean employeeHome, ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLHomeScreenRH.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FXMLHomeScreenRHController controller = loader.getController();
        controller.setBackHandler(new IBackEvent() {
            @Override
            public void goBack(ActionEvent backEvent) {
                stage.setScene(((Node) event.getSource()).getScene());
                txtPassword.setText("");
                txtPassword.setPromptText("insert your password");
                txtUsername.setText("");
                txtUsername.setPromptText("insert your user name");
                lblError.setText("");
                Repository.getSingleton().logout();
                stage.setMinHeight(420);
                stage.setMinWidth(600);
                stage.setWidth(600);
                stage.setHeight(420);
                stage.setResizable(false);
                stage.setTitle(Repository.getAppName() + " - LogIn");
                stage.centerOnScreen();
                stage.show();
            }
        });
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(Repository.getAppName() + " - HomeScreen");
        stage.setMinHeight(850);
        stage.setMinWidth(1280);
        stage.centerOnScreen();
        stage.show();
    }
}
