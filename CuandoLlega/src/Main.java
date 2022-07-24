import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ControlCenter controlCenter = new ControlCenter();  //Crea un objeto de la clase ControlCenter

        int[][] busStockRead = controlCenter.readBusStock(); // Lee el archivo BusStock.csv y lo guarda en una matriz de enteros
        Bus[] buses = controlCenter.initializeBuses(busStockRead); // Inicializa los buses con los datos de la matriz busStockRead

        Hashtable<Integer, String[]> busStopsRead = controlCenter.readBusStops();   // Lee el archivo BusStops.csv y lo guarda
        BusStop[] busStops = controlCenter.initializeBusStops(busStopsRead); // Inicializa los paraderos

        // Prints a menu with the options to choose:
        // 1. Usar datos en tiempo real provistos por RealTimeData.csv
        // 2. Simular datos en tiempo real para los buses (aleatoriamente)
        // 3. Salir
        int option = 0;
        Scanner scanner = new Scanner(System.in);
        while (option != 4) {
            System.out.println("==========================================================");
            System.out.println("¡Bienvenido al sistema de control de buses!:");
            System.out.println("1. Usar datos en tiempo real provistos por RealTimeData.csv");
            System.out.println("2. Simular datos aleatorios en tiempo real para los buses");
            System.out.println("3. Mostrar información cargada en el sistema");
            System.out.println("4. Salir");
            System.out.println("==========================================================");
            System.out.println("Ingrese su opción: ");
            option = scanner.nextInt();
            if (option == 1) {
                System.out.println("Leyendo archivo RealTimeData.csv...");;
            } else if (option == 2) {
                int option2 = 0;
                buses = controlCenter.simulateBuses(buses); // Simula aplicando valores aleatorios para los buses
                controlCenter.writeRealTimeData(buses); //Ahora escribe el archivo RealTimeData.csv con los datos simulados de los buses
                while(option2!=2){
                    int[] possibleBuses = new int[buses.length];    // Posibles buses a elegir para la siguiente ruta
                    for (int i = 0; i < buses.length; i++)
                        possibleBuses[i] = buses[i].getBusNumber();
                    int[] possibleStops = new int[busStops.length]; // Posibles paraderos a elegir para la siguiente ruta
                    for (int i = 1; i < busStops.length; i++)
                        possibleStops[i] = busStops[i].getBusStopNumber();
                    int[] possibleDirections = new int[2]; // Posibles direcciones a elegir para la siguiente ruta
                    possibleDirections[0] = 1;
                    possibleDirections[1] = 2;
                    System.out.println("==========================================================");
                    System.out.println("1. Consultar próximo bus en paradero");
                    System.out.println("2. Volver al menú principal");
                    System.out.println("==========================================================");
                    System.out.println("Ingrese su opción: ");
                    option2 = scanner.nextInt();
                    if (option2 == 1) {
                        // Request the user to get busNumber, busStopNumber and direction
                        System.out.println("Ingrese el número de bus: ");
                        int busNumber = scanner.nextInt();
                        // Check if the busNumber is valid
                        boolean busNumberValid = false;
                        for (int i = 0; i < possibleBuses.length; i++) {
                            if (busNumber == possibleBuses[i]) {
                                busNumberValid = true;
                                break;
                            }
                        }
                        System.out.println("Ingrese el número de paradero: ");
                        int busStopNumber = scanner.nextInt();
                        // Check if the busStopNumber is valid
                        boolean busStopNumberValid = false;
                        for (int i = 1; i < possibleStops.length; i++) {
                            if (busStopNumber == possibleStops[i]) {
                                busStopNumberValid = true;
                                break;
                            }
                        }
                        System.out.println("Ingrese la dirección (1: Norte, 2: Sur): ");
                        int directionInt = scanner.nextInt();
                        // Check if the direction is valid
                        boolean directionValid = false;
                        for (int i = 0; i < possibleDirections.length; i++) {
                            if (directionInt == possibleDirections[i]) {
                                directionValid = true;
                                break;
                            }
                        }
                        String direction;
                        if(directionInt == 1)
                            direction = "North";
                        else if(directionInt == 2)
                            direction = "South";
                        else
                            direction = "North";

                        // Check if the busNumberValid, busStopNumberValid and directionValid are valid. If not, print an error message and continue.
                        if (!busNumberValid) {
                            System.out.println("El número de bus ingresado no es válido.");
                            continue;
                        }
                        if (!busStopNumberValid) {
                            System.out.println("El número de paradero ingresado no es válido.");
                            continue;
                        }
                        if (!directionValid) {
                            System.out.println("La dirección ingresada no es válida.");
                            continue;
                        }
                        System.out.println("Consultando próximo bus de la línea " + busNumber + " en el paradero " + busStopNumber + " en la dirección " + direction + "...");
                        int arrivalTime = (int) controlCenter.nextBus(buses, busStops, busNumber, busStopNumber, direction);
                        if(arrivalTime>=100)
                            System.out.println("No hay buses disponibles para esta línea, o bien, su bus no se detiene en el paradero indicado.");
                        else
                            System.out.println("El próximo bus llegará en " + arrivalTime + " minutos más.");

                    } else if (option2 == 2) {
                        System.out.println("Volviendo al menú principal...");
                    } else {
                        System.out.println("Opción inválida :/ Intente nuevamente.");
                    }
                }
            } else if(option == 3){
                System.out.println("El sistema indica que hay " + buses.length + " buses cargados en el sistema.");
                System.out.println("Además, existen " + (busStops.length-1) + " paraderos cargados.");
            }
            else if (option == 4) {
                System.out.println("Saliendo...");
            } else {
                System.out.println("Opción inválida :/ Intente nuevamente");
            }
        }

        // EJEMPLOS:
        //controlCenter.nextBus(buses, busStops,101, 3,"North"); //Imprime tiempo estimado
        //controlCenter.nextBus(buses, busStops,101, 3,"South");
    }
}
