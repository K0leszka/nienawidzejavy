// KROK-07/09/10: AnalogClock – zegar rysujący pełny SVG (tarcza + 3 wskazówki).
// • Ma prywatną, finalną, polimorficzną listę wskazówek (Hour/Minute/Second).
// • Zmiana czasu => aktualizacja wskazówek (setTime synchronizuje).
// • Może zapisać własny SVG do pliku i wygenerować zegary dla listy miast.

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class AnalogClock extends Clock {
    // KROK-09: lista wskazówek – dokładnie po jednej z każdego typu
    private final List<ClockHand> hands = List.of(
            new HourHand(),   // czarna, grubsza, krótsza
            new MinuteHand(), // czarna, cieńsza, dłuższa
            new SecondHand()  // czerwona, najdłuższa
    );

    public AnalogClock(boolean is24h, City city) {
        super(is24h, city);
        syncHands(); // aby stan był spójny
    }

    // Synchronizacja wskazówek ze stanem czasu zegara
    private void syncHands() {
        for (ClockHand h : hands) {
            h.setTime(hour, minute, second);
        }
    }

    // Nadpisujemy setTime, aby po ustawieniu czasu od razu przesłać go do wskazówek
    @Override
    public void setTime(int hour, int minute, int second) {
        super.setTime(hour, minute, second);
        syncHands();
    }

    @Override
    public void setTime(String hhmmss) {
        super.setTime(hhmmss);
        syncHands();
    }

    @Override
    public void setCurrentTime() {
        super.setCurrentTime();
        syncHands();
    }

    // KROK-07: Generowanie całego SVG (tarcza + 3 wskazówki)
    public String toSvg() {
        StringBuilder sb = new StringBuilder();
        sb.append("<svg width=\"200\" height=\"200\" viewBox=\"-100 -100 200 200\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        // Tarcza (okrąg + 12/3/6/9)
        sb.append("  <circle cx=\"0\" cy=\"0\" r=\"90\" fill=\"none\" stroke=\"black\" stroke-width=\"2\" />\n");
        sb.append("  <g text-anchor=\"middle\">\n");
        sb.append("    <text x=\"0\" y=\"-80\" dy=\"6\">12</text>\n");
        sb.append("    <text x=\"80\" y=\"0\" dy=\"4\">3</text>\n");
        sb.append("    <text x=\"0\" y=\"80\" dy=\"6\">6</text>\n");
        sb.append("    <text x=\"-80\" y=\"0\" dy=\"4\">9</text>\n");
        sb.append("  </g>\n\n");

        // Wskazówki: godziny, minuty, sekundy (parametry długości/grubości/koloru jak w przykładzie z paczki)
        // Godziny – krótsza, grubsza, czarna
        sb.append(hands.get(0).toSvg(50, 4, "black"));
        // Minuty – dłuższa, cieńsza, czarna
        sb.append("\n");
        sb.append(hands.get(1).toSvg(70, 2, "black"));
        // Sekundy – najdłuższa, cienka, czerwona
        sb.append("\n");
        sb.append(hands.get(2).toSvg(80, 1, "red"));

        sb.append("</svg>\n");
        return sb.toString();
    }

    // Zapisz pojedynczy zegar do pliku
    public void saveSvg(Path path) {
        try {
            Files.writeString(path, toSvg());
        } catch (IOException e) {
            throw new RuntimeException("Nie udało się zapisać SVG: " + path, e);
        }
    }

    // KROK-10: Wygeneruj zegary dla listy miast – każdy do osobnego pliku "<Miasto>.svg"
    public static void saveClocksForCities(List<City> cities, boolean is24h, Path outputDir) {
        try {
            Files.createDirectories(outputDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (City c : cities) {
            AnalogClock ac = new AnalogClock(is24h, c);
            ac.setCurrentTime(); // czas wg letniego offsetu miasta
            String fileName = c.getName().replaceAll("[^\\p{L}0-9_\\- ]", "_") + ".svg";
            ac.saveSvg(outputDir.resolve(fileName));
        }
    }
}
