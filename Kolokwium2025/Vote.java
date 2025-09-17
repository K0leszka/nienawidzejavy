// KROK-05..11..16..17: Klasa Vote – przechowuje wynik głosowania dla JEDNEJ lokalizacji (gminy)
// Mega-komentarze: tłumaczę każdy element powoli i prosto.

import java.util.*;
import java.util.stream.Collectors;

public class Vote {
    // --- [KROK-05] STAN OBIEKTU ---
    // votesForCandidate: ile głosów uzyskał każdy kandydat W TEJ gminie.
    // • kluczem jest Candidate (referencję do obiektu kandydata przekazujemy z zewnątrz),
    // • wartością jest liczba uzyskanych głosów (long, bo może być > 2^31).
    private final Map<Candidate, Long> votesForCandidate = new LinkedHashMap<>();

    // location: dokładnie trzy pola w kolejności: [województwo, powiat, gmina]
    // (w plikach CSV kolejność kolumn to Gmina,Powiat,Województwo – dlatego przy parsowaniu odwrócimy).
    private final List<String> location = new ArrayList<>(3);

    // --- [KROK-11] CACHE SUMY ---
    // suma wszystkich głosów (żeby nie liczyć wielokrotnie w toString/percentage) – leniwie wyliczana i zapamiętywana.
    private Long cachedTotal = null;

    // Pusty konstruktor pomocniczy – używamy tylko wewnętrznie (np. w summarize)
    private Vote() {}

    // Prosty fabryczny konstruktor – używany w fromCsvLine
    private Vote(List<String> location, Map<Candidate, Long> map) {
        this.location.addAll(location);
        this.votesForCandidate.putAll(map);
    }

    // --- [KROK-06] fromCsvLine – tworzy Vote z jednego wiersza CSV (pierwsza lub druga tura) ---
    public static Vote fromCsvLine(String csvLine, List<Candidate> candidatesInOrder) {
        // 1) Rozbij wiersz po przecinku.
        String[] cols = csvLine.split(",");
        // Minimalna liczba kolumn: 3 (Gmina,Powiat,Województwo) + co najmniej 2 kolumny kandydatów
        if (cols.length < 5) {
            throw new IllegalArgumentException("Za mało kolumn w wierszu CSV: " + csvLine);
        }

        String gmina = cols[0].trim();
        String powiat = cols[1].trim();
        String woj = cols[2].trim();

        // Zapisujemy lokalizację jako [województwo, powiat, gmina] – tak wymaga treść zadania.
        List<String> loc = List.of(woj, powiat, gmina);

        Map<Candidate, Long> map = new LinkedHashMap<>();
        // Od 4-tej kolumny do końca – liczby głosów, w kolejności kandydatów przekazanych w argumencie.
        int expected = candidatesInOrder.size();
        int available = cols.length - 3;
        if (available < expected) {
            // Jeśli wiersz ma mniej kolumn niż kandydatów, to albo to 2. tura (2 kandydatów) albo plik jest błędny.
            // Zaciśniemy listę kandydatów do tylu kolumn ile jest.
            expected = available;
        }
        for (int i = 0; i < expected; i++) {
            Candidate cand = candidatesInOrder.get(i);
            long votes = Long.parseLong(cols[3 + i].trim());
            map.put(cand, votes);
        }
        return new Vote(loc, map);
    }

    // --- [KROK-08] summarize – sumuje listę Vote w JEDEN ---
    public static Vote summarize(List<Vote> votes) {
        Vote result = new Vote();
        // nowa mapa: te same klucze co w wejściu (zakładamy spójność listy kandydatów)
        Map<Candidate, Long> sum = new LinkedHashMap<>();
        for (Vote v : votes) {
            for (Map.Entry<Candidate, Long> e : v.votesForCandidate.entrySet()) {
                sum.merge(e.getKey(), e.getValue(), Long::sum);
            }
        }
        result.votesForCandidate.putAll(sum);
        // KROK-08: location puste – to podsumowanie wielu gmin
        result.location.clear();
        // cache sumy policzymy leniwie
        return result;
    }

    // --- [KROK-17] summarize z nadaniem location (np. województwa) ---
    public static Vote summarize(List<Vote> votes, List<String> location) {
        Vote res = summarize(votes);
        res.location.clear();
        res.location.addAll(location);
        return res;
    }

    // --- [KROK-09] votes() i percentage() dla danego kandydata ---
    public long votes(Candidate c) {
        return votesForCandidate.getOrDefault(c, 0L);
    }

    public double percentage(Candidate c) {
        long total = totalVotes();
        if (total == 0) return 0.0;
        return (votes(c) * 100.0) / total;
    }

    // --- [KROK-11] Liczenie sumy tylko raz (cache) ---
    public long totalVotes() {
        if (cachedTotal == null) {
            cachedTotal = votesForCandidate.values().stream().mapToLong(Long::longValue).sum();
        }
        return cachedTotal;
    }

    // --- [KROK-10] Ładny wydruk: każdy kandydat + procent ---
    @Override public String toString() {
        return votesForCandidate.entrySet().stream()
                .map(e -> e.getKey().name() + ": " + String.format(java.util.Locale.US, "%.2f%%", percentage(e.getKey())))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    // Dostęp do lokalizacji (kopie defensywne, by nie wyciekał stan mutowalny)
    public List<String> getLocation() { return List.copyOf(location); }
    public String voivodeship() { return location.isEmpty() ? "" : location.get(0); }
    public String powiat() { return location.size() < 2 ? "" : location.get(1); }
    public String gmina() { return location.size() < 3 ? "" : location.get(2); }

    // --- [KROK-16] Filtrowanie po lokalizacji ---
    public static List<Vote> filterByLocation(List<Vote> all, List<String> loc) {
        // Wejściowa lista lokacji może mieć 1, 2 lub 3 elementy: [woj], [woj, powiat], [woj, powiat, gmina]
        List<String> norm = new ArrayList<>();
        for (String s : loc) norm.add(s.trim().toLowerCase(java.util.Locale.ROOT));

        return all.stream().filter(v -> {
            String woj = v.voivodeship().toLowerCase(java.util.Locale.ROOT);
            String pow = v.powiat().toLowerCase(java.util.Locale.ROOT);
            String gmi = v.gmina().toLowerCase(java.util.Locale.ROOT);
            if (norm.size() == 1) {
                return woj.equals(norm.get(0));
            } else if (norm.size() == 2) {
                return woj.equals(norm.get(0)) && pow.equals(norm.get(1));
            } else if (norm.size() == 3) {
                return woj.equals(norm.get(0)) && pow.equals(norm.get(1)) && gmi.equals(norm.get(2));
            } else {
                return false;
            }
        }).collect(Collectors.toList());
    }

    // Pomocnicze: dostęp do wewnętrznej mapy (tylko do odczytu)
    public Map<Candidate, Long> asMap() { return Collections.unmodifiableMap(votesForCandidate); }
}
