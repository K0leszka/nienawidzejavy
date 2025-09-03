public class ElectricCar extends Car {
    private int batterCapacity;

    public ElectricCar(String brand, String model, int year,int numberOfDoors, int batterCapacity) {
        super(brand, model, year,numberOfDoors);
        this.batterCapacity = batterCapacity;
    }

    public double getBatterCapacity() {
        return batterCapacity;
    }

    @Override
    public String toString() {
        return "ElectricCar" + ";" + getBrand() + ";" + getModel() + ";" + getYear() + ";" + getNumberOfDoors() + ";" + batterCapacity;
    }
}
