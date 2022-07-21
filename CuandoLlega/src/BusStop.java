import java.util.Hashtable;

public class BusStop {
    private int busStopNumber;
    private String direction;
    //El diccionario percentBusStop indica el porcentaje del recorrido correspondiente a cada busNumber (ej.: 101 -> 60%, 201 -> 65%, etc.)
    public Hashtable<Integer, Integer> percentBusStop = new Hashtable<Integer, Integer>();
    public int[] possibleBusNumbers;

    public BusStop(int busStopNumber, String direction, int[] possibleBusNumbers, Hashtable<Integer, Integer> possibleBusNumbersPercents) {
        this.busStopNumber = busStopNumber;
        this.direction = direction;
        this.possibleBusNumbers = possibleBusNumbers;
        this.percentBusStop = possibleBusNumbersPercents;
    }

    public BusStop(int busStopNumber, String direction, int[] possibleBusNumbers) {
        this.busStopNumber = busStopNumber;
        this.direction = direction;
        this.possibleBusNumbers = possibleBusNumbers;
    }


    public BusStop(int BusStopNumber, int[] possibleBusNumbers) {
        this.busStopNumber = BusStopNumber;
        this.possibleBusNumbers = possibleBusNumbers;
    }

    // Getters and Setters / ToString
    public int getBusStopNumber() {
        return busStopNumber;
    }
    public void setBusStopNumber(int busStopNumber) {
        this.busStopNumber = busStopNumber;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public Hashtable<Integer, Integer> getPercentBusStop() {
        return percentBusStop;
    }
    public void setPercentBusStop(Hashtable<Integer, Integer> percentBusStop) {
        this.percentBusStop = percentBusStop;
    }
    public int[] getPossibleBusNumbers() {
        return possibleBusNumbers;
    }
    public void setPossibleBusNumbers(int[] possibleBusNumbers) {
        this.possibleBusNumbers = possibleBusNumbers;
    }
    public String toString() {
        return "BusStop{" +
                "busStopNumber=" + busStopNumber +
                ", direction='" + direction + '\'' +
                ", percentBusStop=" + percentBusStop +
                ", possibleBusNumbers=" + possibleBusNumbers +
                '}';
    }
}
