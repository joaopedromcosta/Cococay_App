/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Repository;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        System.out.println(this.txtUsername.getText());
        System.out.println(this.txtPassword.getText());
        try {
            Repository.getSingleton().logIn("joaocosta","joaocosta");
            //Go to next screen
            System.out.println("Sucesso");
        } catch (Exception ex) {
            this.lblError.setText(ex.getMessage());
        }
        
    }
}
