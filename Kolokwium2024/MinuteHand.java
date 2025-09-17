// KROK-08: MinuteHand – wskazówka minutowa.
// Kąt = minuty * 6° + ułamek od sekund (0.1° na każdą sekundę).

public class MinuteHand extends ClockHand {
    @Override
    public double angleDegrees() {
        return minute * 6.0 + second * 0.1; // 6°/min + 0.1°/s
    }
}
