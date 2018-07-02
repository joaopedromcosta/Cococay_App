/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Model.Ferias;
import cococay_final.Model.Funcionario;
import cococay_final.Model.RestricaoFerias;
import cococay_final.Repository;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLCalendarController implements Initializable {
    //
    private int currentYearDisplayed;//Save the current year being displayed. By default, is the current year
    private int currentMonthDisplayed;//Save the current month being displayed. By default, is the current month
    private int currentDay;//Save the current day being displayed. By default, is the current day
    private int firstDayWeekOfCurrentMonth;//Save the day of the week(e.g. Monday equals 0) for the current month
    private int numberOfDaysInMonth;//save the number of days in the current displayed month
    //
    private List<Ferias> currentMonthDisplayedHolidays;
    private List<RestricaoFerias> currentMonthDisplayedRestrictions;
    //
    private boolean showPersonalHolidays = false;
    private boolean showEmployeesHolidays = false;
    private boolean showHolidays = false;
    //
    private Image personalHolidaysImage = new Image("/cococay_final/Assets/holiday_green.png");
    private Image personalHolidaysWaitingImage = new Image("/cococay_final/Assets/waiting_yellow.png");
    private Image employeesHolidaysImageYellow = new Image("/cococay_final/Assets/holiday_yellow.png");//For employees not in the same team
    private Image employeesHolidaysImageRed = new Image("/cococay_final/Assets/holiday_red.png");//For employees in the same team
    private Image holidaysImage = new Image("/cococay_final/Assets/holiday_yellow.png");
    private Image restrictionsImage = new Image("/cococay_final/Assets/cancel_red.png");
    //
    @FXML GridPane gridContainerOfDayElements;
    private List<FXMLCalendarDayElementController> daysElementsList;
    //Month buttons id's
    private Button previousClickedButton = null;
    @FXML Button btnMonth0;
    @FXML Button btnMonth1;
    @FXML Button btnMonth2;
    @FXML Button btnMonth3;
    @FXML Button btnMonth4;
    @FXML Button btnMonth5;
    @FXML Button btnMonth6;
    @FXML Button btnMonth7;
    @FXML Button btnMonth8;
    @FXML Button btnMonth9;
    @FXML Button btnMonth10;
    @FXML Button btnMonth11;
    @FXML Button btnPreviousYear;
    @FXML Button btnNextYear;
    
    //
    //restrictions necessary for the new request window
    private boolean showRestrictions = false;
    private boolean showDptHolidays = false;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.daysElementsList = new ArrayList<>();
        //
        int col = 0, row = 0;
        for(int i = 0; i < 42; i++){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCalendarDayElement.fxml"));
            Parent parent = null;
            try {
                parent = loader.load();
            } catch (IOException e) { e.printStackTrace(); }
            FXMLCalendarDayElementController controller = loader.getController();
            //controller.setDay(Integer.toString(i+1), false);
            this.gridContainerOfDayElements.add(parent, col, row);
            this.daysElementsList.add(controller);
            if(col < 6){
                col++;
            }else{
                col = 0;
                row++;
            }
        }
        //Define current dateString settings
        Calendar dt = Calendar.getInstance();
	System.out.println(Calendar.getInstance().get(Calendar.MONTH));
        this.currentYearDisplayed = Calendar.getInstance().get(Calendar.YEAR);
        this.currentMonthDisplayed = Calendar.getInstance().get(Calendar.MONTH);
        System.out.println("Current Year: " + this.currentYearDisplayed);
        this.setCurrrentDisplayedMonthYearElements();
        this.updateDayElements();
        this.changeSelectedMonthBtnStyle();
        DateFormat dtf = new SimpleDateFormat("yy");
        String year = dtf.format(Calendar.getInstance().getTime());
        this.btnPreviousYear.setText("'" + (Integer.parseInt(year) -1));
        this.btnNextYear.setText("'" + (Integer.parseInt(year) +1));
    }
    //Update calendar
    public void updateCalendar(){
        this.updateDayElements();
    }
    //Set style for current month displayed
    private void changeSelectedMonthBtnStyle(){
        if(this.previousClickedButton != null){
            this.previousClickedButton.setStyle("/cococay_final/cococay_style.css");
            this.previousClickedButton.getStyleClass().add("button-barra");
        }
        switch(this.currentMonthDisplayed){
            case 0: this.btnMonth0.setStyle("-fx-focus-color: #ff6b6b; -fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-border-color: #ff6b6b;");
                this.previousClickedButton = this.btnMonth0;
                break;
            case 1: this.btnMonth1.setStyle("-fx-focus-color: #ff6b6b; -fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-border-color: #ff6b6b;");
                this.previousClickedButton = this.btnMonth1;
                break;
            case 2: this.btnMonth2.setStyle("-fx-focus-color: #ff6b6b; -fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-border-color: #ff6b6b;");
                this.previousClickedButton = this.btnMonth2;
                break;
            case 3: this.btnMonth3.setStyle("-fx-focus-color: #ff6b6b; -fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-border-color: #ff6b6b;");
                this.previousClickedButton = this.btnMonth3;
                break;
            case 4: this.btnMonth4.setStyle("-fx-focus-color: #ff6b6b; -fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-border-color: #ff6b6b;");
                this.previousClickedButton = this.btnMonth4;
                break;
            case 5: this.btnMonth5.setStyle("-fx-focus-color: #ff6b6b; -fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-border-color: #ff6b6b;");
                this.previousClickedButton = this.btnMonth5;
                break;
            case 6: this.btnMonth6.setStyle("-fx-focus-color: #ff6b6b; -fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-border-color: #ff6b6b;");
                this.previousClickedButton = this.btnMonth6;
                break;
            case 7: this.btnMonth7.setStyle("-fx-focus-color: #ff6b6b; -fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-border-color: #ff6b6b;");
                this.previousClickedButton = this.btnMonth7;
                break;
            case 8: this.btnMonth8.setStyle("-fx-focus-color: #ff6b6b; -fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-border-color: #ff6b6b;");
                this.previousClickedButton = this.btnMonth8;
                break;
            case 9: this.btnMonth9.setStyle("-fx-focus-color: #ff6b6b; -fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-border-color: #ff6b6b;");
                this.previousClickedButton = this.btnMonth9;
                break;
            case 10: this.btnMonth10.setStyle("-fx-focus-color: #ff6b6b; -fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-border-color: #ff6b6b;");
                this.previousClickedButton = this.btnMonth10;
                break;
            case 11: this.btnMonth11.setStyle("-fx-focus-color: #ff6b6b; -fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-border-color: #ff6b6b;");
                this.previousClickedButton = this.btnMonth11;
                break;
        }
    }
    //
    private void setCurrrentDisplayedMonthYearElements(){
        Calendar dt = Calendar.getInstance();
        if(this.currentMonthDisplayed == dt.get(Calendar.MONTH) && this.currentYearDisplayed == dt.get(Calendar.YEAR))
            this.currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        else
            this.currentDay = -1;
        dt.set(Calendar.YEAR, this.currentYearDisplayed);
        dt.set(Calendar.MONTH, this.currentMonthDisplayed);
        //
        this.numberOfDaysInMonth = dt.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("\tMax days: " + dt.getActualMaximum(Calendar.DAY_OF_MONTH));
        
        dt.set(Calendar.DAY_OF_MONTH, 1);
        this.firstDayWeekOfCurrentMonth = dt.get(Calendar.DAY_OF_WEEK);
        System.out.println("Weekday of this month: " + this.firstDayWeekOfCurrentMonth);
    }
    //
    public void updateDayElements(){
        this.currentMonthDisplayedHolidays = null;
        this.currentMonthDisplayedRestrictions = null;
        this.currentMonthDisplayedRestrictions = Repository.getSingleton().getRestrictions(this.currentMonthDisplayed + 1, this.currentYearDisplayed);
        this.currentMonthDisplayedHolidays = Repository.getSingleton().getCurrentMonthHolidaysEmployees(this.currentMonthDisplayed + 1, this.currentYearDisplayed);
        this.cleanDays();
        FXMLCalendarDayElementController controller;
        for(int i = 0; i < this.numberOfDaysInMonth; i++){
            controller = this.daysElementsList.get(this.firstDayWeekOfCurrentMonth - 1 + i);
            controller.setDay(Integer.toString(i + 1), ((this.currentDay != -1 && this.currentDay == (i + 1))));
        }  
        this.updateHolidaysIcons();
    }
    //Change calendar showed.... show Personal Holidays as priority
    public void updateHolidaysIcons(){
        FXMLCalendarDayElementController controller;
        boolean dontDestroyEmployeesHolidays;
        boolean dontDestroyHolidays;
        DateFormat dtf = new SimpleDateFormat("d/M/yyyy");
        Date date = null;
        String dateString;
        List<String> holidayTooltip = new ArrayList<>();
        if(this.currentMonthDisplayedHolidays != null || this.currentMonthDisplayedRestrictions != null){
            for(int i = 0; i < this.numberOfDaysInMonth; i++){
                //
                holidayTooltip.clear();
                controller = this.daysElementsList.get(this.firstDayWeekOfCurrentMonth - 1 + i);
                dateString = ((i + 1) + "/" + (this.currentMonthDisplayed + 1) + "/" + this.currentYearDisplayed);
                System.out.println("Date to be checked: " + dateString);
                try {
                    date = dtf.parse(dateString);
                } catch (ParseException ex) { System.out.println("DATE FORMAT ERROR!");}
                controller.setDayIcon(null);
                if(this.currentMonthDisplayedHolidays != null)
                    search: {
                        for(Ferias f: this.currentMonthDisplayedHolidays){
                            if(f.getDataInicio().compareTo(date) <= 0 && f.getDataFim().compareTo(date) >=0){
                                //Show personal holiday
                                if(this.showPersonalHolidays){
                                    if (Objects.equals(Repository.getSingleton().getLoggedEmployee().getIdFuncionario(), f.getIdFuncionario().getIdFuncionario()) && f.getAprovado() && f.getVerificadoRh()) {
                                        controller.setDayIcon(this.personalHolidaysImage);
                                        break search;
                                    }else if(Objects.equals(Repository.getSingleton().getLoggedEmployee().getIdFuncionario(), f.getIdFuncionario().getIdFuncionario()) && !f.getAprovado() && !f.getVerificadoRh()){
                                        //Show loading image
                                        controller.setDayIcon(this.personalHolidaysWaitingImage);
                                        break search;
                                    }
                                }
                                }  
                        }
                        for(Ferias f: this.currentMonthDisplayedHolidays){
                            if(f.getDataInicio().compareTo(date) <= 0 && f.getDataFim().compareTo(date) >=0){
                                //Show employees holidays if the personal check box is disabled
                                if(this.showEmployeesHolidays || this.showDptHolidays){
                                    if(f.getAprovado() && f.getVerificadoRh())//
                                        if(Repository.getSingleton().isFromSameDepartment(f.getIdFuncionario().getIdFuncionario())){// 
                                            controller.setDayIcon(this.employeesHolidaysImageYellow);
                                            break search;
                                        }else{
                                            if(!this.showDptHolidays){
                                                controller.setDayIcon(this.employeesHolidaysImageRed);
                                                break search;
                                            }
                                        }
                                    }
                            }   
                        }    
                    }
                if(this.showRestrictions && this.currentMonthDisplayedRestrictions != null){
                    search2:{
                        for(RestricaoFerias r: this.currentMonthDisplayedRestrictions){
                            if(r.getDataInicio().compareTo(date) <= 0 && r.getDataFim().compareTo(date) >= 0){
                                controller.setDayIcon(this.restrictionsImage);
                                break search2;
                            }
                        }
                    }
                }
                //Define tooltip for the current day being checked
                /*
                String temp = "";
                for(Ferias f: this.currentMonthDisplayedHolidays){
                    if(f.getDataInicio().compareTo(date) <= 0 && f.getDataFim().compareTo(date) >=0){
                        temp = Repository.getSingleton().getNameAndDepartment(f.getIdFuncionario().getIdFuncionario());
                        if(!holidayTooltip.contains(temp)){
                            holidayTooltip.add(temp);
                        }
                    }
                    
                    controller.setDayTooltip(holidayTooltip);
                    //Show holiday ...just change the style of the day label
                    if(this.showHolidays){

                    }
                }*/
            }
        }
    }
    //delete all text and icons in day elements for next update
    private void cleanDays(){
        this.daysElementsList.forEach((ctn) -> {
            ctn.setDay("", false);
            //Add clean to icons after methods being created
            ctn.setDayIcon(null);
            ctn.cleanTooltip();
        });
    }
    //Setters
    public void setShowRestrictions(boolean showRestrictions) {
        this.showRestrictions = showRestrictions;
    }
    public void setShowPersonalHolidays(boolean showPersonalHolidays) {
        this.showPersonalHolidays = showPersonalHolidays;
        this.updateHolidaysIcons();
    }
    public void setShowEmployeesHolidays(boolean showEmployeesHolidays) {
        this.showEmployeesHolidays = showEmployeesHolidays;
        this.updateHolidaysIcons();
    }
    public void setShowHolidays(boolean showHolidays) {
        this.showHolidays = showHolidays;
        this.updateHolidaysIcons();
    }
    //Click listeners
    @FXML public void btnPrevYearClicked(ActionEvent event){
        this.currentYearDisplayed--;
        this.btnNextYear.setText("'"+ (Integer.parseInt(Integer.toString(this.currentYearDisplayed).substring(2, 4)) + 1));
        this.btnPreviousYear.setText("'"+ (Integer.parseInt(Integer.toString(this.currentYearDisplayed).substring(2, 4)) - 1));
        this.setCurrrentDisplayedMonthYearElements();
        this.updateDayElements();
    }
    @FXML public void btnNextYearClicked(ActionEvent event){
        this.currentYearDisplayed++;
        this.btnNextYear.setText("'"+ (Integer.parseInt(Integer.toString(this.currentYearDisplayed).substring(2, 4)) + 1));
        this.btnPreviousYear.setText("'"+ (Integer.parseInt(Integer.toString(this.currentYearDisplayed).substring(2, 4)) - 1));
        this.setCurrrentDisplayedMonthYearElements();
        this.updateDayElements();
    }
    @FXML public void btnMonth1Clicked(ActionEvent event){
        this.currentMonthDisplayed = 0;
        this.setCurrrentDisplayedMonthYearElements();
        this.updateDayElements();
        this.changeSelectedMonthBtnStyle();
    }
    @FXML public void btnMonth2Clicked(ActionEvent event){
        this.currentMonthDisplayed = 1;
        this.setCurrrentDisplayedMonthYearElements();
        this.updateDayElements();
        this.changeSelectedMonthBtnStyle();
    }
    @FXML public void btnMonth3Clicked(ActionEvent event){
        this.currentMonthDisplayed = 2;
        this.setCurrrentDisplayedMonthYearElements();
        this.updateDayElements();
        this.changeSelectedMonthBtnStyle();
    }
    @FXML public void btnMonth4Clicked(ActionEvent event){
        this.currentMonthDisplayed = 3;
        this.setCurrrentDisplayedMonthYearElements();
        this.updateDayElements();
        this.changeSelectedMonthBtnStyle();
    }
    @FXML public void btnMonth5Clicked(ActionEvent event){
        this.currentMonthDisplayed = 4;
        this.setCurrrentDisplayedMonthYearElements();
        this.updateDayElements();
        this.changeSelectedMonthBtnStyle();
    }
    @FXML public void btnMonth6Clicked(ActionEvent event){
        this.currentMonthDisplayed = 5;
        this.setCurrrentDisplayedMonthYearElements();
        this.updateDayElements();
        this.changeSelectedMonthBtnStyle();
    }
    @FXML public void btnMonth7Clicked(ActionEvent event){
        this.currentMonthDisplayed = 6;
        System.out.println("1");
        this.setCurrrentDisplayedMonthYearElements();
        System.out.println("2");
        this.updateDayElements();
        System.out.println("3");
        this.changeSelectedMonthBtnStyle();
        System.out.println("4");
    }
    @FXML public void btnMonth8Clicked(ActionEvent event){
        this.currentMonthDisplayed = 7;
        this.setCurrrentDisplayedMonthYearElements();
        this.updateDayElements();
        this.changeSelectedMonthBtnStyle();
    }
    @FXML public void btnMonth9Clicked(ActionEvent event){
        this.currentMonthDisplayed = 8;
        this.setCurrrentDisplayedMonthYearElements();
        this.updateDayElements();
        this.changeSelectedMonthBtnStyle();
    }
    @FXML public void btnMonth10Clicked(ActionEvent event){
        this.currentMonthDisplayed = 9;
        this.setCurrrentDisplayedMonthYearElements();
        this.updateDayElements();
        this.changeSelectedMonthBtnStyle();
    }
    @FXML public void btnMonth11Clicked(ActionEvent event){
        this.currentMonthDisplayed = 10;
        this.setCurrrentDisplayedMonthYearElements();
        this.updateDayElements();
        this.changeSelectedMonthBtnStyle();
    }
    @FXML public void btnMonth12Clicked(ActionEvent event){
        this.currentMonthDisplayed = 11;
        this.setCurrrentDisplayedMonthYearElements();
        this.updateDayElements();
        this.changeSelectedMonthBtnStyle();
    }

    public List<RestricaoFerias> getCurrentMonthDisplayedRestrictions() {
        return currentMonthDisplayedRestrictions;
    }

    public void setShowDptHolidays(boolean showDptHolidays) {
        this.showDptHolidays = showDptHolidays;
    }
    
    
}
