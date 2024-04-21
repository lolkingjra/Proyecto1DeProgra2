package controller;


import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import javafx.stage.Stage;


public class window3Controller implements Initializable {

    @FXML
    private Button Disponibles;

    @FXML
    private Button Pagados;

    @FXML
    private Button Reservados;

    @FXML
    private GridPane gridpane;
    private Stage stage;
    private window2Controller controllerWindow2;

    @FXML
    void seeDisponibles(ActionEvent event) {

    }

    @FXML
    void seePagados(ActionEvent event) {

    }

    @FXML
    void seeReservados(ActionEvent event) {

    }

    public void init2(Stage stage, window2Controller window1Controller) {
        this.controllerWindow2 = window1Controller;
        this.stage = stage;

    }

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Agregar aca el metodo funcional de llenarGridPane
    }
}
