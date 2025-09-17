// Main – demonstracja wszystkich kroków z kolosa 2024.
// • Wczytuje strefy z CSV do List<City>.
// • Testuje DigitalClock (KROK-06) i toString 24h/12h.
// • Generuje przykładowe zegary analogowe do plików SVG (KROK-10).

import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Ścieżka do CSV (dostosuj, np. "./strefy.csv")
            Path csv = Paths.get("strefy.csv");

            // KROK-03C: wczytaj miasta
            List<City> cities = City.parseFile(csv);
            System.out.println("Wczytano miast: " + cities.size());
            // Wypisz pierwsze parę dla kontroli
            cities.stream().limit(5).forEach(c -> System.out.println("  " + c));

            // KROK-05: sortowanie po offset letnim, a przy remisie po nazwie
            List<City> sorted = City.sortByOffsetThenName(cities);
            System.out.println("\nMiasta posortowane wg offsetu letniego:");
            sorted.forEach(c -> System.out.println("  " + c.getName() + " UTC" + (c.getSummerOffsetHours()>=0?"+":"") + c.getSummerOffsetHours()));

            // KROK-06: testy DigitalClock – przypadki z tabeli (przykładowe)
            DigitalClock dc24 = new DigitalClock(true);  // 24h
            dc24.setTime("00:00:00");
            System.out.println("\n[24h] 00:00:00 -> " + dc24);
            dc24.setTime("23:59:00");
            System.out.println("[24h] 23:59:00 -> " + dc24);

            DigitalClock dc12 = new DigitalClock(false); // 12h
            dc12.setTime(12, 0, 0);
            System.out.println("[12h] 12:00:00 -> " + dc12);
            dc12.setTime(0, 5, 0); // wewnętrznie 0:05 = 12:05 AM
            System.out.println("[12h] 00:05:00 -> " + dc12);

            // KROK-04/01C: setCurrentTime wg miasta (weźmy np. Berlin)
            City berlin = findByName(cities, "Berlin");
            DigitalClock berlinClock = new DigitalClock(true, berlin);
            berlinClock.setCurrentTime();
            System.out.println("\nAktualny czas (letni) w Berlinie (24h): " + berlinClock);

            // KROK-07/08/09/10: AnalogClock – zapisz zegary SVG dla kilku miast
            Path outDir = Paths.get("zegary_svg");
            AnalogClock.saveClocksForCities(Arrays.asList(
                    findByName(cities, "Berlin"),
                    findByName(cities, "Londyn"),
                    findByName(cities, "Los Angeles"),
                    findByName(cities, "Bangkok")
            ), true, outDir);
            System.out.println("\nZapisano pliki SVG do katalogu: " + outDir.toAbsolutePath());

        } catch (Exception ex) {
            System.err.println("Błąd: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static City findByName(List<City> cities, String name) {
        return cities.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Brak miasta: " + name));
    }
}
