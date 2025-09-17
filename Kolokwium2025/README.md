# README – Kolos: Wybory 2020 (JAVA OOP) – **mega-komentarze + KROK-xx**

Szukaj w plikach `.java` po tagach `// KROK-XX` (np. `KROK-05`).

## Pliki
- `src/Candidate.java` – **KROK-01** rekord kandydata (imię i nazwisko).
- `src/Election.java` – **KROK-02/03/4/13/15** lista kandydatów, wczytywanie, first/second turn, zwycięzca.
- `src/ElectionTurn.java` – **KROK-04/07/12/14/17** tura wyborów: głosy, wczytywanie, zwycięzca, dogrywka, summarize.
- `src/Vote.java` – **KROK-05/06/08/09/10/11/16/17** pojedynczy wynik gminy + narzędzia (filtrowanie, sumowanie, procenty, cache sumy).
- `src/NoWinnerException.java` – **KROK-12** wyjątek gdy nikt nie przekroczył 50%.
- `src/VoivodeshipMap.java` – **KROK-18/19/20**: zaktualizowana wersja klasy (dodane metody: lista województw, kolorowanie).
- `src/SelectableMap.java` – **KROK-20**: zaznaczanie wybranego województwa innym kolorem.
- `src/VoteMap.java` – **KROK-21**: kolorowanie województw wg zwycięzcy (mapa wyników).
- `src/Main.java` – testy wszystkiego (druk, sumy, mapy SVG).

## Dane wejściowe
- `kandydaci.txt` – lista kandydatów (jeden na linię) – **dokładnie w kolejności** odpowiadającej kolumnom w `1.csv`. fileciteturn0file0
- `1.csv` – pierwsza tura: kolumny **Gmina,Powiat,Województwo, ... głosy kandydatów ...** (w kolejności z `kandydaci.txt`).
- `2.csv` – druga tura: **Gmina,Powiat,Województwo, [kandydatA],[kandydatB]** – tylko dwie kolumny kandydatów.

## Szybki start
1) Umieść `kandydaci.txt`, `1.csv`, `2.csv` obok katalogu `src/` (lub wskaż ścieżki w `Main`).  
2) Skompiluj i odpal `Main`.  
3) Wygeneruje się m.in. `mapa_base.svg`, `mapa_select.svg`, `mapa_vote.svg` (w bieżącym folderze).

