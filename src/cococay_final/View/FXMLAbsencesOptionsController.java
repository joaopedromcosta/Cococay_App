/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Model.AbsencesForList;
import cococay_final.Model.Falta;
import cococay_final.Model.Funcionario;
import cococay_final.Model.HolidaysForList;
import cococay_final.Repository;
import java.net.URL;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLAbsencesOptionsController implements Initializable {

    @FXML BorderPane container;
    @FXML Button btnAdd;
    @FXML Button btnCancel;
    @FXML CheckBox checkJustified;
    @FXML DatePicker datePickerDateOfAbsence;
    @FXML TextField txtJustification;
    @FXML Label lblWarnings;
    
    @FXML ComboBox<String> comboEmployees;
    private ObservableList<String> observableListEmployeesCombo;
    private String currentSelectedEmployeeCombo = null;
    List<Funcionario> employeesList;
    //
    //Table view
    @FXML TableView<AbsencesForList> tableAbsences;
    @FXML TableColumn<AbsencesForList, String> columnBeginDate;
    @FXML TableColumn<AbsencesForList, String> columnName;
    @FXML TableColumn<AbsencesForList, String> columnJustified;
    @FXML TableColumn<AbsencesForList, String> columnJustification;
    
    private ObservableList<AbsencesForList> observableListAbsences;
    
    /**
     * Initializes the controller class.
     */
    
    //Remove sunday and saturday of pick option
    private Callback<DatePicker, DateCell> getDayCellFactory() {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
 
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
 
                        // Disable Sunday and Saturday.
                        if (item.getDayOfWeek() == DayOfWeek.SUNDAY //
                                || item.getDayOfWeek() == DayOfWeek.SATURDAY ) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ff6b6b;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.employeesList = Repository.getSingleton().getEmployeesListOfAllDepartments();
        List<String> temp = new ArrayList<>();
        for(Funcionario f: this.employeesList){
            temp.add(f.getNome());
        }
        this.observableListEmployeesCombo = FXCollections.observableArrayList(temp);
        this.comboEmployees.setItems(observableListEmployeesCombo);
        this.comboEmployees.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.currentSelectedEmployeeCombo = newValue;
        });
        
        //
        this.datePickerDateOfAbsence.setValue(LocalDate.now());
        this.datePickerDateOfAbsence.setShowWeekNumbers(false);
        Callback<DatePicker, DateCell> dayCellFactory= this.getDayCellFactory();
        this.datePickerDateOfAbsence.setDayCellFactory(dayCellFactory);
        //Change pattern of calendar
        String pattern = "dd/MM/yyyy";
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
        @Override
        public String toString(LocalDate date) {
          if (date != null) {
            return dateFormatter.format(date);
          } else {
            return "";
          }
        }
        @Override
        public LocalDate fromString(String string) {
          if (string != null && !string.isEmpty()) {
            return LocalDate.parse(string, dateFormatter);
          } else {
            return null;
          }
        }
        };
        //Set the new string pattern for the calendar
        this.datePickerDateOfAbsence.setConverter(converter);
        //
        this.columnBeginDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.columnJustified.setCellValueFactory(new PropertyValueFactory<>("justified"));
        this.columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.columnJustification.setCellValueFactory(new PropertyValueFactory<>("justification"));

        this.observableListAbsences =  FXCollections.observableArrayList(Repository.getSingleton().getAllAbsences());
        this.tableAbsences.setItems(observableListAbsences);
        
    } 
    
    public void btnAddClicked(ActionEvent event){
        if(this.currentSelectedEmployeeCombo == null){
            this.lblWarnings.setText("Select a employee!");
            return;
        }
        if(this.checkJustified.isSelected() && this.txtJustification.getText().isEmpty()){
            this.lblWarnings.setText("Insert the justification for the absence!");
            return;
        }
        //
        try {
            this.showConfirmation("Confirm New Absence", "Are you sure that you want to add a new absence?");
            Falta temp = new Falta();
            for(Funcionario f: this.employeesList){
                if(f.getNome().equals(this.currentSelectedEmployeeCombo)){
                    temp.setIdFuncionarioFalta(f);
                }
            }
            temp.setDataFalta(Date.valueOf(this.datePickerDateOfAbsence.getValue()));
            if(this.checkJustified.isSelected()){
                temp.setJustificada((short) 1);
                temp.setDescricao(this.txtJustification.getText());
            }else{
                temp.setJustificada((short) 0);
                temp.setDescricao("N/A");
            }
            Repository.getSingleton().addNewAbsence(temp);
            this.showInformation("Success", "The new absence was added with success");
            this.currentSelectedEmployeeCombo = null;
            this.comboEmployees.getSelectionModel().clearSelection();
            this.observableListAbsences.clear();
            this.observableListAbsences =  FXCollections.observableArrayList(Repository.getSingleton().getAllAbsences());
            this.tableAbsences.setItems(observableListAbsences);
        } catch (Exception ex) {
            this.lblWarnings.setText(ex.getMessage());
        }
    }
    
    public void btnCancelClicked(ActionEvent event){
        this.comboEmployees.getSelectionModel().clearSelection();
        this.currentSelectedEmployeeCombo = null;
        this.datePickerDateOfAbsence.setValue(LocalDate.now());
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
