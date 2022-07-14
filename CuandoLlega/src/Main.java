import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Hashtable;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ControlCenter controlCenter = new ControlCenter();  //Crea un objeto de la clase ControlCenter
        int[][] busStockRead = controlCenter.readBusStock(); // Lee el archivo BusStock.csv y lo guarda en una matriz de enteros
        Bus[] buses = controlCenter.initializeBuses(busStockRead); // Inicializa los buses con los datos de la matriz busStockRead

        Hashtable<Integer, String[]> busStopsRead = controlCenter.readBusStops();   // Lee el archivo BusStops.csv y lo guarda
        BusStop[] busStops = controlCenter.initializeBusStops(busStopsRead); // Inicializa los busStops con los datos de la matriz busStopsRead


        controlCenter.writeRealTimeData(buses); // Escribe el archivo RealTimeData.csv con los datos de los buses

    }
}
