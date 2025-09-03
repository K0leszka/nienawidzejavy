public class Car extends Vehicle {
    private int numberOfDoors;

    public Car(String brand, String model, int year,int numberOfDoors) {
        super(brand, model, year);
        this.numberOfDoors = numberOfDoors;
    }


    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    @Override
    public String toString() {
        return getBrand() + ";" + getModel() + ";" + getYear() + ";" + getNumberOfDoors();
    }
}
