/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Model.Equipa;
import cococay_final.Model.FuncaoTrabalho;
import cococay_final.Model.Funcionario;
import cococay_final.Repository;
import java.math.BigDecimal;
import java.net.URL;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLNewEmployeePersonalDataController implements Initializable {
    
    @FXML Label lblWarnings;
    //Personal data
    @FXML TextField txtName;
    @FXML TextField txtAdress;
    @FXML TextField txtPostalCode1;
    @FXML TextField txtPostalCode2;
    @FXML TextField txtCity;
    @FXML DatePicker datePiBirthDate;
    //Tax Data
    @FXML TextField txtCivilIdNumber;
    @FXML TextField txtTaxNumber;
    @FXML TextField txtSocialSecurityNumber;
    @FXML TextField txtIBAN;
    @FXML TextField txtSalary;
    @FXML TextField txtOtherPosition;
    
    //
    @FXML ComboBox<String> comboPositionHeld;
    private ObservableList<String> positionHeldObservableList;
    private String currentSelectedPositionHeld;
    
    //Departments Variables
    @FXML ComboBox<String> comboDepartment;
    private ObservableList<String> departmentObservableList;
    private String currentSelectedDepartment;
    //
    @FXML Button btnDelete;
    @FXML Button btnAdd;
    //List of departments added
    @FXML ListView listDepartmentsAdded;
    private ObservableList<String> observableListDepartmentsAdded;
    private String currentSelectedDepartmentOfListView = null;
    //Login details variables
    @FXML TextField txtDefaultUserPassword;
    //list of all the positions and departmens available in the db
    List<FuncaoTrabalho> positionHeldList;
    List<Equipa> departmentsList;
    //
    Funcionario newEmployee;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.setFieldMaxLength();
        this.setCalendar();
        this.setComboBoxes();
        this.setFocusProperties();
        //Set departments added list 
        this.observableListDepartmentsAdded = FXCollections.observableArrayList();
        this.listDepartmentsAdded.setItems(observableListDepartmentsAdded);
            //set a listener for the list view selection
        this.listDepartmentsAdded.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.currentSelectedDepartmentOfListView = (String) newValue;
            
        });
    }    
    //fields events
    
    
    //Set the maximum ammount of caracthers for each field
    private void setFieldMaxLength(){
        //set name maximum 100 bytes
        this.txtName.setOnKeyTyped(event ->{
            this.lblWarnings.setText("");
            int maxCharacters = 99;
            if(txtName.getText().length() >= maxCharacters){
                event.consume();
                this.lblWarnings.setText("Warning: Maximum name size of 100 characters reached!");
            }
            if(event.getCharacter().length() > 0){
                if(Character.isDigit(event.getCharacter().charAt(0)))
                    consumeCharacters(event);
            }
        });
        //set adress maximum 100 bytes
        this.txtAdress.setOnKeyTyped(event ->{
            int maxCharacters = 99;
            if(txtAdress.getText().length() >= maxCharacters){
                event.consume();
                this.lblWarnings.setText("Warning: Maximum adress size of 100 characters reached!");
            }

        });
        //set maximum 4 caracther
        this.txtPostalCode1.setOnKeyTyped(event ->{
            int maxCharacters = 4;
            if(txtPostalCode1.getText().length() >= maxCharacters)
                event.consume();
            if(event.getCharacter().length() > 0){
                if(Character.isLetter(event.getCharacter().charAt(0)))
                    consumeCharacters(event);
            }
        });
        //set maximum 3 caracther
        this.txtPostalCode2.setOnKeyTyped(event ->{
            int maxCharacters = 3;
            if(txtPostalCode2.getText().length() >= maxCharacters) 
                event.consume();
            if(event.getCharacter().length() > 0){
                if(Character.isLetter(event.getCharacter().charAt(0)))
                    consumeCharacters(event);
            }
        });
        //set maximum 40 caracther
        this.txtCity.setOnKeyTyped(event ->{
            int maxCharacters = 40;
            if(txtCity.getText().length() >= maxCharacters) 
                event.consume();
            if(event.getCharacter().length() > 0){
                if(Character.isDigit(event.getCharacter().charAt(0)))
                    consumeCharacters(event);
            }
        });
        //
        this.txtCivilIdNumber.setOnKeyTyped(event ->{
            int maxCharacters = 8;

            if(txtCivilIdNumber.getText().length() >= maxCharacters) 
                event.consume();
            if(event.getCharacter().length() > 0){
                if(Character.isLetter(event.getCharacter().charAt(0)))
                    consumeCharacters(event);
            }
        });
        //
        this.txtTaxNumber.setOnKeyTyped(event ->{
            int maxCharacters = 9;
            if(txtTaxNumber.getText().length() >= maxCharacters) 
                event.consume();
            if(event.getCharacter().length() > 0){
                if(Character.isLetter(event.getCharacter().charAt(0)))
                    consumeCharacters(event);
            }
        });
        //
        this.txtSocialSecurityNumber.setOnKeyTyped(event ->{
            int maxCharacters = 11;

            if(txtSocialSecurityNumber.getText().length() >= maxCharacters) 
                event.consume();
            if(event.getCharacter().length() > 0){
                if(Character.isLetter(event.getCharacter().charAt(0)))
                    consumeCharacters(event);
            }
        });
        //
        this.txtIBAN.setOnKeyTyped(event ->{
            int maxCharacters = 25;
            if(txtIBAN.getText().length() >= maxCharacters) event.consume();
        });
        //
        this.txtOtherPosition.setOnKeyTyped(event ->{
            int maxCharacters = 100;
            if(txtOtherPosition.getText().length() >= maxCharacters) event.consume();
            if(event.getCharacter().length() > 0){
                if(Character.isDigit(event.getCharacter().charAt(0)))
                    consumeCharacters(event);
            }
        });
    }
    //Consume character
    private void consumeCharacters(KeyEvent event){
        event.consume();
    }
    //Set the birth date calendar
    private void setCalendar() {
        this.datePiBirthDate.setShowWeekNumbers(false);
        this.datePiBirthDate.setValue(LocalDate.now().minusYears(18));//Because the new employee must be at least 18 years old
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
      this.datePiBirthDate.setConverter(converter);
      
    }
    //Set the position held and department combo boxes
    private void setComboBoxes() {
        //
        this.positionHeldList = Repository.getSingleton().getPositionHeldList();
        List<String> temp = new ArrayList<>();
        for(FuncaoTrabalho x: this.positionHeldList){
            temp.add(x.getDesignacaoEquipa());
        }
        temp.add("Create New...");
        this.positionHeldObservableList = FXCollections.observableArrayList(temp);
        this.comboPositionHeld.setItems(positionHeldObservableList);
        this.comboPositionHeld.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.currentSelectedPositionHeld = newValue;
            if(newValue.equals("Create New...")){
                this.currentSelectedPositionHeld = null;
                this.txtOtherPosition.setEditable(true);
                this.txtOtherPosition.setPromptText("insert new position");
                
            }else{
                this.txtOtherPosition.setEditable(false);
                this.txtOtherPosition.setPromptText("");
                this.txtOtherPosition.setText("");
            }
        });
        //
        this.departmentsList = Repository.getSingleton().getAllActiveDepartments();
        temp = new ArrayList<>();
        for(Equipa x: this.departmentsList){
            temp.add(x.getDesignacao());
        }
        this.departmentObservableList = FXCollections.observableArrayList(temp);
        this.comboDepartment.setItems(departmentObservableList);
        this.comboDepartment.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.currentSelectedDepartment = newValue;
        });
    }
    //When a text field loses focus the parameter must be checked
    private void setFocusProperties() {
        //Name field must be not empty and may only contain letters
        this.txtName.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue){
                //Set a new default user/password. The first and last name
                if(!this.txtName.getText().isEmpty()){
                    int first = this.txtName.getText().indexOf(" ");
                    while(this.txtName.getText().charAt(this.txtName.getText().length() - 1) == ' '){
                        this.txtName.setText(this.txtName.getText().substring(0, this.txtName.getText().length() - 1));
                    }
                    int last = this.txtName.getText().lastIndexOf(" ");
                    String userPassword;
                    //Put each caracther decapitalized
                    if(first == -1 || last == this.txtName.getText().length() - 1){
                        userPassword = (this.txtName.getText()).toLowerCase();
                    }else{
                        userPassword = (this.txtName.getText().substring(0, first) + this.txtName.getText().substring(last + 1, this.txtName.getText().length())).toLowerCase();
                    }
                    //Remove all accent from the word
                    this.txtDefaultUserPassword.setText(Normalizer.normalize(userPassword, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", ""));
                }
                try { this.checkNameField(false); } catch (Exception ex) { this.lblWarnings.setText(ex.getMessage());}
            }
        });
        //Adress field must be not empty
        this.txtAdress.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue){
                try { this.checkAdressField(false); } catch (Exception ex) { this.lblWarnings.setText(ex.getMessage()); }
            }
        });
        //
        this.txtCity.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue){
                try { this.checkPostalCodeField(false); } catch (Exception ex) { this.lblWarnings.setText(ex.getMessage()); }
            }
        });
        this.txtCivilIdNumber.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue){
                try { this.checkCivilIdNumberField(true); } catch (Exception ex) { this.lblWarnings.setText(ex.getMessage()); }
            }
        });
        this.txtTaxNumber.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue){
                try { this.checkTaxNumberField(true); } catch (Exception ex) { this.lblWarnings.setText(ex.getMessage()); }
            }
        });
        this.txtSocialSecurityNumber.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue){
                try { this.checkSocialSecurityNumberField(true); } catch (Exception ex) { this.lblWarnings.setText(ex.getMessage()); }
            }
        });
        this.txtIBAN.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue){
                try { this.checkIBANField(true); } catch (Exception ex) { this.lblWarnings.setText(ex.getMessage()); }
            }
        });
        this.txtSalary.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue){
                try { this.checkSalaryField(false); } catch (Exception ex) { this.lblWarnings.setText(ex.getMessage()); }
            }
        });
    }
    //Check fields
    private void checkNameField(boolean checkDB) throws Exception{
        if(this.txtName.getText().isEmpty()){
            throw new Exception("Warning: Name field is a required field!");
        }
        for(int i = 0; i < this.txtName.getText().length(); i++){
            if(Character.isDigit(this.txtName.getText().charAt(i))){
                throw new Exception("Warning: Name field must only contain letters");
            }
        }
    }
    private void checkAdressField(boolean checkDB) throws Exception{
        if(this.txtAdress.getText().isEmpty()){
            throw new Exception("Warning: Adress field is a required field!");
        }
    }
    private void checkPostalCodeField(boolean checkDB) throws Exception{
        if(this.txtPostalCode1.getText().isEmpty() || this.txtPostalCode2.getText().isEmpty()){
            throw new Exception("Warning: Postal Code field is a required field!");
        }
        //Check if postal cod has the minimum ammount of numbers
        if(this.txtPostalCode1.getText().length() < 4 || this.txtPostalCode2.getText().length() < 3){
            throw new Exception("Warning: Postal Code inserted must be in this format: 0000 - 000!");
        }
        //Check if both postal cod fields only contain numbers
        for(int i = 0; i < this.txtPostalCode1.getText().length(); i++){
            if(Character.isLetter(this.txtPostalCode1.getText().charAt(i))){
                throw new Exception("Warning: Postal Code field must only contain numbers!");
            }
        }
        for(int i = 0; i < this.txtPostalCode2.getText().length(); i++){
            if(Character.isLetter(this.txtPostalCode2.getText().charAt(i))){
                throw new Exception("Warning: Postal Code field must only contain numbers!");
            }
        }
        //Check if city field is not empty and  has only letters
        if(this.txtCity.getText().isEmpty()){
            throw new Exception("Warning: City field is a required field!");
        }
        for(int i = 0; i < this.txtCity.getText().length(); i++){
            if(Character.isDigit(this.txtCity.getText().charAt(i))){
                throw new Exception("Warning: City field must only contain letters!");
            }
        }
    }
    private void checkCivilIdNumberField(boolean checkDB) throws Exception{
        if(this.txtCivilIdNumber.getText().isEmpty()){
            throw new Exception("Warning: Civil Number ID is a required field!");
        }
        if(this.txtCivilIdNumber.getText().length() < 8)
            throw new Exception("Warning: Civil Number ID must contain 8 numbers!");
        for(int i = 0; i < this.txtCivilIdNumber.getText().length(); i++){
            if(Character.isLetter(this.txtCivilIdNumber.getText().charAt(i))){
                throw new Exception("Warning: Civil Number ID must contain only numbers!");
            }
        }
        if(checkDB){
            //Check db for already existing registry
            if(Repository.getSingleton().isFieldAlreadyInDB("select count(*) from Funcionario where NUMERO_CARTAO_CIDADAO = '"+this.txtCivilIdNumber.getText()+"'")){
                throw new Exception("Warning: Civil ID number is already in use by another employee!");
            }
        }
    }
    private void checkTaxNumberField(boolean checkDB) throws Exception{
        if(this.txtTaxNumber.getText().isEmpty()){
            throw new Exception("Warning: Tax number is a required field!");
        }
        if(this.txtTaxNumber.getText().length() < 9)
            throw new Exception("Warning: Tax number must contain 9 numbers!");
        for(int i = 0; i < this.txtTaxNumber.getText().length(); i++){
            if(Character.isLetter(this.txtTaxNumber.getText().charAt(i))){
                throw new Exception("Warning: Tax number must contain only numbers!");
            }
        }
        if(checkDB){
            //Check db for already existing registry
            if(Repository.getSingleton().isFieldAlreadyInDB("select count(*) from Funcionario where NUMERO_CONTRIBUINTE = '"+this.txtTaxNumber.getText()+"'")){
                throw new Exception("Warning: Tax number is already in use by another employee!");
            }
        }
    }
    private void checkSocialSecurityNumberField(boolean checkDB) throws Exception{
        if(this.txtSocialSecurityNumber.getText().isEmpty()){
            throw new Exception("Warning: Social Security number is a required field!");
        }
        if(this.txtSocialSecurityNumber.getText().length() < 11)
            throw new Exception("Warning: Social Security number must contain 11 numbers!");
        for(int i = 0; i < this.txtSocialSecurityNumber.getText().length(); i++){
            if(Character.isLetter(this.txtSocialSecurityNumber.getText().charAt(i))){
                throw new Exception("Warning: Social Security number must contain only numbers!");
            }
        }
        if(checkDB){
            //Check db for already existing registry
            if(Repository.getSingleton().isFieldAlreadyInDB("select count(*) from Funcionario where NUMERO_SEGURANCA_SOCIAL = '"+this.txtSocialSecurityNumber.getText()+"'")){
                throw new Exception("Warning: Social Security number is already in use by another employee!");
            }
        }
    }
    private void checkIBANField(boolean checkDB) throws Exception{
        if(this.txtIBAN.getText().isEmpty() || this.txtIBAN.getText().equals("PT50")){
            throw new Exception("Warning: IBAN number is a required field!");
        }
        if(this.txtIBAN.getText().length() < 25)
            throw new Exception("Warning: IBAN number size must be 25 characters!");
        for(int i = 0; i < this.txtIBAN.getText().length(); i++){
            if(i < 2){
                if(Character.isDigit(this.txtIBAN.getText().charAt(i))){
                    throw new Exception("Warning: IBAN first two elements must be letters!");
                }
            }else{
                if(Character.isLetter(this.txtIBAN.getText().charAt(i))){
                    throw new Exception("Warning: IBAN number must contain only numbers after second element!");
                }
            }
        }
        if(checkDB){
            //Check db for already existing registry
            if(Repository.getSingleton().isFieldAlreadyInDB("select count(*) from Funcionario where IBAN = '"+this.txtIBAN.getText()+"'")){
                throw new Exception("Warning: IBAN number is already in use by another employee!");
            }
        }
    }
    private void checkSalaryField(boolean checkDB) throws Exception{
        if(this.txtSalary.getText().isEmpty()){
            throw new Exception("Warning: Salary is a required field!");
        }
        for(int i = 0; i < this.txtSalary.getText().length(); i++){
            if(Character.isLetter(this.txtSalary.getText().charAt(i)) && this.txtSalary.getText().charAt(i) != '.'){
                throw new Exception("Warning: Salary must contain only numbers!");
            }
        }
        String salaryString = this.txtSalary.getText();
        float salary = Float.parseFloat(salaryString);
        if(salary < Repository.getSingleton().getMinimumWage())
            throw new Exception("Warning: Salary must be above the minimum wage!");
    }
    //Check position held selected
    public void checkPositionHeldSelected() throws Exception{
        if(this.currentSelectedPositionHeld == null && !this.txtOtherPosition.isEditable()){
            throw new Exception("Warning: Position Held is a required field!");
        }
        //if the text field is editable then there must be a new position inserted. Test if there is text and also test if that position already exists
        if(this.txtOtherPosition.isEditable()){
            if(this.txtOtherPosition.getText().isEmpty()){
                throw new Exception("Warning: Insert a new position in the correct field!");
            }
            if(this.positionHeldObservableList.contains(this.txtOtherPosition.getText())){
                this.comboPositionHeld.getSelectionModel().select(this.txtOtherPosition.getText());
                this.txtOtherPosition.setEditable(false);
                this.txtOtherPosition.setText("");
                this.currentSelectedPositionHeld = this.comboPositionHeld.getSelectionModel().getSelectedItem();
            }else{
                this.showConfirmation("Confirm New Position", "Are you sure that you want to add this new position '"+this.txtOtherPosition.getText()+"' to the company?");
                this.currentSelectedPositionHeld = this.txtOtherPosition.getText();
            }
        }
        
    }
    //Check departements selected
    private void checkDepartmentsSelected(boolean checkDB) throws Exception{
        if(this.observableListDepartmentsAdded.isEmpty()){
            throw new Exception("Warning: Select at least one department!");
        }
    }
    //Check username...if already exists ask to change a little bit
    private void checkUserName(boolean checkDB) throws Exception{
        if(this.txtDefaultUserPassword.getText().isEmpty()){
            throw new Exception("Warning: User/Password is a required field!");
        }
        if(this.txtDefaultUserPassword.getText().length() < 6){
            throw new Exception("Warning: Username too short!");
        }
        if(checkDB){
            if(Repository.getSingleton().isFieldAlreadyInDB("select count(*) from login where username = '"+this.txtDefaultUserPassword.getText()+"'")){
                throw new Exception("Warning: Username selected is already in use!");
            }
        }
    }
    @FXML
    public void btnAddClicked(ActionEvent event){
        if(this.currentSelectedDepartment != null)
            if(!this.observableListDepartmentsAdded.contains(this.currentSelectedDepartment))//If the list doesnt have the new department then add him
                this.observableListDepartmentsAdded.add(this.currentSelectedDepartment);
        this.currentSelectedDepartment = null;
        this.comboDepartment.getSelectionModel().clearSelection();
    }
    @FXML
    public void btnDeleteClicked(ActionEvent event){
        if(this.currentSelectedDepartmentOfListView != null){
            this.observableListDepartmentsAdded.remove(this.observableListDepartmentsAdded.indexOf(this.currentSelectedDepartmentOfListView));
        }
        this.currentSelectedDepartmentOfListView = null;
        this.listDepartmentsAdded.getSelectionModel().clearSelection();
    }
    //Do all the checks
    private void checkAllFields() throws Exception{
        //Do all checks and if all good, add to db
        this.checkNameField(true);
        this.checkAdressField(true);
        this.checkPostalCodeField(true);
        this.checkCivilIdNumberField(true);
        this.checkTaxNumberField(true);
        this.checkSocialSecurityNumberField(true);
        this.checkIBANField(true);
        this.checkSalaryField(true);
        this.checkPositionHeldSelected();
        this.checkDepartmentsSelected(true);
        this.checkUserName(true);
    }
    //Set the cursor for the first field empty
    private void setCursorForEmptyField(String message){
        if(message.contains("Name")){
            this.txtName.requestFocus();
        }
        if(message.contains("Adress")){
            this.txtAdress.requestFocus();
        }
        if(message.contains("Postal Code")){
            this.txtPostalCode1.requestFocus();
        }
        if(message.contains("City")){
            this.txtCity.requestFocus();
        }
        if(message.contains("Civil Number ID")){
            this.txtCivilIdNumber.requestFocus();
        }
        if(message.contains("Tax number")){
            this.txtTaxNumber.requestFocus();
        }
        if(message.contains("Social Security number")){
            this.txtSocialSecurityNumber.requestFocus();
        }
        if(message.contains("IBAN")){
            this.txtIBAN.requestFocus();
        }
        if(message.contains("Salary")){
            this.txtSalary.requestFocus();
        }
        if(message.contains("User/Password")){
            this.txtDefaultUserPassword.requestFocus();
        }
    }
    //Add new employee to the database after all the checks
    public void addEmployee() throws Exception{
        boolean sendException = false;
        try{
            this.checkAllFields();
            this.addEmployeeToDB();
            this.addEmployeeToDepartmentsDB();
            this.addLoginDB();
        }catch(Exception e){
            this.lblWarnings.setText(e.getMessage());
            this.setCursorForEmptyField(e.getMessage());
            sendException = true;
        }
        if(sendException)
            throw new Exception("");
        sendException = false;
    }
    
    //
    private FuncaoTrabalho createNewPosition() throws Exception {
        for(FuncaoTrabalho e: this.positionHeldList){
            if(e.getDesignacaoEquipa().equals(this.currentSelectedPositionHeld))
                return e;
        }
        return Repository.getSingleton().addNewPositionHeld(this.currentSelectedPositionHeld);
    }
    //
    private void addEmployeeToDB() throws Exception {
        FuncaoTrabalho funcaoTrabalho = this.createNewPosition();
        if(funcaoTrabalho == null)
            throw new Exception("Error: An error has occurred when trying to add the new position. Contact the system Administrator!");
        Funcionario temp = new Funcionario();
        temp.setIdFuncaoTrabalho(funcaoTrabalho);
        temp.setNome(this.txtName.getText());
        temp.setMorada(this.txtAdress.getText());
        temp.setCodigoPostal(this.txtPostalCode1.getText() + "-" + this.txtPostalCode2.getText() + " " + this.txtCity.getText());
        //add birth date
        temp.setDataNascimento(null);
        temp.setNumeroCartaoCidadao(this.txtCivilIdNumber.getText());
        temp.setNumeroSegurancaSocial(this.txtSocialSecurityNumber.getText());
        temp.setNumeroContribuinte(this.txtTaxNumber.getText());
        temp.setIban(this.txtIBAN.getText());
        temp.setNumeroDiasFeriasTotal((short) 25);
        temp.setNumeroDiasFeriasAtraso((short) 0);
        temp.setSalario(BigDecimal.valueOf(Double.valueOf(this.txtSalary.getText())));
        temp.setDataContrato(new Date());
        temp.setEstado((short) 1);
        this.newEmployee = Repository.getSingleton().addNewEmployee(temp);
        this.addEmployeeToDepartmentsDB();
    }
    //
    private void addEmployeeToDepartmentsDB() throws Exception {
        List<Long> listOfIDDepartments = new ArrayList<>();
        for(String e: this.observableListDepartmentsAdded){
            search1: {
                for(Equipa d: this.departmentsList){
                    if(d.getDesignacao().equals(e)){
                        listOfIDDepartments.add(d.getIdEquipa());
                        break search1;
                    }
                }
            }
        }
        Repository.getSingleton().setDepartmentsForNewEmployee(listOfIDDepartments, this.newEmployee.getIdFuncionario());
    }
    //
    private void addLoginDB() throws Exception {
        Repository.getSingleton().setNewLoggin(this.newEmployee, this.txtDefaultUserPassword.getText());
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
