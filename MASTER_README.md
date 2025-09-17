# Mega README – Nawigacja po kolokwiach (Ctrl+F friendly)

Szukaj zawsze po **tagach** w nawiasach: `[YYYY-KROK-XX]` albo po **słowach‑kluczach** (np. „LinkedList”).  
To README jest po to, żebyś mógł zrobić szybkie **Ctrl+F** i od razu trafić do właściwego pliku/kroku.

---

## 📦 Zestawy
- **[2022 – Produkty / koszyk / inflacja]** → folder: `java_kolos_src.zip` → `src/`
- **[2024 – Zegary / City / SVG]** → folder: `java_kolos_2024_src.zip` → `src/`
- **[WYBORY 2020 – tury / CSV / mapy]** → folder: `election_kolos_src.zip` → `src/`

---

## 🔎 Szybkie skróty wg haseł (Ctrl+F)
- `LinkedList` → [2024-KROK-09] (lista wskazówek w `AnalogClock`) **(lista, polimorfizm)**  
  (Jeśli potrzebujesz klasycznej kolejki/stacku na `LinkedList`, zajrzyj też do mojego „zestawu ratunkowego” – tag **KROK-05: Użycie LinkedList**.)
- `Stream / filter / map` → [2022-KROK-03], [2022-KROK-05], [WYBORY-KROK-17]
- `Comparator / sort` → [2024-KROK-05], [WYBORY-KROK-14]
- `I/O pliki (Files, BufferedReader)` → [2022-KROK-03], [2024-KROK-03/07], [WYBORY-KROK-03/07/18]
- `Wyjątek (custom)` → [2022-KROK-04], [WYBORY-KROK-12]
- `equals/hashCode` (w tych kolokwiach nie wymagane jawnie; używamy rekordów i map)
- `SVG` → [2024-KROK-07/08/10], [WYBORY-KROK-18/20/21]

> Tip: jeżeli chcesz konkretnie „jak zrobić X”, wpisz np. `KROK-08` + nazwę zestawu (2022/2024/WYBORY).

---

## 2022 – Produkty / koszyk / inflacja (plik: `java_kolos_src.zip`)
Folder: `src/`  
Testy: `Main.java`

- **[2022-KROK-01]** `Product.java` – klasa **abstrakcyjna**, `getPrice(int,int)`, indeksowanie miesiąca, magazyn produktów (`addProducts`, `clearProducts`, `byPrefix`).
- **[2022-KROK-02]** `NonFoodProduct.java` – fabryka `fromCsv(...)`, jeden wektor cen, `getPrice(...)`.
- **[2022-KROK-03]** `FoodProduct.java` – wczytanie **województw**, średnia z cen, `getPrice(y,m,provincePrefix)`.
- **[2022-KROK-04]** `AmbigiousProductException.java` – wiele dopasowań prefiksu.
- **[2022-KROK-05]** `Cart.java` – koszyk: `add`, `getPrice`, `getInflation`.
- **[2022-KROK-06]** `Main.java` – demo: wczytanie `data/`, testy `byPrefix`, `FoodProduct`, koszyk.

**Słowa‑klucze**: `Stream`, `Files.write`, `Files.newBufferedReader`, `Comparator.comparing`

---

## 2024 – Zegary / City / SVG (plik: `java_kolos_2024_src.zip`)
Folder: `src/`  
Testy: `Main.java`

- **[2024-KROK-01]** `Clock.java` – **abstrakcyjny zegar**, tryb 24h/12h, walidacje, `setTime(...)`, `setCurrentTime()` (offset miasta), `toString()`.
- **[2024-KROK-02]** `DigitalClock.java` – dziedziczenie bez zmian (do testów z tabeli).
- **[2024-KROK-03]** `City.java` – `parseLine`, `parseFile` dla `strefy.csv` (N/S/E/W), `toString()`.
- **[2024-KROK-04]** `Clock` ↔ `City` – pole `City` w zegarze, konstruktor z miastem.
- **[2024-KROK-05]** `City.sortByOffsetThenName(...)` – sortowanie i „teoretyczny offset” z długości geogr.
- **[2024-KROK-06]** `Main.java` – testy `DigitalClock` (krawędziowe godziny).
- **[2024-KROK-07]** `AnalogClock.java` – pełny **SVG** zegara, `toSvg`, `saveSvg`.
- **[2024-KROK-08]** `ClockHand.java` + `Second/Minute/HourHand.java` – kąty: 6°, 6°+0.1°/s, 30°+0.5°/min+…
- **[2024-KROK-09]** `AnalogClock` – **finalna polimorficzna lista** 3 wskazówek, synchronizacja z czasem.
- **[2024-KROK-10]** `AnalogClock.saveClocksForCities(...)` – generowanie wielu plików **SVG**.

