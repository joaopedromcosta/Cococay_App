/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Model.Equipa;
import cococay_final.Repository;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLDepartmentsController implements Initializable {

    //Borderpane
    @FXML BorderPane container;
    //Table view
    @FXML TableView<Equipa> tableDepartments;
    @FXML TableColumn<Equipa, String> columnID;
    @FXML TableColumn<Equipa, String> columnDenomination;
    @FXML TableColumn<Equipa, String> columnAbbreviation;
    //@FXML TableColumn<Equipa, String> columnNumberEmployees;
    @FXML TableColumn<Equipa, String> columnMinimumNumberEmployees;
    private ObservableList<Equipa> observableListDepartments;
    private Equipa currentSelectedDepartment;
    
    //Spinner
    @FXML Spinner spinMinimumElements;
    //
    @FXML TextField txtDenomination;
    @FXML TextField txtAbbreviation;
    //Button add or change
    @FXML Button btnAddUpdateDpt;
    private Node centerNode;
    //label
    @FXML Label lblWarnings;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.spinMinimumElements.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0));
        // TODO
        this.columnID.setCellValueFactory(new PropertyValueFactory<>("idEquipa"));
        this.columnDenomination.setCellValueFactory(new PropertyValueFactory<>("designacao"));
        this.columnAbbreviation.setCellValueFactory(new PropertyValueFactory<>("abreviatura"));
        //this.columnNumberEmployees.setCellValueFactory(new PropertyValueFactory<>("numberOfElements"));
        this.columnMinimumNumberEmployees.setCellValueFactory(new PropertyValueFactory<>("minimoElementosTrabalhar"));
        //setup the table view content
        List<Equipa> ListDepartments = Repository.getSingleton().getAllDepartments();
        this.observableListDepartments =  FXCollections.observableArrayList(ListDepartments);
        this.tableDepartments.setItems(observableListDepartments);
        //
        this.tableDepartments.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.currentSelectedDepartment = newValue;
            this.btnAddUpdateDpt.setText("Details");
        });
        //
        this.setFocusProperties();
        //
    }    
    //Check denomination
    private void checkDenomination() throws Exception{
        if(this.txtDenomination.getText().isEmpty()){
            throw new Exception("Denomination is a required field!");
        }
        if(Repository.getSingleton().isFieldAlreadyInDB("select count(*) from equipa where designacao = '"+this.txtDenomination.getText()+"'"))
            throw new Exception("Denomination inserted is already in use!");
    }
    //check abbreviation
    private void checkAbbreviation() throws Exception{
        if(this.txtAbbreviation.getText().isEmpty()){
            throw new Exception("Abbreviation is a required field!");
        }
        if(Repository.getSingleton().isFieldAlreadyInDB("select count(*) from equipa where abreviatura = '"+this.txtAbbreviation.getText()+"'"))
            throw new Exception("Abbreviation inserted is already in use!");
    }
    
    public void btnAddDetailsClicked(ActionEvent event){
        if(this.btnAddUpdateDpt.getText().equals("Details")){
            //Change pane
            this.goToDetails(event);
        }else{
            try {
                //do checks
                this.checkDenomination();
                this.checkAbbreviation();
                //Save in db
                this.showConfirmation("Add New Department", "Are you sure that you want to add a new Department?");
                Equipa temp = Repository.getSingleton().addNewDepartment(this.txtDenomination.getText(), this.txtAbbreviation.getText(), (Integer) this.spinMinimumElements.getValue());
                if(temp != null){
                    this.observableListDepartments.add(temp);
                    this.showInformation("Success", "New department was added with success!");
                    this.txtAbbreviation.setText("");
                    this.txtDenomination.setText("");
                }else{
                    this.showInformation("Failed", "Error: New department couldn't be added!");
                }
            } catch (Exception ex) {
                this.lblWarnings.setText(ex.getMessage());
            }
        }
    }

    private void setFocusProperties() {
        //
        this.txtDenomination.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                this.tableDepartments.getSelectionModel().clearSelection();
                this.currentSelectedDepartment = null;
                this.btnAddUpdateDpt.setText("Add Department");
            }
        });
        this.txtAbbreviation.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                this.tableDepartments.getSelectionModel().clearSelection();
                this.currentSelectedDepartment = null;
                this.btnAddUpdateDpt.setText("Add Department");
            }
        });
        this.spinMinimumElements.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                this.tableDepartments.getSelectionModel().clearSelection();
                this.currentSelectedDepartment = null;
                this.btnAddUpdateDpt.setText("Add Department");
            }
        });
    }
    //
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

    private void goToDetails(ActionEvent event) {
        centerNode = container.getCenter();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDepartmentDetails.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FXMLDepartmentDetailsController controller = loader.getController();
        controller.setBackHandler(new IBackEvent() {
            @Override
            public void goBack(ActionEvent backEvent) {
                container.setCenter(centerNode);
                container.getBottom().setVisible(true);
                stage.setTitle(Repository.getAppName() + " - Employees");
                observableListDepartments.clear();
                List<Equipa> ListDepartments = Repository.getSingleton().getAllDepartments();
                observableListDepartments =  FXCollections.observableArrayList(ListDepartments);
                tableDepartments.setItems(observableListDepartments);
            }
        });
        stage.setTitle(Repository.getAppName() + " - Department Details");
        this.container.setCenter(parent);
        controller.setDepartment(this.currentSelectedDepartment);
        this.container.getBottom().setVisible(false);
    }
    
    //
    public void setBottom(boolean s){
        if(!s){
            container.setBottom(null);
        }
    }
    
}
