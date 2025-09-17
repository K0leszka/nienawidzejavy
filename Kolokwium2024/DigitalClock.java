// KROK-02: DigitalClock – prosta, „cyfrowa” wersja zegara.
// Dziedziczy CAŁĄ logikę z Clock – tu nie musimy nic zmieniać, ale mamy klasę pod testy.

public class DigitalClock extends Clock {
    public DigitalClock(boolean is24h, City city) {
        super(is24h, city);
    }
    public DigitalClock(boolean is24h) {
        super(is24h);
    }
}
