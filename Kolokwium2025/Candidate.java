// KROK-01: Rekord kandydata – nowoczesny, zwięzły zapis klasy danych (Java 16+).
// "record" automatycznie generuje: pola final, konstruktor, gettery, equals/hashCode, toString.
public record Candidate(String name) {}
