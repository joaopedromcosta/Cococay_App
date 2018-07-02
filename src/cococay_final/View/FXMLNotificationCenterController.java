/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.View;

import cococay_final.Repository;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author joaocosta-ipvc
 */
public class FXMLNotificationCenterController implements Initializable {

    @FXML ListView listViewNotificationsList;
    private ObservableList<String> notificationsObservableList;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        List<String> notificationsList = Repository.getSingleton().getNotifications();
        if(notificationsList.isEmpty())
            notificationsList.add("There isn't any notification to show at this moment");
        this.notificationsObservableList =  FXCollections.observableArrayList(notificationsList);
        this.listViewNotificationsList.setItems(notificationsObservableList);
    }    

}
