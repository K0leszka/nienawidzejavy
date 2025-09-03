public class Truck extends Vehicle {
    private int loadCapacity;

    public Truck(String brand, String model, int year, int loadCapacity) {
        super(brand,model,year);
        this.loadCapacity = loadCapacity;
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

    @Override
    public String toString(){
        return "Truck" + ";" + getBrand() + ";" + getModel() + ";" + getYear() + ";" + loadCapacity;
    }
}
