// KROK-08: ClockHand – abstrakcyjna "wskazówka" zegara analogowego.
// Definiujemy wspólne API: ustawianie czasu, obliczanie kąta, generowanie SVG.

public abstract class ClockHand {
    // Bieżący czas przekazywany do wskazówki (sekundy/minuty/godziny – jako 24h).
    protected int hour;   // 0..23
    protected int minute; // 0..59
    protected int second; // 0..59

    // Ustawia czas – przekazywany z zegara
    public void setTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    // Oblicza kąt w stopniach (0° = godzina 12, rośnie zgodnie z ruchem wskazówek)
    public abstract double angleDegrees();

    // Zwraca fragment SVG rysujący jedną wskazówkę jako linię
    // Parametry: długość (piksele), grubość (px), kolor (np. "black" / "red").
    public String toSvg(double length, double strokeWidth, String color) {
        // Kąt w SVG podajemy w atrybucie transform="rotate(...)"
        double angle = angleDegrees();
        return String.format(
            "  <line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"-%s\" stroke=\"%s\" stroke-width=\"%s\" transform=\"rotate(%s)\" />\n",
            format(length), color, format(strokeWidth), format(angle)
        );
    }

    // Prościutkie formatowanie bez zbędnych ogonków dziesiętnych
    private String format(double v) {
        if (Math.abs(v - Math.rint(v)) < 1e-9) {
            return String.valueOf((long)Math.rint(v));
        }
        return String.format(java.util.Locale.US, "%.2f", v);
    }
}
