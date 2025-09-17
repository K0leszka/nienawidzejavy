// KROK-21: VoteMap – kolory województw zależne od zwycięzcy w danym województwie.
// * Trzymamy mapę: woj -> kandydat zwycięzca; i mapę kandydat -> kolor.
// * Nadpisujemy colorFor tak, aby użyć odpowiedniego koloru.

import java.util.*;

public class VoteMap extends VoivodeshipMap {
    private final Map<String, Candidate> winnerByVoiv = new HashMap<>(); // klucz: nazwa woj. (lowercase)
    private final Map<Candidate, String> colorByCandidate = new HashMap<>();

    public void setWinnerFor(String voivodeship, Candidate candidate) {
        winnerByVoiv.put(voivodeship.trim().toLowerCase(java.util.Locale.ROOT), candidate);
    }

    public void setColorFor(Candidate c, String colorHex) {
        colorByCandidate.put(c, colorHex);
    }

    @Override
    protected String colorFor(String voiv) {
        Candidate c = winnerByVoiv.get(voiv.toLowerCase(java.util.Locale.ROOT));
        if (c == null) return "#bbbbbb"; // brak danych – jasnoszary
        return colorByCandidate.getOrDefault(c, "#000000");
    }
}
