/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Model.Ferias;
import cococay_final.Model.Requests;
import cococay_final.Repository;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class FXMLValidateRequestsController implements Initializable {
    
    @FXML TableView<Requests> requestsTable;
    @FXML TableColumn<Requests, String> nameColumn;
    @FXML TableColumn<Requests, String> departmentColumn;
    @FXML TableColumn<Requests, String> begin_DateColumn;
    @FXML TableColumn<Requests, String> end_DateColumn;
    //
    @FXML Button btnAccept;
    @FXML Button btnRefuse;
    @FXML Button btnCancel;
    
    
    private ObservableList<Requests> requestsObservableList;
    
    
    private Requests currentSelectedRequest = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.initializeTable();
    }    
    //
    private void initializeTable() {
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        this.begin_DateColumn.setCellValueFactory(new PropertyValueFactory<>("beginDate"));
        this.end_DateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        //
        List<Requests> requestsList = Repository.getSingleton().getRequestsList();
        this.requestsObservableList =  FXCollections.observableArrayList(requestsList);
        this.requestsTable.setItems(requestsObservableList);
        
        this.requestsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.currentSelectedRequest = newValue;
        });
    }
    //
    @FXML
    public void btnAcceptClicked(ActionEvent event){
        if(this.currentSelectedRequest != null)
            try {
                this.showConfirmation("Confirm Validation","Are you sure you want to Accept the request?");
                Repository.getSingleton().setValidationRequest(this.currentSelectedRequest.getId(), true);
                this.requestsObservableList.remove(this.requestsObservableList.indexOf(this.currentSelectedRequest));
            } catch (Exception ex) {}
    }
    //
    @FXML
    public void btnRefuseClicked(ActionEvent event){
        if(this.currentSelectedRequest != null)
            try {
                this.showConfirmation("Confirm Validation","Are you sure you want to Refuse the request?");
                Repository.getSingleton().setValidationRequest(this.currentSelectedRequest.getId(), false);
                this.requestsObservableList.remove(this.requestsObservableList.indexOf(this.currentSelectedRequest));
            } catch (Exception ex) {}
    }
    //
    @FXML
    public void btnCancelClicked(ActionEvent event){
        this.requestsTable.getSelectionModel().clearSelection();
        this.currentSelectedRequest = null;
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
}