**Słowa‑klucze**: `LinkedList` (zastosowanie listy polimorficznej), `transform="rotate(...)"`, `Instant`, `ZoneOffset`

---

## WYBORY 2020 – 1/2 tura / CSV / mapy (plik: `election_kolos_src.zip`)
Folder: `src/`  
Testy: `Main.java`

- **[WYBORY-KROK-01]** `Candidate.java` – rekord kandydata.
- **[WYBORY-KROK-02]** `Election.java` – pole `candidates` + metoda zwracająca **kopię listy** (te same referencje).
- **[WYBORY-KROK-03]** `Election.populateCandidates(...)` + `populate(...)` – wczytanie kandydatów i dalsze kroki.
- **[WYBORY-KROK-04]** `ElectionTurn.java` – konstruktor z listą kandydatów; w `Election` pola `firstTurn`, `secondTurn` (na start `null`).
- **[WYBORY-KROK-05]** `Vote.java` – `Map<Candidate,Long> votesForCandidate`, `List<String> location=[woj,pow,gmi]`.
- **[WYBORY-KROK-06]** `Vote.fromCsvLine(...)` – parsuje **1.csv / 2.csv** do `Vote`.
- **[WYBORY-KROK-07]** `ElectionTurn.populate(path)` – wczytuje listę `Vote`; w `Election.populate()` wywołanie dla `1.csv`.
- **[WYBORY-KROK-08]** `Vote.summarize(List<Vote>)` – suma głosów (location = puste).
- **[WYBORY-KROK-09]** `Vote.votes(c)`, `Vote.percentage(c)` – liczba i procent.
- **[WYBORY-KROK-10]** `Vote.toString()` – wypis procentów dla kandydatów.
- **[WYBORY-KROK-11]** cache sumy głosów (liczone raz).
- **[WYBORY-KROK-12]** `ElectionTurn.winner()` – >50% albo `NoWinnerException`.
- **[WYBORY-KROK-13]** `Election.winner` – ustawiany w `populate()` po 1. turze, jeśli jest >50%.
- **[WYBORY-KROK-14]** `ElectionTurn.runoffCandidates()` – TOP2.
- **[WYBORY-KROK-15]** w `populate()` – jeśli brak zwycięzcy, zrób `secondTurn` z TOP2 + `2.csv` → zwycięzca całości.
- **[WYBORY-KROK-16]** `Vote.filterByLocation(votes, ["woj",...])` – filtry: 1/2/3 poziomy.
- **[WYBORY-KROK-17]** `ElectionTurn.summarize()` i `summarize(location)` – najpierw filtr, potem suma.
- **[WYBORY-KROK-18]** `VoivodeshipMap.saveToSvg(...)` – generuje bazową mapę SVG.
- **[WYBORY-KROK-19]** `VoivodeshipMap.listVoivodeships()` + zebrane sumy 2. tury per województwo.
- **[WYBORY-KROK-20]** `SelectableMap` – `select(voiv)` i kolorowanie wybranego województwa (hook `colorFor`).
- **[WYBORY-KROK-21]** `VoteMap` – kolorowanie każdego woj. według zwycięzcy (mapa wyników).

**Słowa‑klucze**: `LinkedHashMap`, `BufferedReader`, `Files.newBufferedReader`, `record`, `RuntimeException`, `Comparator`

---

## 🧭 Przykłady wyszukiwania (Ctrl+F)
- Szukasz **LinkedList**? → wpisz `2024-KROK-09` (lista wskazówek; przykład polimorfizmu na liście).  
  (lub w „zestawie ratunkowym” – `KROK-05: Użycie LinkedList`).
- Szukasz **parsowania CSV**? → `2022-KROK-03`, `WYBORY-KROK-06`, `WYBORY-KROK-07`.
- Szukasz **custom exception**? → `2022-KROK-04`, `WYBORY-KROK-12`.
- Szukasz **SVG**? → `2024-KROK-07..10`, `WYBORY-KROK-18..21`.

---

## ℹ️ Uwaga organizacyjna
Każdy projekt ma swój własny `README.md` z bardziej szczegółowymi krokami i komentarzami „łopatologicznymi”.  
To **Mega README** służy jako indeks do szybkiego skakania po projektach i krokach.
