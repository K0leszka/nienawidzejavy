public class Main {
    public static void main(String[] args) throws VehicleNotFoundException {

        FleetManager f1 = new FleetManager();
        Vehicle v1 = new Vehicle("Volvo", "C40", 2001);
        Vehicle v2 = new Vehicle("Ford", "Focus", 2001);
        Vehicle v3 = new Truck("Ford", "F250", 2002, 600);

        f1.addFleet(v1);
        f1.addFleet(v2);
        f1.addFleet(v3);
        f1.addFleet(new Vehicle("Chevy", "Chevelle", 1997));
        f1.addFleet(new ElectricCar("Tesla", "Cybertruck", 2012, 5, 2000));
        f1.addFleet(new Truck("Ford", "F150",2001,800));
        f1.addFleet(new Truck("Ford", "F150",2001,800));

        f1.saveToFile("Fleet.txt");
        f1.getFleet().clear();
        f1.readFromFile("Fleet.txt");
        f1.readFromFile("Fleet.txt");

        System.out.println("Odczytane pojazdy");
        for(Vehicle v : f1.getFleet()) {
            System.out.println(v);
        }
      }

    }
