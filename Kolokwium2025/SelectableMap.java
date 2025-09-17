// KROK-20: SelectableMap – podklasa VoivodeshipMap z możliwością zaznaczenia jednego województwa innym kolorem.
public class SelectableMap extends VoivodeshipMap {
    private String selected = null; // nazwa województwa (lowercase), które ma być wyróżnione

    // Wybór województwa do wyróżnienia
    public void select(String voivodeship) {
        if (voivodeship == null) selected = null;
        else selected = voivodeship.trim().toLowerCase(java.util.Locale.ROOT);
    }

    // Nadpisujemy kolorowanie: wybrane = czerwone, reszta = szare
    @Override
    protected String colorFor(String v) {
        if (selected != null && v.equalsIgnoreCase(selected)) {
            return "#cc0000"; // czerwony
        }
        return "#888888"; // domyślnie szary
    }
}
