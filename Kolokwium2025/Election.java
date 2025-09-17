// KROK-02/03/04/13/15: Klasa Election – zarządza kandydatami i turami wyborów.
// • prywatne pole candidates (lista),
// • populateCandidates(path) – czyta kandydatów z pliku,
// • populate() – ładuje 1.csv do firstTurn, sprawdza winner; jeśli brak → tworzy secondTurn z dwójką najlepszych i ładuje 2.csv,
// • pole winner + akcesor.

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Election {
    // --- [KROK-02] kandydaci – prywatne pole ---
    private final List<Candidate> candidates = new ArrayList<>();

    // kopiująca metoda dostępu: NOWA lista, ale te same referencje do obiektów
    public List<Candidate> getCandidatesCopy() {
        return new ArrayList<>(candidates);
    }

    // --- [KROK-04] tury ---
    private ElectionTurn firstTurn;
    private ElectionTurn secondTurn; // początkowo null (tworzony tylko przy dogrywce)

    public ElectionTurn getFirstTurn() { return firstTurn; }
    public ElectionTurn getSecondTurn() { return secondTurn; }

    // --- [KROK-13] zwycięzca całości ---
    private Candidate winner;
    public Candidate getWinner() { return winner; }

    // --- [KROK-03] wczytywanie kandydatów ---
    public void populateCandidates(Path candidatesFile) {
        try {
            List<String> lines = Files.readAllLines(candidatesFile);
            candidates.clear();
            for (String l : lines) {
                String name = l.trim();
                if (!name.isBlank()) candidates.add(new Candidate(name));
            }
        } catch (IOException e) {
            throw new RuntimeException("Błąd czytania pliku kandydatów: " + candidatesFile, e);
        }
    }

    // KROK-03: populate – ładowanie wszystkiego
    public void populate(Path candidatesFile, Path csvTurn1, Path csvTurn2) {
        // 1) kandydaci
        populateCandidates(candidatesFile);

        // 2) pierwsza tura
        firstTurn = new ElectionTurn(getCandidatesCopy());
        firstTurn.populate(csvTurn1);

        // 3) spróbuj wyłonić zwycięzcę
        try {
            winner = firstTurn.winner(); // jeśli >50%, super – koniec
        } catch (NoWinnerException e) {
            // 4) druga tura: wybierz dwóch, utwórz secondTurn, załaduj 2.csv
            List<Candidate> two = firstTurn.runoffCandidates();
            secondTurn = new ElectionTurn(two);
            secondTurn.populate(csvTurn2);
            // zwycięzca wyborów to zwycięzca 2. tury
            winner = secondTurn.winner();
        }
    }
}
