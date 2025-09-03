import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FleetManager {
    private List<Vehicle> fleet;

    public FleetManager() {
        fleet = new ArrayList<Vehicle>();
    }

    public void addFleet(Vehicle vehicle) {
        fleet.add(vehicle);
    }


    public List<Vehicle> getFleet() {
        return new ArrayList<>(fleet);

    }

    public void findByBrand(String brand) {
        for (Vehicle vehicle : fleet) {
            if (vehicle.getBrand().equals(brand)) {
                System.out.println("Vehicle/s found: " + vehicle);
            }
        }
    }

    public void findByModel(String model) throws VehicleNotFoundException {
        for (Vehicle vehicle : fleet) {
            if (vehicle.getModel().equals(model)) {
                System.out.println("Vehicle model/s found: " + vehicle);
                return;
            }
        }
        throw new VehicleNotFoundException("Vehicle model not found", 404);
    }

    public int countByType(Class<?> type){
       int count = 0;
        for(Vehicle vehicle : fleet){
            if (type.isInstance(vehicle)){
                count++;
            }
        }
        return count;
    }

    public void filterByYear(int minyear,  int maxyear) {
        for (Vehicle vehicle : fleet) {
            if (vehicle.getYear() >= minyear && vehicle.getYear() <= maxyear) {
                System.out.println("Vehicle/s found: " + vehicle);
            }
        }
    }

    public void saveToFile(String filename) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Vehicle vehicle : fleet) {
                writer.write(vehicle.toString());
                writer.newLine();
            }
            System.out.println("Vehicle/s saved at: " + filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readFromFile(String filename) {
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            while((line = reader.readLine()) != null) {
                String[] split = line.split(";");
                String type = split[0];
                if (type.equals("Vehicle")) {
                    String brand = split[1];
                    String model = split[2];
                    int year = Integer.parseInt(split[3]);
                    fleet.add(new Vehicle(brand, model, year));
                } else if (type.equals("Car")) {
                    String brand = split[1];
                    String model = split[2];
                    int years = Integer.parseInt(split[3]);
                    int doors = Integer.parseInt(split[4]);
                    fleet.add(new Car(brand, model, years, doors));
                } else if (type.equals("ElectricCar")) {
                    String brand = split[1];
                    String model = split[2];
                    int years = Integer.parseInt(split[3]);
                    int doors = Integer.parseInt(split[4]);
                    int battery = Integer.parseInt(split[5]);
                    fleet.add(new ElectricCar(brand, model, years, doors, battery));
                } else if (type.equals("Truck")) {
                    String brand = split[1];
                    String model = split[2];
                    int year = Integer.parseInt(split[3]);
                    int load = Integer.parseInt(split[4]);
                    fleet.add(new Truck(brand, model, year, load));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
