# README – Kolokwium OOP 2024 (Zegar, City, CSV, Analog/Digital) – **mega komentarze** + **KROK-y** do Ctrl+F

Szukaj po tagach `KROK-XX` (np. `KROK-03`) w plikach `.java`.

- **KROK-01 – `Clock` (klasa abstrakcyjna)**: przechowuje godzinę/minutę/sekundę, tryb 24h/12h, setter z walidacją, `setTime("HH:MM:SS")`, `setCurrentTime()` (wg *letniego* przesunięcia miasta), `toString()` (24h vs 12h).
- **KROK-02 – `DigitalClock`**: dziedziczy po `Clock`. Użyte do testów z tabeli (np. `00:00:00`, `23:59:00`).
- **KROK-03 – `City` + `parseLine`/`parseFile`**: wczytuje `strefy.csv` (Stolica, Strefa czasowa letnia, Szerokość, Długość). Parsuje `N/S/E/W` na znak współrzędnych.
- **KROK-04 – `Clock` + `City`**: dodane prywatne pole `City` i konstruktor przyjmujący `City`. Metody używają przesunięcia godzinowego miasta.
- **KROK-05 – sortowanie miast**: statyczne narzędzie w `City` do sortowania po *strefie letniej*, a przy remisie po nazwie (demo w `Main`). Dodatkowo metoda pomocnicza licząca przesunięcie z długości geogr. (`round(longitude/15)`).
- **KROK-06 – testy `DigitalClock`**: przykładowe czasy z tabeli (0→`00:00:00`, skrajne wartości, itd.).
- **KROK-07 – `AnalogClock`**: dziedziczy po `Clock`. Zwraca pełny SVG (tarcza + 3 wskazówki). `saveSvg(Path)` zapisuje plik SVG.
- **KROK-08 – `ClockHand` (abstr.) + `SecondHand`/`MinuteHand`/`HourHand`**: obliczanie kątów w stopniach i generowanie pojedynczej `<line ... transform="rotate(...)">`.
- **KROK-09 – lista wskazówek w `AnalogClock`**: prywatna, finalna, polimorficzna lista 3 sztuk; zmiana czasu → aktualizacja wszystkich wskazówek.
- **KROK-10 – generowanie wielu zegarów**: `AnalogClock.saveClocksForCities(...)` tworzy pliki SVG dla kolejnych miast i ustawia czas wg strefy letniej. Demonstracja w `Main`.

> **Uwaga o strefie czasowej:** kolokwium podaje kolumnę „Strefa czasowa letnia” → traktujemy ją jako *liczbę godzin względem UTC* (np. Berlin=2, Londyn=1). Do `setCurrentTime()` bierzemy `Instant.now(UTC)` i dodajemy offset miasta.

> **Kąty w SVG:** godzina 12 to 0°, rośnie *zgodnie z ruchem wskazówek* (SVG `rotate(angle)` działa jak obrót układu współrzędnych). Sekunda/minuta: `6°` na jednostkę; godzina: `30°` na godzinę + ułamki od minut/sekund.

Pliki:
- `src/Clock.java`
- `src/DigitalClock.java`
- `src/City.java`
- `src/ClockHand.java`, `src/SecondHand.java`, `src/MinuteHand.java`, `src/HourHand.java`
- `src/AnalogClock.java`
- `src/Main.java`

Dane wejściowe:
- Umieść `strefy.csv` obok wykonywania programu (ścieżka podana w `Main`).

- `zegar.svg` z paczki jest *wzorem* – nasz generator wypisuje równoważny SVG (tarcza + 3 wskazówki z prawidłowymi kątami).
