// KROK-01: Klasa abstrakcyjna Clock – przechowuje czas i wie, czy działa w trybie 24h/12h.
// Mega-komentarze: tłumaczę absolutne podstawy, krok po kroku.

import java.time.*; // <- nowoczesne API daty/czasu (Instant, ZoneOffset)
import java.util.Objects;

public abstract class Clock {
    // --- [STAN] ---
    // Godzina, minuta, sekunda jako liczby całkowite.
    // UWAGA: w tym zadaniu przyjmujemy, że wewnętrznie przechowujemy czas w "trybie 24h"
    // (0..23 dla godzin). To upraszcza obliczenia; tryb 12h dotyczy tylko formatowania (toString).
    protected int hour;   // 0..23
    protected int minute; // 0..59
    protected int second; // 0..59

    // Czy drukować wynik w zapisie 24-godzinnym (true) czy 12-godzinnym (false).
    protected boolean is24h;

    // KROK-04: Zegar jest "osadzony" w jakimś mieście (strefie czasowej letniej).
    // To pole pozwala np. ustawić aktualny czas wg offsetu miasta.
    private City city;

    // --- [KONSTRUKTORY] ---
    // Konstruktor przyjmuje: tryb 24h/12h + referencję do City (może być null, jeśli niepotrzebne).
    protected Clock(boolean is24h, City city) {
        this.is24h = is24h;
        this.city = city; // można zostawić null (np. do prostych testów).
        // domyślny czas: 00:00:00
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
    }

    // Przeciążony konstruktor: bez miasta (city=null).
    protected Clock(boolean is24h) {
        this(is24h, null);
    }

    // Getter miasta (gdyby trzeba było je wypisać).
    public City getCity() { return city; }

    // Setter miasta (opcjonalnie, gdy zmieniamy "osadzenie" zegara).
    public void setCity(City city) { this.city = city; }

    // --- [USTAWIANIE CZASU] ---
    // KROK-01A: Ustawianie czasu liczbowo z WALIDACJĄ.
    // • hour musi być 0..23 (bo wewnętrznie trzymamy czas 24h)
    // • minute i second 0..59.
    public void setTime(int hour, int minute, int second) {
        // 1) Sprawdź argumenty i rzuć wyjątek jeśli są niepoprawne:
        if (hour < 0 || hour > 23) {
            // "w kontekście zegara 24-godzinnego należy rzucić IllegalArgumentException"
            throw new IllegalArgumentException("Godzina poza zakresem (0..23): " + hour);
        }
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Minuta poza zakresem (0..59): " + minute);
        }
        if (second < 0 || second > 59) {
            throw new IllegalArgumentException("Sekunda poza zakresem (0..59): " + second);
        }
        // 2) Zapisz stan:
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    // KROK-01B: Ustawianie czasu z napisu "HH:MM:SS".
    // • Pozwala wygodnie testować bez liczenia.
    public void setTime(String hhmmss) {
        Objects.requireNonNull(hhmmss, "Napis z czasem nie może być null");
        String[] parts = hhmmss.split(":");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Oczekiwano formatu HH:MM:SS, a jest: " + hhmmss);
        }
        int h = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        int s = Integer.parseInt(parts[2]);
        setTime(h, m, s); // użyj walidacji z metody powyżej
    }

    // KROK-01C: Bieżący czas wg miasta (strefa letnia).
    // • Bierzemy aktualne "UTC" i dodajemy "Strefa czasowa letnia" w godzinach.
    public void setCurrentTime() {
        int baseHour24;
        if (city == null) {
            // Gdy nie mamy miasta, ustawiamy po prostu lokalny czas systemu w trybie 24h.
            LocalTime nowLocal = LocalTime.now();
            baseHour24 = nowLocal.getHour();
            this.minute = nowLocal.getMinute();
            this.second = nowLocal.getSecond();
            this.hour = baseHour24;
            return;
        }
        // Pobierz aktualną chwilę w UTC (bez strefy)
        Instant now = Instant.now();
        // Dodaj offset miasta (w godzinach) -> przelicz na sekundowy Duration
        int offsetHours = city.getSummerOffsetHours();
        Instant shifted = now.plusSeconds((long) offsetHours * 3600L);
        LocalTime lt = LocalDateTime.ofInstant(shifted, ZoneOffset.UTC).toLocalTime();
        // Ustaw wynik
        this.hour = lt.getHour();
        this.minute = lt.getMinute();
        this.second = lt.getSecond();
    }

    // --- [FORMATOWANIE] ---
    // KROK-01D: toString() – 24h vs 12h. "W trybie 12‑godzinnym napis powinien ograniczać się do 12 godzin".
    // • 24h: 00..23
    // • 12h: 01..12 + sufiks AM/PM (czytelne na kolosie)
    @Override
    public String toString() {
        if (is24h) {
            // Format 24h: HH:MM:SS (np. 23:59:00)
            return String.format("%02d:%02d:%02d", hour, minute, second);
        } else {
            // Format 12h: 12,1..11 z AM/PM
            int h12 = hour % 12;
            if (h12 == 0) h12 = 12;
            String ampm = (hour < 12) ? "AM" : "PM";
            return String.format("%02d:%02d:%02d %s", h12, minute, second, ampm);
        }
    }
}
