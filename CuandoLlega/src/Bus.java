public class Bus {
    //getter, setter and constructor methods:
    private int id;
    private int busNumber;
    private String direction;
    private int totalDistance;
    private double recPercentage;
    private double averageSpeed;

    public Bus(int id, int busNumber, String direction, int totalDistance, double recPercentage, double averageSpeed) {
        this.id = id;
        this.busNumber = busNumber;
        this.direction = direction;
        this.totalDistance = totalDistance;
        this.recPercentage = recPercentage;
        this.averageSpeed = averageSpeed;
    }

    // Getters and Setters / ToString
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getBusNumber() {
        return busNumber;
    }
    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public double getRecPercentage() {
        return recPercentage;
    }
    public void setRecPercentage(double recPercentage) {
        this.recPercentage = recPercentage;
    }
    public double getAverageSpeed() {
        return averageSpeed;
    }
    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
    public int getTotalDistance() {
        return totalDistance;
    }
    public String toString() {
        return "Bus{" +
                "id=" + id +
                ", busNumber=" + busNumber +
                ", direction='" + direction + '\'' +
                ", totalDistance=" + totalDistance +
                ", recPercentage=" + recPercentage +
                ", averageSpeed=" + averageSpeed +
                '}';
    }

}
