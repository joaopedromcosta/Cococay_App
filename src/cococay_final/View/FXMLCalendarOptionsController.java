/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLCalendarOptionsController implements Initializable {

    @FXML BorderPane calendarOptionsContainer;
    //
    @FXML CheckBox checkBoxPersonalHolidays;
    @FXML CheckBox checkBoxEmployeesHolidays;
    @FXML CheckBox checkBoxHolidays;//Feriados <- remover nota
    @FXML Button btnNewRequest;
    
    //In order to access the calendar methods we must save a pointer to the created controller
    private FXMLCalendarController calendarController;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCalendar.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.calendarController = loader.getController();
        this.calendarController.setShowPersonalHolidays(true);
        this.checkBoxPersonalHolidays.setSelected(true);
        this.calendarOptionsContainer.setTop(parent);
    }    
    
    //Method called when personal holidays check box is pressed -> change of state
    public void personalHolidaysCheckBoxStateChanged(ActionEvent event){
        System.out.println("Personal Holidays Check Box pressed");
        this.calendarController.setShowPersonalHolidays(this.checkBoxPersonalHolidays.isSelected());
    }
    //Method called when employees holidays check box is pressed -> change of state
    public void employeesHolidaysCheckBoxStateChanged(ActionEvent event){
        System.out.println("Employees Holidays Check Box pressed");
        this.calendarController.setShowEmployeesHolidays(this.checkBoxEmployeesHolidays.isSelected());
    }
    //Method called when holidays check box is pressed -> change of state
    public void holidaysCheckBoxStateChanged(ActionEvent event){
        System.out.println("Holidays Check Box pressed");
        this.calendarController.setShowHolidays(this.checkBoxHolidays.isSelected());
    }
    
    //New request button pressed
    public void newRequestButtonClicked(ActionEvent event){
        System.out.println("New Request button pressed");
    }
    
    
}
