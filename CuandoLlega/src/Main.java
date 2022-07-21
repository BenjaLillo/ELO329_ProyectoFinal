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
        BusStop[] busStops = controlCenter.initializeBusStops(busStopsRead); // Inicializa los paraderos

        controlCenter.writeRealTimeData(buses); // Escribe el archivo RealTimeData.csv con los datos iniciales de los buses

        buses = controlCenter.simulateBuses(buses); // Simula aplicando valores aleatorios para los buses

        controlCenter.writeRealTimeData(buses); //Ahora escribe el archivo RealTimeData.csv con los datos simulados de los buses

        controlCenter.nextBus(buses, busStops,101, 3,"North"); //Imprime por consola el
        // tiempo estimado de llegada del bus especificado en el paradero indicado

        controlCenter.nextBus(buses, busStops,101, 3,"South"); //Imprime por consola el
        // tiempo estimado de llegada del bus especificado en el paradero indicado
    }
}
