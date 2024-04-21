/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import BD.DataBaseConnect;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javafx.scene.control.DatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import oracle.jdbc.OracleTypes;

public class window2Controller {

    @FXML
    private Button eliminar;
    @FXML
    private TextField comprador;
    @FXML
    private TextField compra;
    @FXML
    private GridPane gridpane;
    @FXML
    private Button addRifa;
    @FXML
    private TextField cantNumeros;
    @FXML
    private TextField metPago;
    @FXML
    private DatePicker date;

    @FXML
    private TextField description;
    @FXML
    private TextField prize;
    @FXML
    private TextField valueNumber;
    @FXML
    private MenuButton mostrarRifa;
    @FXML
    private Label rifa;
    @FXML
    private TextField nameOwner;

    @FXML
    private Button numGanador;

    @FXML
    private Label nameGanador;

    private window1Controller controllerWindow1;
    private window2Controller controllerWindow2;
    private window3Controller controllerWindow3;
    private Stage stage;//ventana que se visualiza

    @FXML

    public void init(Stage stage, window1Controller window1Controller) {
        this.controllerWindow1 = window1Controller;
        this.stage = stage;

    }

    @FXML
    void seeWindow3(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Windows/window3.fxml"));
            Parent root = loader.load();
            window3Controller controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();//
            stage.setScene(scene);

            controller.init2(stage, this);
            stage.show();
            // Se oculta la ventana actual
            this.stage.hide();
        } catch (Exception e) {
            // Maneja la excepción aquí, por ejemplo, imprímela en la consola
            e.printStackTrace();
        }

    }

    @FXML
    private void returnWindow1(ActionEvent event) throws SQLException {
        try (Connection conn = DataBaseConnect.getConnection()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Windows/window1.fxml"));
                Parent root = loader.load();
                window1Controller controller = loader.getController();
                Scene scene = new Scene(root);
                Stage stage = new Stage();//
                stage.setScene(scene);
                controller.init2(stage, this);
                stage.show();
                System.out.println("Conectado");
                // Se oculta la ventana actual
                this.stage.hide();
            } catch (Exception e) {
                // Maneja la excepción aquí, por ejemplo, imprímela en la consola
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void agregarRifas(ActionEvent event) {
                llenarMenuRifas();
        try (Connection conn = DataBaseConnect.getConnection()) {
            try (CallableStatement agregarRifa = conn.prepareCall("{call insertar_rifa(?, ?, ?, ?, ?, ?, ?)}")) {//callablestatmente es para  llamame a esta funcion y la ejecute.   El call insertar rifa lo que hizo es llamar una funcion de la BD en sql

                System.out.println("Nombre propietario: " + nameOwner.getText());
                System.out.println("Premio: " + prize.getText());
                int cantidadNumeros = cantNumeros.getText().isEmpty() ? 0 : Integer.parseInt(cantNumeros.getText());
                System.out.println("Cantidad de números: " + cantidadNumeros);
                System.out.println("Fecha: " + date.getValue().toString());
                System.out.println("Descripción: " + description.getText());
                int valorNumero = valueNumber.getText().isEmpty() ? 0 : Integer.parseInt(valueNumber.getText());
                System.out.println("Valor del número: " + valorNumero);
                System.out.println("Método de pago: " + metPago.getText());

                agregarRifa.setString(1, nameOwner.getText());
                agregarRifa.setString(2, prize.getText());
                //   int cantidadNumeros = cantNumeros.getText().isEmpty() ? 0 : Integer.parseInt(cantNumeros.getText());
                agregarRifa.setInt(3, cantidadNumeros);
                agregarRifa.setString(4, date.getValue().toString());
                agregarRifa.setString(5, description.getText());
                //  int valorNumero = valueNumber.getText().isEmpty() ? 0 : Integer.parseInt(valueNumber.getText());
                agregarRifa.setInt(6, valorNumero);
                agregarRifa.setString(7, metPago.getText());
                agregarRifa.execute();

                System.out.println("se ejecuto el comando");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("comando tiburon");
        }
        nameOwner.clear();
        prize.clear();
        cantNumeros.clear();
        description.clear();
        valueNumber.clear();
        metPago.clear();
    }

    public void setStage(Stage primaryStage) {

        stage = primaryStage;

    }

    private void llenarMenuRifas() {
      
        try (Connection conn = DataBaseConnect.getConnection()) {
            try (CallableStatement mostrarrifas = conn.prepareCall("{call Obtener_Nombres_Rifas(?)}")) {
                mostrarrifas.registerOutParameter(1, OracleTypes.CURSOR);
                mostrarrifas.execute();
                // Obtener el cursor de resultados
                ResultSet rs = (ResultSet) mostrarrifas.getObject(1);

                // Iterar sobre el cursor de resultados para obtener los nombres de las rifas
                while (rs.next()) {
                    String nombreRifa = rs.getString("nombre");
                    MenuItem menuItem = new MenuItem(nombreRifa);
                    menuItem.setOnAction(this::MostrarRifas);
                    mostrarRifa.getItems().add(menuItem);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/*
    @FXML
private void handleMenuButtonClicked(ActionEvent event) {
           MenuItem menuItem = (MenuItem) event.getSource();

    MostrarRifas(MenuItem menuItem); // Llama a MostrarRifas cuando se haga clic en el MenuButton
}
    */
    @FXML
    private void MostrarRifas(ActionEvent event) {
         MenuItem menuItem = (MenuItem) event.getSource();
        try (Connection conn = DataBaseConnect.getConnection()) {
            try (CallableStatement numTotal = conn.prepareCall("{call Obtener_Numero_Total(?, ?)}")) {
                numTotal.setString(1, menuItem.getText());
                numTotal.registerOutParameter(2, Types.INTEGER); // Registro del parámetro de salida
                numTotal.execute();

                rifa.setText(menuItem.getText());
                List<Integer> numeros = new ArrayList<>();

                try (CallableStatement numerosComprados = conn.prepareCall("{call Numeros_Apartados_Comprados(?, ?)}")) {
                    numerosComprados.setString(1, rifa.getText());
                    numerosComprados.registerOutParameter(2, OracleTypes.CURSOR); // Registro del parámetro de salida
                    numerosComprados.execute();

                    // Obtener el cursor de resultados
                    ResultSet rs = (ResultSet) numerosComprados.getObject(2);

                    // Iterar sobre el cursor de resultados para obtener los números apartados o comprados
                    while (rs.next()) {
                        numeros.add(rs.getInt("numero_elegido"));
                        System.out.println("Número apartado o comprado: " + rs.getInt("numero_elegido"));
                    }
                    rs.close();
                    llenarGridPane(numTotal.getInt(2), numeros);

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void comprarNumero(ActionEvent event) {
        System.out.println("Método comprarNumero llamado.");
        if (!comprador.getText().isEmpty() && !compra.getText().isEmpty()) {
            if ("pagado".equals(compra.getText()) || "apartado".equals(compra.getText())) {
                Button button = (Button) event.getSource(); // Obtener el botón que disparó el evento
                try (Connection conn = DataBaseConnect.getConnection()) {
                    try (CallableStatement stmt = conn.prepareCall("{call Insertar_Persona(?)}")) {
                        stmt.setString(1, comprador.getText());
                        stmt.execute();
                    }
                    try (CallableStatement stmt = conn.prepareCall("{call Insertar_Numeros_Rifa(?, ?, ?, ?)}")) {
                        stmt.setInt(1, Integer.parseInt(button.getText()));
                        stmt.setString(2, rifa.getText());
                        stmt.setString(3, comprador.getText());
                        stmt.setString(4, compra.getText());
                        stmt.execute();
                        if ("pagado".equals(compra.getText())) {
                            button.setStyle("-fx-background-color: red");
                        }
                        if ("apartado".equals(compra.getText())) {
                            button.setStyle("-fx-background-color: yellow");
                        }
                        button.setDisable(true);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        comprador.clear();
        compra.clear();
    }

    public void llenarGridPane(int totalNumeros, List<Integer> numeros) {
        // Limpiar el GridPane por si tenía botones previamente
        gridpane.getChildren().clear();
        gridpane.getColumnConstraints().clear();
        gridpane.getRowConstraints().clear();

        // Ajustar el espaciado entre los nodos del GridPane
        gridpane.setHgap(10); // Espacio horizontal entre nodos
        gridpane.setVgap(10); // Espacio vertical entre nodos
        gridpane.setPadding(new Insets(10)); // Relleno exterior del GridPane

        // Establecer restricciones de crecimiento para las filas y columnas
        for (int i = 0; i < 10; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(100 / 10); // Cada columna ocupa el 10% del ancho disponible
            gridpane.getColumnConstraints().add(colConstraints);
        }

        int numRows = (totalNumeros + 9) / 10;
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100 / numRows); // Cada fila ocupa el 100% del alto disponible dividido por la cantidad de filas
            gridpane.getRowConstraints().add(rowConstraints);
        }

        // Llenar el GridPane con botones
        for (int i = 0; i < totalNumeros; i++) {
            int numero = i; // Número del botón
            Button button = new Button(String.valueOf(numero));
            button.setId(String.valueOf(numero));
            button.setPrefSize(50, 50); // Tamaño prefijado del botón
            button.setStyle(
                    "-fx-background-color: green; "
                    + "-fx-text-fill: white; "
                    + "-fx-border-color: darkgreen; "
                    + "-fx-border-width: 1px; "
                    + "-fx-border-radius: 2px; "
                    + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);"
            );
            button.setOnAction(this::comprarNumero);
            int columna = i % 10; // Columna en la que se debe agregar el botón
            int fila = i / 10; // Fila en la que se debe agregar el botón
            gridpane.add(button, columna, fila); // Agregar el botón al GridPane

            for (int n = 0; n < numeros.size(); n++) {
                if (numero == numeros.get(n)) {
                    try (Connection conn = DataBaseConnect.getConnection()) {
                        try (CallableStatement estado = conn.prepareCall("{call Estado_Numero_Rifa(?, ?, ?)}")) {
                            estado.setInt(1, n);
                            estado.setString(2, rifa.getText());
                            estado.registerOutParameter(3, Types.VARCHAR); // Registro del parámetro de salida
                            estado.execute();

                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        gridpane.requestLayout();
    }

    @FXML
    private void eliminarRifa(ActionEvent event) {

        try (Connection conn = DataBaseConnect.getConnection(); CallableStatement cstmt = conn.prepareCall("{call ELIMINAR_RIFA(?)}")) {

            cstmt.setString(1, rifa.getText());

            cstmt.execute();
            // String resultado = cstmt.getString(1);
            // System.out.println(resultado); 

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
    }

    @FXML
    private void obtenerGanador() {

        try {
            String RifaSeleccionada = rifa.getText();
            Connection conn = DataBaseConnect.getConnection();
            System.out.println("Ganador encontrado");
            CallableStatement cstntTotalNumber = conn.prepareCall("{call Obtener_Numero_Total(?,?)}");
            cstntTotalNumber.setString(1, RifaSeleccionada);
            cstntTotalNumber.registerOutParameter(2, Types.INTEGER);
            cstntTotalNumber.execute();
            int cantidadTotalNumeros = cstntTotalNumber.getInt(2);
            System.out.println("Ganador encontrado");
            Random random = new Random();
            int numeroGanador = random.nextInt(cantidadTotalNumeros) + 1;

            CallableStatement cstmt = conn.prepareCall("{call numeros_rifa(?,?)}");
            cstmt.setString(1, RifaSeleccionada);
            cstmt.registerOutParameter(2, OracleTypes.CURSOR);
            System.out.println("Ganador encontrado");
            CallableStatement cstmtNumerosApartadosComprados = conn.prepareCall("{call Numeros_Apartados_Comprados(?, ?)}");
            cstmtNumerosApartadosComprados.setString(1, RifaSeleccionada);
            cstmtNumerosApartadosComprados.registerOutParameter(2, OracleTypes.CURSOR);
            cstmtNumerosApartadosComprados.execute();
            ResultSet rs = (ResultSet) cstmtNumerosApartadosComprados.getObject(2);
            System.out.println("Ganador encontrado");
            System.out.println(numeroGanador);
            while (rs.next()) {
                if (numeroGanador == rs.getInt("numero_elegido")) {
                    //  mostrarResultado("Ganador", "Número: " + numeroGanador);
                    //  guardarGanadorEnBD(numeroGanador);
                    System.out.println("Ganador encontrado");
                    break;
                }
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    /*  private void mostrarResultado(String nombre, String numero) {
        nameGanador.setText(nombre);
        numGanador.setText(numero);
    }

    private void guardarGanadorEnBD(int numeroGanador) {
        try {
            Connection conn = DataBaseConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE RIFAS SET GANADOR = ? WHERE ID_RIFA = ?");
            stmt.setString(1, "Número: " + numeroGanador);
            int idRifaSeleccionada = Integer.parseInt(rifa.getText());
            stmt.setInt(2, idRifaSeleccionada);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     */
    private void init3(Stage stage, window3Controller controller3Window) {
        this.controllerWindow3 = controller3Window;
        this.stage = stage;
    }
    
    
    public void initialize(URL url, ResourceBundle rb
    ) {
        System.out.println("ME ESTOY LLAMANDO PRIMERO");
       llenarMenuRifas();
       // MostrarRifas(event);
    }
}
