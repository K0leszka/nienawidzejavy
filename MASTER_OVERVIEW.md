# MASTER OVERVIEW – Opisy projektów / kolokwiów

## 📦 2022 – Produkty, Koszyk i Inflacja
To kolokwium skupia się na **dziedziczeniu klas i obsłudze wyjątków** przy pracy z plikami CSV.  
Główne elementy:
- **Hierarchia produktów**:
  - `Product` – klasa abstrakcyjna, wspólne pola (np. nazwa, ceny miesięczne), logika do przeliczania indeksów miesięcy.
  - `NonFoodProduct` – produkty niefospożywcze, każdy ma jeden wektor cen.
  - `FoodProduct` – produkty spożywcze, posiadają ceny zależne od województw. Obsługa prefiksów nazw województw (np. `ma` → „mazowieckie”). Jeśli prefiks pasuje do kilku województw, rzucany jest `AmbigiousProductException`.
- **Koszyk (`Cart`)** – przykładowa kolekcja produktów:
  - dodawanie produktów do koszyka,
  - liczenie łącznej ceny w danym miesiącu,
  - wyliczanie inflacji (zmiana cen w czasie).
- **Obsługa danych** – odczytywanie plików CSV, wykorzystanie `Stream`ów i `Files`.
- **Ćwiczone tematy**: dziedziczenie, wyjątki, strumienie, mapy, parsowanie plików, polimorfizm.

---

## 📦 2024 – Zegary, Miasta i SVG
Kolokwium zorientowane na **czas, daty i grafikę wektorową (SVG)**.
- **Abstrakcyjny zegar (`Clock`)**:
  - przechowywanie godziny/minuty/sekundy,
  - tryb 24h/12h,
  - ustawianie aktualnego czasu na podstawie strefy miasta.
- **Miasta (`City`)**:
  - parsowanie pliku `strefy.csv` (nazwa, współrzędne, strefa czasowa),
  - sortowanie miast wg offsetu i nazwy,
  - konwersje między `LocalDateTime` a strefami czasowymi.
- **Zegar cyfrowy (`DigitalClock`)** – prosty podklasowy test (wyświetlanie czasu jako tekst).
- **Zegar analogowy (`AnalogClock`)**:
  - polimorficzna lista wskazówek (`ClockHand` abstrakcyjna, `SecondHand`, `MinuteHand`, `HourHand`),
  - liczenie kątów wskazówek na podstawie czasu (sekunda=6°, minuta=6°+0.1°/s, godzina=30°+0.5°/min + …),
  - generowanie grafiki SVG (`toSvg()`, `saveSvg()`),
  - zapisywanie wielu zegarów do plików (`saveClocksForCities`).
- **Ćwiczone tematy**: daty i czasy w Javie (`LocalDateTime`, `ZoneOffset`), grafika wektorowa, polimorfizm (lista wskazówek), obsługa plików.

---

## 📦 WYBORY 2020 – CSV, Tury i Mapy
Najbardziej „realne” zadanie – analiza wyników wyborów prezydenckich.  
Łączy **CSV, kolekcje, wyjątki i grafikę (mapy województw)**.
- **Kandydaci (`Candidate`)** – rekord przechowujący nazwę.
- **Głosy (`Vote`)**:
  - przechowuje liczbę głosów na kandydata w danej gminie,
  - potrafi policzyć procenty,
  - `summarize()` – zsumowanie wielu głosów,
  - `filterByLocation()` – wybór tylko z województwa/powiatu/gminy,
  - metoda `toString()` wypisuje procentowe wyniki.
- **Tura wyborów (`ElectionTurn`)**:
  - wczytywanie danych z plików CSV (`1.csv`, `2.csv`),
  - `winner()` – sprawdza czy ktoś ma >50%, w przeciwnym razie rzuca `NoWinnerException`,
  - `runoffCandidates()` – wskazuje dwóch najlepszych kandydatów,
  - `summarize()` – podsumowania głosów ogólnie albo per lokalizacja.
- **Całe wybory (`Election`)**:
  - ładowanie kandydatów,
  - uruchomienie I tury,
  - jeśli nie ma zwycięzcy → tworzy II turę i wczytuje `2.csv`,
  - ostateczne wyznaczenie zwycięzcy.
- **Mapa województw (`VoivodeshipMap`)**:
  - rysowanie konturów województw w SVG,
  - `SelectableMap` – możliwość podświetlenia jednego województwa,
  - `VoteMap` – kolorowanie województw wg zwycięzcy w danym regionie.
- **Ćwiczone tematy**: CSV i kolekcje (`Map`, `List`), wyjątki własne, agregacja danych, polimorfizm, rozszerzanie klas, generowanie SVG.

---

## 📦 Drawing App – Canvas, Figury i CSV
Aplikacja JavaFX do rysowania prostych figur.  
Pokazuje **polimorfizm, dziedziczenie i GUI**.
- **Hierarchia figur**:
  - `Shape` (abstrakcyjna) – wspólne pola: współrzędne, kolor obrysu.
  - `FilledShape` – dodatkowo kolor wypełnienia.
  - `Line`, `Rectangle`, `Ellipse` – implementacje `draw(...)` na `Canvas`.
- **Kontroler (`DrawingController`)**:
  - obsługa myszki (`pressed` → punkt startu, `released` → tworzenie figury),
  - lista figur (`ArrayList<Shape>`),
  - `redraw()` – odrysowanie wszystkich figur na canvasie,
  - obsługa przycisków:
    - **New** → czyszczenie,
    - **Open** → wybór pliku i wczytanie figur z CSV,
    - **Save** → zapis figur do CSV.
- **Format CSV**: `TYPE;x1;y1;x2;y2;stroke;fill`
  - np. `RECT;10;10;50;50;#000000;#FF0000`
  - linie zapisują „-” w polu fill.
- **Main.java** – uruchomienie JavaFX, załadowanie widoku FXML.
- **Ćwiczone tematy**: JavaFX (Canvas, ColorPicker, FileChooser), dziedziczenie i polimorfizm, zapisywanie danych do pliku.

---
