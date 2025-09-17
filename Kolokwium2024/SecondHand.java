// KROK-08: SecondHand – implementacja wskazówki sekundowej.
// Kąt = sekundy * 6° (bo 60 sekund to pełny obrót 360°, a 360/60 = 6).

public class SecondHand extends ClockHand {
    @Override
    public double angleDegrees() {
        return second * 6.0; // 6° na sekundę
    }
}
