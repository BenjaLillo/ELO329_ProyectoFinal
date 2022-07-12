public class Bus {
    //getter, setter and constructor methods:
    private int id;
    private int busNumber;
    private String direction;
    private double recPercentage;
    private double averageSpeed;

    public Bus(int id, int busNumber, String direction, double recPercentage, double averageSpeed) {
        this.id = id;
        this.busNumber = busNumber;
        this.direction = direction;
        this.recPercentage = recPercentage;
        this.averageSpeed = averageSpeed;
    }

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

    public String toString() {
        return "Bus{" +
                "id=" + id +
                ", busNumber=" + busNumber +
                ", direction='" + direction + '\'' +
                ", recPercentage=" + recPercentage +
                ", averageSpeed=" + averageSpeed +
                '}';
    }

}
