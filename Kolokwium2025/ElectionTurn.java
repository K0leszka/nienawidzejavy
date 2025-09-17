// KROK-04/07/12/14/17: Tura wyborów – trzyma listę kandydatów i listę głosów z gmin
// + narzędzia: wczytywanie z CSV, wyłanianie zwycięzcy (>50%), wskazanie dwóch do drugiej tury,
// + summarize() (bezargumentowe i z filtrem lokalizacji).

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class ElectionTurn {
    private final List<Candidate> candidates;   // nie kopiujemy obiektów – te same referencje
    private final List<Vote> votes = new ArrayList<>(); // KROK-07: lista wyników z gmin

    public ElectionTurn(List<Candidate> candidates) {
        // zapisujemy referencje (lista może być nową kopią; obiekty kandydatów są współdzielone)
        this.candidates = new ArrayList<>(candidates);
    }

    public List<Candidate> getCandidates() { return List.copyOf(candidates); }
    public List<Vote> getVotes() { return List.copyOf(votes); }

    // KROK-07: populate – wczytaj CSV i zapełnij votes
    public void populate(Path csvPath) {
        try (BufferedReader br = Files.newBufferedReader(csvPath)) {
            String header = br.readLine(); // pierwsza linia – nagłówki kolumn
            if (header == null) return;
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                votes.add(Vote.fromCsvLine(line, candidates));
            }
        } catch (IOException e) {
            throw new RuntimeException("Błąd czytania pliku: " + csvPath, e);
        }
    }

    // KROK-12: zwycięzca (>50%); w przeciwnym razie NoWinnerException
    public Candidate winner() {
        Vote sum = summarize(); // suma po wszystkich gminach
        long total = sum.totalVotes();
        for (Candidate c : candidates) {
            if (sum.percentage(c) > 50.0) return c;
        }
        throw new NoWinnerException("Brak zwycięzcy (>50%). Suma głosów=" + total);
    }

    // KROK-14: dwaj najlepsi (po liczbie głosów)
    public List<Candidate> runoffCandidates() {
        Vote sum = summarize();
        // posortuj wg głosów malejąco
        return candidates.stream()
                .sorted((a,b) -> Long.compare(sum.votes(b), sum.votes(a)))
                .limit(2)
                .collect(Collectors.toList());
    }

    // KROK-17: summarize – bez argumentów (cała tura)
    public Vote summarize() {
        return Vote.summarize(votes);
    }

    // KROK-17: summarize – z filtrem lokalizacji
    public Vote summarize(List<String> location) {
        List<Vote> filtered = Vote.filterByLocation(votes, location);
        return Vote.summarize(filtered, location);
    }
}
