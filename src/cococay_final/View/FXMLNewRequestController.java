/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Model.Ferias;
import cococay_final.Model.RestricaoFerias;
import cococay_final.Repository;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLNewRequestController implements Initializable {

    @FXML BorderPane borderPaneNewRequest;
    @FXML DatePicker dtPickerBegin;
    @FXML DatePicker dtPickerEnd;
    @FXML Label lblWarnings;
    @FXML Label lblDaysAvailable;
    @FXML Button btnConfirm;
    @FXML Button btnRefresh;
    
    private FXMLCalendarController calendarController; 
    
    private List<Ferias> currentMonthLoggedEmployeeHolidays;
    private List<RestricaoFerias> currentMonthDisplayedRestrictions;
    private int numberOfHolidaysAvailable;
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.btnRefresh.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/refresh.png"))));
        this.btnRefresh.setTooltip(new Tooltip("Refresh Calendar"));
        //Create the calendar view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCalendar.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.calendarController = loader.getController();
        this.calendarController.setShowPersonalHolidays(true);
        this.calendarController.setShowRestrictions(true);
        this.calendarController.setShowDptHolidays(true);
        this.calendarController.updateCalendar();
        this.borderPaneNewRequest.setTop(parent);
        this.currentMonthDisplayedRestrictions = this.calendarController.getCurrentMonthDisplayedRestrictions();
        //Define calendar pickers values
        this.dtPickerBegin.setValue(LocalDate.now());
        this.dtPickerEnd.setValue(this.dtPickerBegin.getValue().plusDays(1));
        //Disable week numbers
        this.dtPickerBegin.setShowWeekNumbers(false);
        this.dtPickerEnd.setShowWeekNumbers(false);
        // Factory to create Cell of DatePicker
        Callback<DatePicker, DateCell> dayCellFactory= this.getDayCellFactory();
        this.dtPickerBegin.setDayCellFactory(dayCellFactory);
        this.dtPickerEnd.setDayCellFactory(dayCellFactory);
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
      this.dtPickerBegin.setConverter(converter);
      this.dtPickerEnd.setConverter(converter);
      //Change label text for warnings and days available
      this.lblWarnings.setText("");
      this.lblDaysAvailable.setText("Days Available: " + Repository.getSingleton().getNumberHolidaysLoggedEmployee());
      this.numberOfHolidaysAvailable = Repository.getSingleton().getNumberHolidaysLoggedEmployee();
      
    }    
    
    //Check if date selected is prior to the current date
    @FXML
    public void beginDatePickerChanged(ActionEvent event){
        try{
            this.checkTemporaryDates(this.dtPickerBegin.getValue());
            
        }catch(Exception e){
            this.lblWarnings.setText(e.getMessage());
        }
    }
    //Check if date selected is prior to the current date
    @FXML
    public void endDatePickerChanged(ActionEvent event){
        try{
            this.checkTemporaryDates(this.dtPickerEnd.getValue());
        }catch(Exception e){
            this.lblWarnings.setText(e.getMessage());
        }
    }
    //Confirm button clicked
    @FXML
    public void confirmBtnClicked(ActionEvent event){
        this.lblWarnings.setText("");
        try {
            this.checkFinalDates();
            this.showConfirmation("Confirm New Request","Are you sure you want to open a new request?");
            Repository.getSingleton().addNewHoliday(this.dtPickerBegin, this.dtPickerEnd, this.numberOfDaysSelected(true));
            this.showInformation("Success", "Your request has been registed. Now you must wait for a validation!");
        } catch (Exception ex) {
            this.lblWarnings.setText(ex.getMessage());
        }
    }
    //Check for restrictions or number of employees from same departement exceed
    private void checkFinalDates() throws Exception{
        System.out.println("\t\t\tEnter");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Date dt;
        //
        for(int i = 0; i < this.numberOfDaysSelected(false); i++){
            String tempDT = dtf.format(this.dtPickerBegin.getValue().plusDays(i));
            if(Repository.getSingleton().isRestriction(tempDT))
                throw new Exception("Warning: Selected days are defined as Restrictions!");
            
        }
        //Check maximum of elements in holidays
        for(int i = 0; i < this.numberOfDaysSelected(false); i++){
            String tempDT = dtf.format(this.dtPickerBegin.getValue().plusDays(i));
            System.out.println("\t\tDate: " + tempDT);
            if(Repository.getSingleton().hasReachedMaximumDptElementsOnHolidays(tempDT))
                throw new Exception("Warning: Maximum ammount of employees on holidays for the period selected has been reached!");
            //Check if logged employee has holidays already scheduled in the current period
            if(Repository.getSingleton().hasAlreadyScheduledHolidays(tempDT))
                throw new Exception("Warning: You have holidays already scheduled for the selected period!");
        }
        System.out.println("\t\t\tFinal Exit");
    }
    //Check the ammount of days selected for the holidays
    private int numberOfDaysSelected(boolean bol){
        //After all checks are completed successfuly
        LocalDate beginDt = this.dtPickerBegin.getValue();
        LocalDate endDt = this.dtPickerEnd.getValue();
        System.out.println("Diference in days: " + (ChronoUnit.DAYS.between(beginDt, endDt)));
        //Get number of days between two dates including weekends and the begin and end dates
        int temp = (int) (ChronoUnit.DAYS.between(beginDt, endDt)) + 1;
        //Set a calendar type of date corresponding to the datepicker begin
        Calendar calendarDateBegin = Calendar.getInstance();
        calendarDateBegin.set(beginDt.getYear(), beginDt.getMonthValue() - 1, beginDt.getDayOfMonth());
        //Check number of weekend days
        if(bol)
            for(int i = 0; i < (ChronoUnit.DAYS.between(beginDt, endDt)) + 1; i++){
                //If day of the week is sunday or saturday increase one day to available days
                if(calendarDateBegin.get(Calendar.DAY_OF_WEEK) == 1 || calendarDateBegin.get(Calendar.DAY_OF_WEEK) == 7){
                    temp--;
                }
                calendarDateBegin.add(Calendar.DAY_OF_MONTH, 1);
            }
        return temp;
    }
    
    private void checkTemporaryDates(LocalDate date) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.lblWarnings.setText("");
        Date dt = Date.valueOf(date);
        //Check if date argument is the same as the  begin date picker
        if(date.isEqual(this.dtPickerBegin.getValue())){
            if(date.isAfter(this.dtPickerEnd.getValue())){//If it is checks if the begin date is later than the end date
                this.dtPickerEnd.setValue(this.dtPickerBegin.getValue().plusDays(1));//If it is the end date must be changed to later than the begin date
            }
        }else{//The oposite must be checked
            if(date.isBefore(this.dtPickerBegin.getValue())){
                this.dtPickerBegin.setValue(this.dtPickerEnd.getValue().minusDays(1));
            }
        }
        //
        if(this.dtPickerBegin.getValue().isBefore(LocalDate.now())){
            throw new Exception("Warning: Selected days must be later than the current day!");
        }
        //
        int nDays = this.numberOfDaysSelected(true);
        System.out.println(nDays);
        this.lblDaysAvailable.setText("Days Available: " + (this.numberOfHolidaysAvailable - nDays));
        if(nDays > this.numberOfHolidaysAvailable)
            throw new Exception("Warning: The selected number of days exceeds the number of days available!");
        //
        this.currentMonthDisplayedRestrictions = null;
        this.checkFinalDates();
        if(nDays > 10){
            throw new Exception("Note: The selected number of days exceeds the 10 days margin!");
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
    private void showInformation(String title, String header) throws Exception{
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.FINISH);
        alert.setTitle(title);
        alert.setHeaderText(header);
        Optional<ButtonType> option = alert.showAndWait();
    }
    
    @FXML
    public void btnRefreshClicked(ActionEvent event){
        this.calendarController.updateDayElements();
    }
}
