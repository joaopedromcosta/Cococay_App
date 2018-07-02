/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Model.Funcionario;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLCalendarDayElementController implements Initializable {

    @FXML Label lblDayNumber;
    @FXML ImageView imgDayCondition;
    
    private String day;
    private List<Funcionario> employees;
    
    //
    Tooltip t;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("Day element created successufuly!");
    }

    //Define the day number showed in the label
    public void setDay(String day, boolean presentDay){//Present day defines if this day element is the current day in the calendar
        this.day = day;
        System.out.println("\tSet Day Number in day element! " + day);
        this.lblDayNumber.setText(this.day);
        if(presentDay)
            this.lblDayNumber.setStyle("-fx-background-color: #ff6b6b; -fx-border-color: #ff6b6b; -fx-border-radius:1px; -fx-text-fill: white;");
        else
            this.lblDayNumber.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-radius:1px; -fx-text-fill: black;");
        
    }
    
    //Define the image in the day element
    public void setDayIcon(Image image){
        this.imgDayCondition.setImage(image);
    }
    
    //Define a tooltip for a day that has someone with a schedule holiday
    public void setDayTooltip(List<String> listHoliday){
        if(listHoliday.isEmpty()){
            Tooltip.uninstall(this.imgDayCondition, this.t);
            return;
        }
        System.out.println(listHoliday);
        String temp = "";
        for(String x : listHoliday){
            temp += x;
        }
        this.t = new Tooltip(temp);
        Tooltip.install(this.imgDayCondition, this.t);
    }
    
    //clean tooltip
    public void cleanTooltip(){
        Tooltip.uninstall(this.imgDayCondition, this.t);
        this.t = null;
    }
    
    
}
