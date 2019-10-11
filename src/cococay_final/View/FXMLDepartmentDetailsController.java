/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Model.EmployeesForList;
import cococay_final.Model.Equipa;
import cococay_final.Model.FuncaoTrabalho;
import cococay_final.Model.Funcionario;
import cococay_final.Repository;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLDepartmentDetailsController implements Initializable {
    
    
    @FXML TableView<Funcionario> employeesTable;
    //
    @FXML TableColumn<Funcionario, String> idCol;
    @FXML TableColumn<Funcionario, String> nameCol;
    //
    private ObservableList<Funcionario> employeesObservableList;
    private Funcionario currentSelectedEmployee = null;
    //
    @FXML ComboBox<String> comboEmployees;
    private ObservableList<String> observableListEmployeesCombo;
    private String currentSelectedEmployeeCombo = null;
    List<Funcionario> employeesList;
    
    @FXML Button btnGoBack;
    @FXML Button btnAdd;
    @FXML Button btnDelete;
    @FXML Button btnSave;
    @FXML Label lblWarnings;
    @FXML CheckBox checkBoxState;
    //Spinner
    @FXML Spinner spinMinimumElements;
    //
    @FXML TextField txtDenomination;
    @FXML TextField txtAbbreviation;
    
    private Equipa dpt;
    private List<Funcionario> originalList;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.spinMinimumElements.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0));
        this.idCol.setCellValueFactory(new PropertyValueFactory<>("idFuncionario"));
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.employeesObservableList =  FXCollections.observableArrayList();
        this.employeesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.currentSelectedEmployee = newValue;
        });
        this.btnAdd.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/add.png"))));
        this.btnAdd.setTooltip(new Tooltip("Add Employee to this Department"));
        this.btnDelete.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/minus.png"))));
        this.btnDelete.setTooltip(new Tooltip("Remove Employee from this Department"));
        this.btnGoBack.setVisible(false);
    }    
    //
    public void btnAddClicked(ActionEvent event){
        if(this.currentSelectedEmployeeCombo == null){
            this.lblWarnings.setText("\tSelect a employee to add!");
        }else{
            search:{
                for(Funcionario f: this.employeesList){
                    if(f.getNome().equals(this.currentSelectedEmployeeCombo)){
                        this.employeesObservableList.add(f);
                        break search;
                    }
                }
            }
            this.observableListEmployeesCombo.remove(this.currentSelectedEmployeeCombo);
            this.currentSelectedEmployeeCombo = null;
            this.comboEmployees.getSelectionModel().clearSelection();
        }
    }
    //
    public void btnDeleteClicked(ActionEvent event){
        if(this.currentSelectedEmployee == null){
            this.lblWarnings.setText("\tSelect a employee to remove!");
        }else{
            this.observableListEmployeesCombo.add(this.currentSelectedEmployee.getNome());
            this.employeesObservableList.remove(this.currentSelectedEmployee);
            this.currentSelectedEmployee = null;
            this.employeesTable.getSelectionModel().clearSelection();
        }
    }
    //
    public void btnSaveClicked(ActionEvent event){
        try {
            this.doChecks();
            this.showConfirmation("Confirm Changes", "Are you sure you want to save all changes?");
            this.updateData();
            this.showInformation("Success", "All changes saved with success!");
            this.btnGoBack.fire();
        } catch (Exception ex) { System.out.println(ex.getMessage());}
    }
    //
    public void btnReturnClicked(ActionEvent event){
        try {
            this.showConfirmation("Confirm Return", "Are you sure you want to return without saving all changes?");
            this.btnGoBack.fire();
        } catch (Exception ex) { }
    }
    //
    public void setTable(){
        //
        List<Funcionario> employeesList = Repository.getSingleton().getEmployeesListOfDepartment(dpt.getIdEquipa());
        this.employeesObservableList.addAll(employeesList);
        this.employeesTable.setItems(employeesObservableList);
        //Set combobox
        this.employeesList = Repository.getSingleton().getEmployeesListOfAllDepartments();
        List<String> temp = new ArrayList<>();
        for(Funcionario f: this.employeesList){
            if(!this.employeesObservableList.contains(f))
                temp.add(f.getNome());
        }
        this.observableListEmployeesCombo = FXCollections.observableArrayList(temp);
        this.comboEmployees.setItems(observableListEmployeesCombo);
        this.comboEmployees.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.currentSelectedEmployeeCombo = newValue;
            
        });
        this.originalList = new ArrayList<>();
        this.originalList.addAll(this.employeesObservableList);
    }

    public void setBackHandler(IBackEvent iBackEvent) {
        this.btnGoBack.setOnAction(e -> iBackEvent.goBack(e));
    }
    public void setDepartment(Equipa t){
        dpt = t;
        this.txtDenomination.setText(dpt.getDesignacao());
        this.txtAbbreviation.setText(dpt.getAbreviatura());
        this.spinMinimumElements.getValueFactory().setValue(Integer.parseInt(Short.toString(dpt.getMinimoElementosTrabalhar())));
        if(dpt.getEstado() == 1){
            this.checkBoxState.setSelected(true);
        }else{
            this.checkBoxState.setSelected(false);
        }
        this.setTable();
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

    private void updateData() throws Exception {
        if(!dpt.getDesignacao().equals(this.txtDenomination.getText()) || !dpt.getAbreviatura().equals(this.txtAbbreviation.getText()) || dpt.getMinimoElementosTrabalhar()!= Short.valueOf(Integer.toString((int) this.spinMinimumElements.getValue())) || dpt.getEstado() != (this.checkBoxState.isSelected() ? 1 : 0)){
            Equipa t = new Equipa(this.dpt.getIdEquipa());
            t.setDesignacao(this.txtDenomination.getText());
            t.setAbreviatura(this.txtAbbreviation.getText());
            t.setMinimoElementosTrabalhar(Short.valueOf(Integer.toString((int) this.spinMinimumElements.getValue())));
            t.setEstado((short) (this.checkBoxState.isSelected() ? 1 : 0));
            Repository.getSingleton().updateDepartmentDetails(t);
        }
        //
        this.originalList.stream().filter((e) -> (!this.employeesObservableList.contains(e))).forEachOrdered((e) -> {
            //if the observable list doesnt contain the original employee then he was deleted...
            Repository.getSingleton().removerEmployeeFromDepartment(this.dpt.getIdEquipa(), e.getIdFuncionario());
        });
        //
        this.employeesObservableList.removeAll(this.originalList);
        List<Long> id = new ArrayList<>();
        id.add(this.dpt.getIdEquipa());
        if(!this.employeesObservableList.isEmpty()){
            for(Funcionario f: this.employeesObservableList){
                Repository.getSingleton().setDepartmentsForNewEmployee(id, f.getIdFuncionario());
            }
        }
    }

    private void doChecks() throws Exception {
        if(this.txtDenomination.getText().isEmpty())
            throw new Exception("\tDenomination is a required field!");
        if(this.txtAbbreviation.getText().isEmpty())
            throw new Exception("\tAbbreviation is a required field!");
    }

}
