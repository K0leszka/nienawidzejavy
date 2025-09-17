// KROK-08: HourHand – wskazówka godzinowa.
// Kąt = (godzina%12) * 30° + ułamek od minut (0.5°/min) + ułamek od sekund (~0.5/60 °/s).

public class HourHand extends ClockHand {
    @Override
    public double angleDegrees() {
        int h12 = hour % 12; // 0..11
        return h12 * 30.0 + minute * 0.5 + second * (0.5/60.0);
    }
}
