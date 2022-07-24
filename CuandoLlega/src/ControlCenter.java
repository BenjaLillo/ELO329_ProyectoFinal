import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import java.io.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;

import static java.lang.Math.abs;

public class ControlCenter {
    //El diccionario busStopsInPerc indica el porcentaje del recorrido correspondiente a cada busNumber (ej.: 101 -> 60%, 201 -> 65%, etc.)
    public Hashtable<Integer, Integer[]> busStops = new Hashtable<Integer, Integer[]>();

    public int[][] readBusStock() throws IOException {
        // from the second row of the file, reads "src/BusData.csv" and returns a 2D int array with the data of the file.
        // The matrix has 3 columns, and the number of rows is the number of lines in the file.
        // The first row of the matrix is deleted, because it is the header row.

        FileReader archCSV = null;
        CSVReader csvReader = null;
        int[][] initialRead = null;

        try {
            archCSV = new FileReader("src/BusData.csv");
            CSVParser conPuntoYComa = new CSVParserBuilder().withSeparator(';').build();
            csvReader = new CSVReaderBuilder(archCSV).withCSVParser(conPuntoYComa).build();
            String[] fila = null;
            int i = 0;
            while ((fila = csvReader.readNext()) != null) {
                i++;
            }
            initialRead = new int[i][3];
            csvReader.close();
            archCSV = new FileReader("src/BusData.csv");
            conPuntoYComa = new CSVParserBuilder().withSeparator(';').build();
            csvReader = new CSVReaderBuilder(archCSV).withCSVParser(conPuntoYComa).build();
            fila = null;
            i = 0;
            while ((fila = csvReader.readNext()) != null) {
                if (i != 0) {
                    initialRead[i - 1][0] = Integer.parseInt(fila[0]);
                    initialRead[i - 1][1] = Integer.parseInt(fila[1]);
                    initialRead[i - 1][2] = Integer.parseInt(fila[2]);
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
        int[][] initialRead2 = new int[initialRead.length - 1][3];
        for (int i = 0; i < initialRead2.length; i++) {
            initialRead2[i][0] = initialRead[i][0];
            initialRead2[i][1] = initialRead[i][1];
            initialRead2[i][2] = initialRead[i][2];
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
        // The buses are initialized with the values of the first column of the matrix called initialRead.
        // The buses are added to the array called buses.
        ArrayList<Bus> busesArrayList = new ArrayList<Bus>();
        for (int j = 0; j < busStockRead.length; j++) {
            for (int i = 0; i < busStockRead[j][1]; i++) {
                busesArrayList.add(new Bus(0, busStockRead[j][0], "NA",busStockRead[j][2], 0, 0));
                //System.out.println(busesArrayList.get(i).toString());
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
        // reads the file BusStopData.csv and returns a hashtable with the data of the file.
        // The first row is the header, where the first column is the BusNumber, the second column is the BusStops and the third
        // column is the percentage of the bus stops.
        // The hashtable has the key as the BusNumber, and the value as an array with the BusStops and the percentage of the bus stops.
        // The hashtable is called busStopsRead.
        FileReader archCSV = null;
        CSVReader csvReader = null;
        Hashtable<Integer, String[]> busStopsRead = new Hashtable<Integer, String[]>();

        String[][] busStopsRead2 = new String[0][];
        try {
            archCSV = new FileReader("src/BusStopData.csv");
            CSVParser conPuntoYComa = new CSVParserBuilder().withSeparator(';').build();
            csvReader = new CSVReaderBuilder(archCSV).withCSVParser(conPuntoYComa).build();
            String[] fila = null;
            int i = 0;
            while ((fila = csvReader.readNext()) != null) {
                i++;
            }
            busStopsRead2 = new String[i][3];
            csvReader.close();
            archCSV = new FileReader("src/BusStopData.csv");
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
        int totBusStops = 50;
        boolean flag = true;
        BusStop[] allBusStops = new BusStop[totBusStops*2+1];  //Se multiplica por 2 para considerar dirección "North" y "South"

        // Iterates BusStops array
        for (int i = 1; i < totBusStops*2+1; i++) {
            ArrayList<Integer> possibleBusNumbers = new ArrayList<Integer>();
            ArrayList<Integer> possibleBusNumbersPercents = new ArrayList<Integer>();

            //ii increments each 2 iterations of i.
            int ii;
            if(flag==true){
                ii = i/2 + 1;
                flag = false;
            }else{
                ii = i/2;
                flag = true;
            }
            //System.out.println(ii);

            // GETS THE BUS NUMBERS
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
                    if (Integer.parseInt(busStops3[j]) == ii)
                        possibleBusNumbers.add(key);
                }
            }
            //System.out.println(possibleBusNumbers);
            //Converts possibleBusNumbers to an array of integers
            int[] possibleBusNumbers2 = new int[possibleBusNumbers.size()];
            for (int j = 0; j < possibleBusNumbers2.length; j++)
                possibleBusNumbers2[j] = possibleBusNumbers.get(j);

            //If i is odd, then the BusStop is in the north direction, otherwise it is in the south direction.
            String direction = "";
            if (i % 2 == 1) {
                direction = "North";
            } else {
                direction = "South";
            }

            allBusStops[i] = new BusStop(ii,direction,possibleBusNumbers2);
            //System.out.println(allBusStops[i].toString());
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

    public int readRecPercentage(int i) throws IOException {
        // returns the RecPercentage of the bus with the id i, which is in the fourth column of the RealTimeData.csv file.
        // Ignores the first row of the file.
        int recPercentage = 0;
        FileReader archCSV = null;
        CSVReader csvReader = null;

        try {
            archCSV = new FileReader("src/RealTimeData.csv");
            CSVParser conPuntoYComa = new CSVParserBuilder().withSeparator(';').build();
            csvReader = new CSVReaderBuilder(archCSV).withCSVParser(conPuntoYComa).build();
            String[] fila;
            int i2 = 0;
            while ((fila = csvReader.readNext()) != null) {
                if (i2 != 0) {
                    if (i2 == i) {
                        recPercentage = Integer.parseInt(fila[3]);
                    }
                }
                i2++;
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
        return recPercentage;
    }

    public int readRecPerc(int busNumber, int busStopID) throws IOException {
        String busStopIDfromData = readBusBSData(busNumber)[0];
        String busStopPercfromData = readBusBSData(busNumber)[1];
        // converts the busStopIDfromData to an integer array, separated with ","
        String[] busStopIDfromDataArray = busStopIDfromData.split(",");
        String[] busStopPercfromDataArray = busStopPercfromData.split(",");
        // Converts the busStopIDfromDataArray to an integer array
        int[] busStopIDfromDataArrayInt = new int[busStopIDfromDataArray.length];
        for (int j = 0; j < busStopIDfromDataArray.length; j++) {
            busStopIDfromDataArrayInt[j] = Integer.parseInt(busStopIDfromDataArray[j]);
        }
        // Converts the busStopPercfromDataArray to an integer array
        int[] busStopPercfromDataArrayInt = new int[busStopPercfromDataArray.length];
        for (int j = 0; j < busStopPercfromDataArray.length; j++) {
            busStopPercfromDataArrayInt[j] = Integer.parseInt(busStopPercfromDataArray[j]);
        }
        // Iterates busStopIDfromDataArrayInt and compares it to busStopID
        int perc = 0;
        for (int j = 0; j < busStopIDfromDataArrayInt.length; j++) {
            if (busStopIDfromDataArrayInt[j] == busStopID) {
                perc = busStopPercfromDataArrayInt[j];
            }
        }
        return perc;
    }

    public String[] readBusBSData(int busNumber) throws IOException {
        // Returns the data of the bus stops associated with bus, with the id busNumber.
        // The data is in the BusStopData.csv file, where the first column is the bus number.
        // The method iterates to finding when the busNumber is equal to the busNumber in the first column.
        // Then, it returns the data in the second column and the third column.
        String[] data = new String[2];
        FileReader archCSV = null;
        CSVReader csvReader = null;

        try {
            archCSV = new FileReader("src/BusStopData.csv");
            CSVParser conPuntoYComa = new CSVParserBuilder().withSeparator(';').build();
            csvReader = new CSVReaderBuilder(archCSV).withCSVParser(conPuntoYComa).build();
            String[] fila;
            int i2 = 0;
            while ((fila = csvReader.readNext()) != null) {
                if (i2 != 0) {
                    if (Integer.parseInt(fila[0]) == busNumber) {
                        data[0] = fila[1];
                        data[1] = fila[2];
                    }
                }
                i2++;
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
        return data;
    }

    public Bus[] simulateBuses(Bus[] buses){

        // Iterates every bus and sets all attributes to 0
        for (int i = 0; i < buses.length; i++) {
            String simulatedDirection; // Sets to random value between "North" and "South"
            int random = (int) (Math.random() * 2);
            if (random == 0)
                simulatedDirection = "North";
            else
                simulatedDirection = "South";

            int simulatedRecPercentage; // Sets to random value between 0 and 100
            random = (int) (Math.random() * 1000);
            simulatedRecPercentage = random;

            int simulatedAverageSpeed; // Sets to random value between 20 and 45
            random = (int) (Math.random() * 26) + 20;
            simulatedAverageSpeed = random;

            buses[i].setId(i + 1);
            buses[i].setDirection(simulatedDirection);
            buses[i].setRecPercentage(simulatedRecPercentage);
            buses[i].setAverageSpeed(simulatedAverageSpeed);
        }
        return buses;
    }

    public float nextBus(Bus[] buses, BusStop[] busStops, int busNumber, int busStopID, String direction) throws IOException {
        //System.out.println("Testing nextBus method with parameters: " + busNumber + ", " + busStopID + ", " + direction);
        // Iterates only the buses in buses array that have the busNumber of the bus parameter
        int diffMin = 100000;
        int minAverageSpeed = 0;
        int minTotalDistance = 0;
    for (int i = 0; i < buses.length; i++) {
            if (buses[i].getBusNumber() == busNumber) {
                //System.out.println(readRecPercentage(i));
                //System.out.println(readBusBSData(busNumber)[0]);
                //System.out.println(readBusBSData(busNumber)[1]);
                int recPercentage = readRecPerc(busNumber, busStopID);

                if((int) (abs(buses[i].getRecPercentage() - recPercentage)) < diffMin){
                    diffMin = (int) (buses[i].getRecPercentage() - recPercentage);
                    if (diffMin < 0 && buses[i].getDirection()=="North") { //Caso 1: Bus hacia el norte llegando
                        diffMin = diffMin * -1;
                    }
                    else if(diffMin < 0 && buses[i].getDirection()=="South"){ //Caso 2: Bus hacia el sur llegando
                        diffMin = diffMin * -1;
                    }
                    else if(diffMin >= 0 && buses[i].getDirection()=="North"){ //Caso 4: Bus hacia el norte pasado
                        diffMin = 100000;
                    }
                    else if(diffMin >= 0 && buses[i].getDirection()=="South"){ //Caso 3: Bus hacia el sur pasado
                        diffMin = 100000;
                    }
                    if(buses[i].getDirection()!=direction)
                    {
                        diffMin = 100000;
                    }
                    minAverageSpeed = (int) buses[i].getAverageSpeed();
                    minTotalDistance = (int) buses[i].getTotalDistance();
                    //System.out.println("Min average speed: " + minAverageSpeed);
                    //System.out.println("Min total distance: " + minTotalDistance);
                }
            }
        }
        //System.out.println("diffMin" + diffMin);
        //System.out.println(minAverageSpeed);
        //System.out.println(minTotalDistance);
        float minPerc = (float) diffMin / 1000;
        //System.out.println(minPerc);
        float arriveTime = ((minTotalDistance * minPerc) / minAverageSpeed)*60;

        //System.out.println(arriveTime);
        return arriveTime;  //Tiempo de llegada del bus en minutos
    }
}