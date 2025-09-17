// KROK-03: City – reprezentuje wiersz z pliku strefy.csv
// Kolumny: Stolica, Strefa czasowa letnia (godziny), Szerokość geogr., Długość geogr.
// Mega-komentarze o parsowaniu: "52.5200 N" => +52.5200, "118.2437 W" => -118.2437

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class City {
    private final String name;          // nazwa stolicy / miasta
    private final int summerOffsetHours;// przesunięcie godzinowe względem UTC w czasie letnim (np. Berlin=2)
    private final double latitude;      // szerokość geograficzna w stopniach (-90..+90), N=+, S=-
    private final double longitude;     // długość geograficzna w stopniach (-180..+180), E=+, W=-

    public City(String name, int summerOffsetHours, double latitude, double longitude) {
        this.name = name;
        this.summerOffsetHours = summerOffsetHours;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() { return name; }
    public int getSummerOffsetHours() { return summerOffsetHours; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    // KROK-03A: parser wartości typu "24.4539 N" lub "77.0428 W"
    private static double parseSignedDegree(String token) {
        // token może wyglądać np.: "24.4539 N" lub " 54.3773 E"
        token = token.trim();
        // Rozbij po spacji na [wartość, kierunek]
        String[] parts = token.split("\\s+");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Niepoprawny format współrzędnej: " + token);
        }
        double value = Double.parseDouble(parts[0].replace(",", ".")); // na wszelki wypadek
        String dir = parts[1].toUpperCase(Locale.ROOT);
        switch (dir) {
            case "N": case "E":
                return +value;
            case "S": case "W":
                return -value;
            default:
                throw new IllegalArgumentException("Nieznany kierunek (N/S/E/W): " + dir);
        }
    }

    // KROK-03B: parseLine – dostaje pojedynczą linię CSV (po nagłówku) i tworzy City.
    // CSV: Stolica,Strefa czasowa letnia,Szerokość geograficzna,Długość geograficzna
    public static City parseLine(String line) {
        String[] cols = line.split(",");
        if (cols.length < 4) {
            throw new IllegalArgumentException("Za mało kolumn w linii CSV: " + line);
        }
        String name = cols[0].trim();
        int offset = Integer.parseInt(cols[1].trim()); // całkowite godziny
        // Uwaga: w CSV szerokość i długość mogą mieć dodatkowe spacje przed kierunkiem
        String latToken = cols[2].trim(); // np. "24.4539 N"
        String lonToken = cols[3].trim(); // np. "54.3773 E" LUB może zawierać spację przed W/E
        double lat = parseSignedDegree(latToken);
        double lon = parseSignedDegree(lonToken);
        // Dodatkowe walidacje z treści: zakres długości -180..+180
        if (lon < -180 || lon > 180) {
            throw new IllegalArgumentException("Długość geograficzna poza zakresem (-180..180): " + lon);
        }
        return new City(name, offset, lat, lon);
    }

    // KROK-03C: parseFile – czyta cały plik CSV i zwraca listę City
    public static List<City> parseFile(Path csvPath) {
        try {
            List<String> lines = Files.readAllLines(csvPath);
            if (lines.isEmpty()) return List.of();
            // Pomijamy nagłówek (pierwsza linia)
            return lines.stream()
                    .skip(1)
                    .filter(l -> !l.isBlank())
                    .map(City::parseLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Błąd czytania pliku: " + csvPath, e);
        }
    }

    // KROK-05: sortowanie pomocnicze – po offset (rosnąco), a przy remisie po nazwie
    public static List<City> sortByOffsetThenName(List<City> input) {
        return input.stream()
                .sorted(Comparator.comparingInt(City::getSummerOffsetHours).thenComparing(City::getName))
                .collect(Collectors.toList());
    }

    // Dodatkowe: teoretyczne przesunięcie z długości geograficznej (15 stopni = 1h).
    public int theoreticalOffsetFromLongitude() {
        return (int) Math.round(this.longitude / 15.0);
    }

    @Override
    public String toString() {
        return name + " (UTC" + (summerOffsetHours >= 0 ? "+" : "") + summerOffsetHours + ")"
                + " lat=" + latitude + ", lon=" + longitude;
    }
}
