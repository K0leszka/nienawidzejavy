// Main – testuje i demonstruje WSZYSTKIE kroki.
// Ustaw ścieżki do plików poniżej, a potem uruchom.

import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            // ŚCIEŻKI DO DANYCH (zmień jeśli trzeba)
            Path pathCandidates = Paths.get("kandydaci.txt");
            Path pathTurn1 = Paths.get("1.csv");
            Path pathTurn2 = Paths.get("2.csv");

            // KROK-03/04/13/15: wczytaj wybory, tury, zwycięzcę
            Election election = new Election();
            election.populate(pathCandidates, pathTurn1, pathTurn2);

            System.out.println("Zwycięzca wyborów: " + election.getWinner().name());

            // KROK-10/11: toString na sumie głosów 1. tury
            Vote sumTurn1 = election.getFirstTurn().summarize();
            System.out.println("\\n--- Suma 1. tury (procenty) ---");
            System.out.println(sumTurn1);

            // KROK-16/17: filtr i summarize dla konkretnego województwa/powiatu/gminy
            List<String> locWoj = List.of("lubelskie");
            Vote wojSummary = election.getFirstTurn().summarize(locWoj);
            System.out.println("\\nSuma w woj. " + locWoj + ":");
            System.out.println(wojSummary);

            List<String> locPow = List.of("dolnośląskie","bolesławiecki");
            System.out.println("\\nSuma w " + locPow + ":");
            System.out.println(election.getFirstTurn().summarize(locPow));

            List<String> locGmi = List.of("dolnośląskie","dzierżoniowski","m. Dzierżoniów");
            System.out.println("\\nSuma w " + locGmi + ":");
            System.out.println(election.getFirstTurn().summarize(locGmi));

            // KROK-18: podstawowa mapa (oryginalna) – zapis
            VoivodeshipMap baseMap = new VoivodeshipMap();
            baseMap.saveToSvg("mapa_base.svg");

            // KROK-19: lista województw + podsumowanie 2. tury dla KAŻDEGO województwa
            List<String> voivs = VoivodeshipMap.listVoivodeships();
            Map<String, Vote> summaryByVoiv = new LinkedHashMap<>();
            for (String v : voivs) {
                Vote vSum = election.getSecondTurn().summarize(List.of(v));
                summaryByVoiv.put(v, vSum);
            }
            System.out.println("\\nPodsumowanie 2. tury po województwach: " + summaryByVoiv.size() + " pozycji");

            // KROK-20: SelectableMap – zaznacz jedno województwo
            SelectableMap sel = new SelectableMap();
            sel.select("mazowieckie");
            sel.saveToSvg("mapa_select.svg");

            // KROK-21: VoteMap – kolorujemy wg zwycięzcy w województwie
            // Weźmy dwóch finalistów (druga tura)
            List<Candidate> finalists = election.getSecondTurn().getCandidates();
            Candidate a = finalists.get(0);
            Candidate b = finalists.get(1);

            VoteMap vm = new VoteMap();
            vm.setColorFor(a, "#0057B8"); // niebieski
            vm.setColorFor(b, "#DC143C"); // czerwony
            for (Map.Entry<String, Vote> e : summaryByVoiv.entrySet()) {
                String voiv = e.getKey();
                Vote sv = e.getValue();
                Candidate winnerHere = (sv.percentage(a) >= sv.percentage(b)) ? a : b;
                vm.setWinnerFor(voiv, winnerHere);
            }
            vm.saveToSvg("mapa_vote.svg");

            System.out.println("\\nGotowe. Zapisano: mapa_base.svg, mapa_select.svg, mapa_vote.svg");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
