import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class ControlCenter {
    //El diccionario busStopsInPerc indica el porcentaje del recorrido correspondiente a cada busNumber (ej.: 101 -> 60%, 201 -> 65%, etc.)
    public Hashtable<Integer, Integer[]> busStops = new Hashtable<Integer, Integer[]>();

    public int[][] readBusStock() throws IOException {
        // from the second row of the file, reads "src/BusStock.csv" and returns a 2D int array with the data of the file.
        // The matrix has 2 columns, and the number of rows is the number of lines in the file.
        // The first row of the matrix is deleted, because it is the header row.

        FileReader archCSV = null;
        CSVReader csvReader = null;
        int[][] initialRead = null;

        try {
            archCSV = new FileReader("src/BusStock.csv");
            CSVParser conPuntoYComa = new CSVParserBuilder().withSeparator(';').build();
            csvReader = new CSVReaderBuilder(archCSV).withCSVParser(conPuntoYComa).build();
            String[] fila = null;
            int i = 0;
            while ((fila = csvReader.readNext()) != null) {
                i++;
            }
            initialRead = new int[i][2];
            csvReader.close();
            archCSV = new FileReader("src/BusStock.csv");
            conPuntoYComa = new CSVParserBuilder().withSeparator(';').build();
            csvReader = new CSVReaderBuilder(archCSV).withCSVParser(conPuntoYComa).build();
            fila = null;
            i = 0;
            while ((fila = csvReader.readNext()) != null) {
                if (i != 0) {
                    initialRead[i - 1][0] = Integer.parseInt(fila[0]);
                    initialRead[i - 1][1] = Integer.parseInt(fila[1]);
                }
                i++;
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            csvReader.close();
        }

        //deletes last row of the matrix
        int[][] initialRead2 = new int[initialRead.length - 1][2];
        for (int i = 0; i < initialRead2.length; i++) {
            initialRead2[i][0] = initialRead[i][0];
            initialRead2[i][1] = initialRead[i][1];
        }

        return initialRead2;
    }

    public Bus[] initializeBuses(int busStockRead[][]) {
        int totalBuses = 0;

        // Computes totalBuses, which is the summation of the elements of the second column of the matrix called initalRead
        for (int i = 0; i < busStockRead.length; i++) {
            totalBuses += busStockRead[i][1];
        }

        // Creates an array of buses with a length of totalBuses, and initializes as many buses as the totalBuses variable.
        // The buses are initialized with the values of the first column of the matrix called initalRead.
        // The buses are added to the array called buses.
        ArrayList<Bus> busesArrayList = new ArrayList<Bus>();
        for (int j = 0; j < busStockRead.length; j++) {
            for (int i = 0; i < busStockRead[j][1]; i++) {
                busesArrayList.add(new Bus(0, busStockRead[j][0], "NA", 0, 0));
                System.out.println(busesArrayList.get(i).toString());
            }
        }
        // Converts the arrayList to an array of buses called buses.
        Bus[] buses = new Bus[busesArrayList.size()];
        for (int i = 0; i < buses.length; i++) {
            buses[i] = busesArrayList.get(i);
        }
        return buses;
    }

    public Hashtable<Integer, String[]> readBusStops() throws IOException {
        // reads the file BusStops.csv and returns a hashtable with the data of the file.
        // The first row is the header, where the first column is the BusNumber, the second column is the BusStops and the third
        // column is the percentage of the bus stops.
        // The hashtable has the key as the BusNumber, and the value as an array with the BusStops and the percentage of the bus stops.
        // The hashtable is called busStopsRead.
        FileReader archCSV = null;
        CSVReader csvReader = null;
        Hashtable<Integer, String[]> busStopsRead = new Hashtable<Integer, String[]>();

        String[][] busStopsRead2 = new String[0][];
        try {
            archCSV = new FileReader("src/BusStops.csv");
            CSVParser conPuntoYComa = new CSVParserBuilder().withSeparator(';').build();
            csvReader = new CSVReaderBuilder(archCSV).withCSVParser(conPuntoYComa).build();
            String[] fila = null;
            int i = 0;
            while ((fila = csvReader.readNext()) != null) {
                i++;
            }
            busStopsRead2 = new String[i][3];
            csvReader.close();
            archCSV = new FileReader("src/BusStops.csv");
            conPuntoYComa = new CSVParserBuilder().withSeparator(';').build();
            csvReader = new CSVReaderBuilder(archCSV).withCSVParser(conPuntoYComa).build();
            fila = null;
            i = 0;
            while ((fila = csvReader.readNext()) != null) {
                if (i != 0) {
                    busStopsRead2[i - 1][0] = fila[0];
                    busStopsRead2[i - 1][1] = fila[1];
                    busStopsRead2[i - 1][2] = fila[2];
                }
                i++;
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            csvReader.close();
        }

        // Deletes last row of the matrix
        String[][] busStopsRead3 = new String[busStopsRead2.length - 1][3];
        for (int i = 0; i < busStopsRead3.length; i++) {
            busStopsRead3[i][0] = busStopsRead2[i][0];
            busStopsRead3[i][1] = busStopsRead2[i][1];
            busStopsRead3[i][2] = busStopsRead2[i][2];
        }

        // Creates a hashtable with the key as the BusNumber, and the value as an array with the BusStops String array
        // and the percentages of the bus stops String array.
        for (int i = 0; i < busStopsRead3.length; i++) {
            String[] data = new String[]{busStopsRead3[i][1], busStopsRead3[i][2]};
            busStopsRead.put(Integer.parseInt(busStopsRead3[i][0]), data);
        }
        return busStopsRead;
    }

    public BusStop[] initializeBusStops(Hashtable<Integer, String[]> busStopsRead) {
        int totBusStops = 15;
        BusStop[] allBusStops = new BusStop[totBusStops];

        // Iterates BusStops array
        for (int i = 1; i < allBusStops.length; i++) {
            //System.out.println(i);
            ArrayList<Integer> possibleBusNumbers = new ArrayList<Integer>();
            // Iterates every key in the hashtable busStopsRead
            Set<Integer> keys = busStopsRead.keySet();  // Retorna una lista de las claves del hashmap
            for (Integer key : keys) {
                String[] busStops = busStopsRead.get(key);
                String busStops2 = busStops[0];
                //System.out.println(busStops2);
                // Converts the string busStops2 to an array of strings
                String[] busStops3 = busStops2.split(",");
                // If busStops3[i] is equal to the current BusStop, then the BusNumber is added to the arraylist possibleBusNumbers
                for (int j = 0; j < busStops3.length; j++) {
                    if (Integer.parseInt(busStops3[j]) == i)
                        possibleBusNumbers.add(key);
                }
            }

            //System.out.println(possibleBusNumbers);

            //Converts possibleBusNumbers to an array of integers
            int[] possibleBusNumbers2 = new int[possibleBusNumbers.size()];
            for (int j = 0; j < possibleBusNumbers2.length; j++)
                possibleBusNumbers2[j] = possibleBusNumbers.get(j);

            // Initializes the BusStop
            allBusStops[i] = new BusStop(i,possibleBusNumbers2);
            System.out.println(allBusStops[i].toString());
        }
        return allBusStops;
    }

    public void writeRealTimeData(Bus[] buses) throws IOException {
        // Writes RealTimeData.csv with the info of every bus. Every row is a bus.
        // The first row is the header, where the 5 columns are corresponding to the attributes of the Bus class.

        FileWriter archCSV = null;
        CSVWriter csvWriter = null;

        try {
            archCSV = new FileWriter("src/RealTimeData.csv");
            CSVWriterBuilder csvWriterBuilder = new CSVWriterBuilder(archCSV).withSeparator(';');
            csvWriter = (CSVWriter) csvWriterBuilder.build();
            String[] header = {"BusID", "BusNumber", "BusDirection", "RecPercentage", "AverageSpeed"};
            csvWriter.writeNext(header);
            for (int i = 0; i < buses.length; i++) {
                String[] data = {Integer.toString(buses[i].getId()), Integer.toString(buses[i].getBusNumber()),
                        buses[i].getDirection(), Integer.toString((int)buses[i].getRecPercentage()),
                        Integer.toString((int)buses[i].getAverageSpeed())};
                csvWriter.writeNext(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            csvWriter.close();
        }


    }
}