/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class window1Controller {

    private Stage stage;
    @FXML
    private Button button1;

    @FXML
    private Button createTalonario;

    @FXML
    private Label label;
    private window1Controller controllerWindow1;
    private window2Controller controllerWindow2;

    @FXML
    void handleButtonAction(ActionEvent event) {

    }

    @FXML
    void showWindow2(ActionEvent event) throws IOException {

        try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/windows/window2.fxml"));
            Parent root = loader.load();
            window2Controller controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage .setScene(scene);
            controller.init(stage,this);
            stage.show();
           // this.stage.show();//se oculta la 1 y se muestra la window2
            
            // Se oculta la ventana actual
            this.stage.hide();
             controller.initialize(null, null);
        } catch (Exception e) {
            // Maneja la excepción aquí, por ejemplo, imprímela en la consola
            e.printStackTrace();
        }

    }

    public void setStage(Stage primaryStage) {

        stage = primaryStage;

    }
    public void init2(Stage stage, window2Controller window1Controller) {
        this.controllerWindow2 = window1Controller;
        this.stage = stage;

    }
  

}
