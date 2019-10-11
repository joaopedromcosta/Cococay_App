/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Model.Equipa;
import cococay_final.Model.Ferias;
import cococay_final.Model.HolidaysForList;
import cococay_final.Repository;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLHolidaysListController implements Initializable {

    //Borderpane
    @FXML BorderPane container;
    //Table view
    @FXML TableView<HolidaysForList> tableHolidays;
    @FXML TableColumn<HolidaysForList, String> columnBeginDate;
    @FXML TableColumn<HolidaysForList, String> columnEndDate;
    @FXML TableColumn<HolidaysForList, String> columnName;
    @FXML TableColumn<HolidaysForList, String> columnDepartments;
    
    private ObservableList<HolidaysForList> observableListHolidays;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.columnBeginDate.setCellValueFactory(new PropertyValueFactory<>("beginDate"));
        this.columnEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        this.columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.columnDepartments.setCellValueFactory(new PropertyValueFactory<>("departments"));
        
        this.observableListHolidays =  FXCollections.observableArrayList(Repository.getSingleton().getAllHolidays());
        this.tableHolidays.setItems(observableListHolidays);
    }    
    
}
