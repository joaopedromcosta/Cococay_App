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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLHomeScreenRHController implements Initializable {
    @FXML MenuBar menuBar;
    
    @FXML VBox optionsVBox;
    
    @FXML Label lblEmployeeName;
    @FXML Label lblEmployeeDpt;
    @FXML Label lblNextHolidays;
    //Program Options Buttons
    @FXML Button btnLogOut, btnHomeScreen, btnNotifications;
    @FXML Button btnCalendar;
    @FXML Button btnCalendarRequest;
    @FXML Button btnValidate;
    @FXML Button btnAddRestriction;
    @FXML Button btnNewGoal;
    @FXML Button btnEmployees;
    @FXML Button btnTeams;
    @FXML Button btnRecordAbsence;
    @FXML Button btnListData;
    
    
    @FXML BorderPane optionsContainerForHomeScreen;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Set labels regarding the user logged name and department 
        this.lblEmployeeName.setText(Repository.getSingleton().getLoggedEmployee().getNome());
        this.lblEmployeeDpt.setText(Repository.getSingleton().getDepartmentLoggedUser(Repository.getSingleton().getLoggedEmployee().getIdFuncionario()) + " Dpt.");
        this.lblNextHolidays.setText("Next Holidays: " + Repository.getSingleton().getNextHolidays());
        //
        if(Repository.getSingleton().getDepartmentLoggedUser(Repository.getSingleton().getLoggedEmployee().getIdFuncionario()).equals("Human Resources")){//If human resources logged show all option in the main menu
            //Tooltips for all the options buttons
            this.btnNotifications.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/alerts.png"))));
            this.btnNotifications.setTooltip(new Tooltip("Notification Center"));
            this.btnLogOut.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/logout_icon.png"))));
            this.btnLogOut.setTooltip(new Tooltip("Log out"));
            this.btnHomeScreen.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/home.png"))));
            this.btnHomeScreen.setTooltip(new Tooltip("Go to Home Screen"));
            this.btnCalendar.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/calendar.png"))));
            this.btnCalendar.setTooltip(new Tooltip("Open Calendar"));
            this.btnCalendarRequest.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/calendar_plus.png"))));
            this.btnCalendarRequest.setTooltip(new Tooltip("New Request"));
            this.btnValidate.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/inspection.png"))));
            this.btnValidate.setTooltip(new Tooltip("Validate Requests"));
            this.btnAddRestriction.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/cancel.png"))));
            this.btnAddRestriction.setTooltip(new Tooltip("Add Restriction"));
            //this.btnNewGoal.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/goal.png"))));
            //this.btnNewGoal.setTooltip(new Tooltip("New Goal"));
            this.btnEmployees.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/add_user.png"))));
            this.btnEmployees.setTooltip(new Tooltip("Employees"));
            this.btnTeams.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/add_user_group.png"))));
            this.btnTeams.setTooltip(new Tooltip("Departments"));
            this.btnRecordAbsence.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/calendar_delete.png"))));
            this.btnRecordAbsence.setTooltip(new Tooltip("Record Absence"));
            this.btnListData.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/list.png"))));
            this.btnListData.setTooltip(new Tooltip("List Data"));
        }else{//If not human resources logged show only the home, logout, calendar and new request buttons in the main menu
            this.btnLogOut.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/logout_icon.png"))));
            this.btnLogOut.setTooltip(new Tooltip("Log out"));
            this.btnHomeScreen.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/home.png"))));
            this.btnHomeScreen.setTooltip(new Tooltip("Go to Home Screen"));
            this.btnCalendar.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/calendar.png"))));
            this.btnCalendar.setTooltip(new Tooltip("Open Calendar"));
            this.btnCalendarRequest.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/calendar_plus.png"))));
            this.btnCalendarRequest.setTooltip(new Tooltip("New Request"));
            this.btnNotifications.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cococay_final/Assets/alerts.png"))));
            this.btnNotifications.setTooltip(new Tooltip("Notification Center"));
            this.btnValidate.setVisible(false);
            this.btnAddRestriction.setVisible(false);
            this.btnEmployees.setVisible(false);
            this.btnTeams.setVisible(false);
            this.btnListData.setVisible(false);
            
        }
        this.btnNewGoal.setVisible(false);
        //this.btnRecordAbsence.setVisible(false);
        //Decrease vbox size in order to hide scroll bar
        this.optionsVBox.setMaxHeight(410);
        this.btnNotifications.setText(String.valueOf(Repository.getSingleton().getNumberNotifications()));
        this.menuBar.setVisible(false);
    }  
    //Go back to login menu method when btnLogOut is clicked
    public void setBackHandler(IBackEvent event){
        this.btnLogOut.setOnAction(e -> event.goBack(e));
    }
    private void ChangePane(URL u){
        FXMLLoader loader = new FXMLLoader(u);
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.optionsContainerForHomeScreen.setVisible(true);
        this.optionsContainerForHomeScreen.setCenter(parent);
    }
    //Calendar 
    @FXML
    public void btnCalendarClicked(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(Repository.getAppName() + " - Calendar");
        this.ChangePane(getClass().getResource("FXMLCalendarOptions.fxml"));
    }
    //New Request
    @FXML
    public void btnNewRequestClicked(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(Repository.getAppName() + " - New Holiday Request");
        this.ChangePane(getClass().getResource("FXMLNewRequest.fxml"));
    }
    //Validate requests
    @FXML
    public void btnValidateClicked(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(Repository.getAppName() + " - Validate Requests");
        this.ChangePane(getClass().getResource("FXMLValidateRequests.fxml"));
    }
    //Restrictions 
    @FXML
    public void btnAddRestrictionsClicked(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(Repository.getAppName() + " - Restrictions");
        this.ChangePane(getClass().getResource("FXMLRestrictionsListOptions.fxml"));
    }
    //Add new goal
    @FXML
    public void btnNewGoalClicked(ActionEvent event){
        
    }
    //Employees
    @FXML
    public void btnEmployeesClicked(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(Repository.getAppName() + " - Employees");
        this.ChangePane(getClass().getResource("FXMLEmployeesListOptions.fxml"));
    }
    //Departments
    @FXML
    public void btnDepartmentsClicked(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(Repository.getAppName() + " - Departments");
        this.ChangePane(getClass().getResource("FXMLDepartments.fxml"));
    }
    //Record absence
    @FXML
    public void btnRecordAbsenceClicked(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(Repository.getAppName() + " - Absences");
        this.ChangePane(getClass().getResource("FXMLAbsencesOptions.fxml"));
    }
    //List Data
    @FXML
    public void btnListDataClicked(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(Repository.getAppName() + " - List Data");
        this.ChangePane(getClass().getResource("FXMLListData.fxml"));
    }
    
    //Go to home screen wheen btnHomeScreen is clicked
    public void goToHomeScreenBtnClicked(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(Repository.getAppName() + " - HomeScreen");
        System.out.println("Go to home screen");
        this.btnNotifications.setText(String.valueOf(Repository.getSingleton().getNumberNotifications()));
        //Eventually replace with a home screen with any content that may matter to the company
        //this.optionsContainerForHomeScreen.setVisible(false);
        this.optionsContainerForHomeScreen.setCenter(null);
    }
    //
    public void goToNotificationsCenter(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(Repository.getAppName() + " - Notifications Center");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLNotificationCenter.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.optionsContainerForHomeScreen.setVisible(true);
        this.optionsContainerForHomeScreen.setCenter(parent);
    }
}
