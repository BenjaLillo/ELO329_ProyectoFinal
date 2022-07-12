import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ControlCenter controlCenter = new ControlCenter();
        int[][] initialRead = controlCenter.initialRead();

        // Prints the matrix called initalRead
        for (int i = 0; i < initialRead.length; i++) {
            for (int j = 0; j < initialRead[i].length; j++) {
                System.out.print(initialRead[i][j] + " ");
            }
            System.out.println();
        }

        // Creates an array of buses with the data of the matrix called initalRead
        Bus[] buses = new Bus[initialRead[0][1]];
        for (int i = 0; i < initialRead[0][1]; i++) {
            buses[i] = new Bus(0, initialRead[i][0], "North", 0,0);
            System.out.println((buses[i].toString()));
        }

    }
}
