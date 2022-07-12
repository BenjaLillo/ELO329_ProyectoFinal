import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.io.IOException;

public class ControlCenter {
    public void readCSV(String fileName) throws IOException {
        FileReader archCSV = null;
        CSVReader csvReader = null;

        try {
            archCSV = new FileReader(fileName);
            CSVParser conPuntoYComa = new CSVParserBuilder().withSeparator(';').build();
            csvReader = new CSVReaderBuilder(archCSV).withCSVParser(conPuntoYComa).build();
            String[] fila = null;
            while ((fila = csvReader.readNext()) != null) {
                System.out.println(fila[0]
                        + " | " + fila[1]
                        + " |  " + fila[2]
                        + " |  " + fila[3]
                        + " |  " + fila[4]);
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCSV() throws IOException {
        //Writes a csv file called "Test.csv". The file will contain 6 rows and 5 columns.
        // The first row is a header row, where the first cell is "id", the second one is "busNumber", the third is "direction", fourth is "recPercentage" and the last one is "averageSpeed"

        FileWriter archCSV = null;
        CSVWriter csvWriter = null;

        try {
            archCSV = new FileWriter("src/Test.csv");
            csvWriter = new CSVWriter(archCSV,
                    ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            String[] header = {"id", "busNumber", "direction", "recPercentage", "averageSpeed"};
            csvWriter.writeNext(header);
            csvWriter.writeNext(new String[]{"1", "1", "Norte", "0.5", "20"});
            csvWriter.writeNext(new String[]{"2", "2", "Sur", "0.5", "20"});
            csvWriter.writeNext(new String[]{"3", "3", "Norte", "0.5", "20"});
            csvWriter.writeNext(new String[]{"4", "4", "Sur", "0.5", "20"});
            csvWriter.writeNext(new String[]{"5", "5", "Norte", "0.5", "20"});
            csvWriter.writeNext(new String[]{"6", "6", "Sur", "0.5", "20"});
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            csvWriter.close();
        }
    }

    public int[][] initialRead() throws IOException {
        // from the second row of the file, reads "src/BusStock.csv" and returns a 2D int array with the data of the file.
        // The matrix has 2 columns, and the number of rows is the number of lines in the file.
        //the first row of the matrix is deleted, because it is the header row.

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
}