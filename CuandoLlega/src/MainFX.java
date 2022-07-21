// imports javafx
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Hashtable;

public class MainFX extends Application {
    ControlCenter controlCenter;
    int[][] busStockRead;
    Bus[] buses;
    Hashtable<Integer, String[]> busStopsRead;
    BusStop[] busStops;

    public static void main(String[] args) {
        launch(args);
    }

    // Start method javaFX. It includes 4 buttons in a scene with a grid layout.

    public void start(Stage primaryStage) throws IOException {
        controlCenter = new ControlCenter();  //Crea un objeto de la clase ControlCenter

        busStockRead = controlCenter.readBusStock(); // Lee el archivo BusStock.csv y lo guarda en una matriz de enteros
        buses = controlCenter.initializeBuses(busStockRead); // Inicializa los buses con los datos de la matriz busStockRead

        busStopsRead = controlCenter.readBusStops();   // Lee el archivo BusStops.csv y lo guarda
        busStops = controlCenter.initializeBusStops(busStopsRead); // Inicializa los paraderos

        controlCenter.writeRealTimeData(buses);

        //buses = controlCenter.simulateBuses(buses);
        //controlCenter.writeRealTimeData(buses);
        //controlCenter.nextBus(buses, busStops,101, 6,"North");
        //controlCenter.nextBus(buses, busStops,101, 6,"South");

        // Creates a basic scene which contains 4 clickable buttons.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(25, 25, 25, 25));
        Button button1 = new Button("Simular buses");
        Button button2 = new Button("Datos a CSV");
        Button button3 = new Button("Siguiente bus");
        TextField tf1 = new TextField();
        TextField tf2 = new TextField();
        TextField tf3 = new TextField();
        Label t1 = new Label("Inserte recorrido:");
        Label t2 = new Label("Inserte ID de paradero:");
        Label t3 = new Label("Inserte dirección:");
        Label resultado = new Label("[...]");
        grid.add(button1, 0, 0);
        grid.add(button2, 1, 0);
        grid.add(button3, 0, 5);
        grid.add(tf1, 1, 2);
        grid.add(tf2, 1, 3);
        grid.add(tf3, 1, 4);
        grid.add(t1, 0, 2);
        grid.add(t2, 0, 3);
        grid.add(t3, 0, 4);
        grid.add(resultado, 0, 6);
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setTitle("CuandoLlega");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Change scene size
        primaryStage.setWidth(400);
        primaryStage.setHeight(280);


        // The first button calls the method simulateBuses() from the class ControlCenter.
        button1.setOnAction(e -> {
            buses = controlCenter.simulateBuses(buses);
            try {
                controlCenter.writeRealTimeData(buses);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        button2.setOnAction(e -> {
            try {
                controlCenter.writeRealTimeData(buses);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        //The text fields are used to get the bus number and bus stop ID.
        button3.setOnAction(e -> {
            float res = 0;
            int busNumber = Integer.parseInt(tf1.getText());
            int busStopID = Integer.parseInt(tf2.getText());
            String direction = tf3.getText();
            try {
                res = controlCenter.nextBus(buses, busStops, busNumber, busStopID, direction);
                resultado.setText("El siguiente bus llegará apartamente en: " + res + " minutos");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
