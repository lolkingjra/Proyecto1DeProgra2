/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package proyecto1;

import BD.DataBaseConnect;
import java.sql.CallableStatement;
import controller.window1Controller;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ja215
 */
public class Proyecto1 extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/windows/window1.fxml"));//cargar vista con sus atributos
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        window1Controller controller = loader.getController();//el loader hace referencia a todos loas atributos de la scene de scene builder. Este controlador hace la conexion entre la vista y otros controladores
        controller.setStage(primaryStage);
        primaryStage.show();

    }

    public static void main(String[] args) throws SQLException {
        launch(args);
        
        try (Connection conn = DataBaseConnect.getConnection()) {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            insertarRifa(conn, "Nombre de la Rifa", "Premio de la Rifa", 100, new Date(), "Descripción de la Rifa");

            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
//intento probando solo el procedure
    public static void insertarRifa(Connection connection, String nombre, String premio, int totalNumeros, Date fecha, String descripcion) throws SQLException {
        // Crea un CallableStatement
        CallableStatement callableStatement = connection.prepareCall("{ call insertar_rifa(?, ?, ?, ?, ?) }");

      
        callableStatement.setString(1, nombre);
        callableStatement.setString(2, premio);
        callableStatement.setInt(3, totalNumeros);
        callableStatement.setDate(4, new java.sql.Date(fecha.getTime()));
        callableStatement.setString(5, descripcion);

        callableStatement.execute();
        callableStatement.close();
    }

}
