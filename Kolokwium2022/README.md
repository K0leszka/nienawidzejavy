# README – Kolokwium: produkty, ceny, koszyk (mega-komentarze + CTRL+F)

Szukaj po znacznikach **KROK-XX** (np. `KROK-05`) żeby błyskawicznie znaleźć fragmenty w kodzie.

## KROK-00: Przygotowanie danych
- Rozpakuj `data.zip` tak, aby powstały katalogi: `data/nonfood` oraz `data/food` (w tym samym katalogu co `.java`).
- Każdy plik `.csv` zawiera:
  - **nonfood**: 1) nazwa produktu, 2) nagłówek z miesiącami, 3) *jedna* linia z cenami miesięcznymi (od 2010 I do 2022 III).
  - **food**: 1) nazwa produktu, 2) nagłówek `Jednostka terytorialna;...`, 3+) wiersze dla **województw** z cenami miesięcznymi.

## KROK-01: Klasa abstrakcyjna `Product`
- Pole **`name`** (nazwa z pierwszej linii CSV) i **`monthsCount`** (liczba miesięcy).
- Metody pomocnicze do indeksowania miesiąca: `indexOf(year, month)` → `(year-2010)*12 + (month-1)` (walidacja zakresu).
- **Abstrakcyjna**: `double getPrice(int year, int month)` – zwraca cenę danego produktu w danym miesiącu.
- **Statyczne**: prywatna lista `products`, `clearProducts()`, `addProducts(Path dir, ProductFactory factory)`, `List<Product> getProducts()`.
- **Szukanie po prefiksie**: `byPrefix(String prefix)`:
  - 0 dopasowań → **IndexOutOfBoundsException**,
  - 1 dopasowanie → zwraca produkt,
  - >1 dopasowań → **AmbigiousProductException** z listą nazw.
- Interfejs funkcyjny `ProductFactory` – tak, aby działały referencje do metod `FoodProduct::fromCsv` i `NonFoodProduct::fromCsv`.

## KROK-02: `NonFoodProduct` (dziedziczy po `Product`)
- Statyczna fabryka `fromCsv(Path)`: czyta 1) nazwę, 2) pomija nagłówek, 3) linię z cenami (zamień `,` → `.`).
- Implementuje `getPrice(year, month)` na podstawie jednego wektora cen.

## KROK-03: `FoodProduct` (dziedziczy po `Product`)
- Statyczna fabryka `fromCsv(Path)`: czyta 1) nazwę, 2) nagłówek, 3+) wiersze województw.
- Przechowuje mapę **województwo → ceny[]** oraz **średni** wektor (arytmetyczna średnia po województwach).
- `getPrice(int year, int month)` → **średnia** cena we wszystkich województwach.
- `getPrice(int year, int month, String provincePrefix)` → cena w **województwie** wskazanym **prefiksem** (case-insensitive). 
  - 0 dopasowań → **IndexOutOfBoundsException**, >1 → **AmbigiousProductException** z listą województw.

## KROK-04: `AmbigiousProductException`
- Wyjątek (unchecked) przyjmujący `List<String>` (kandydaci) i wypisujący je w komunikacie.

## KROK-05: `Cart` (koszyk)
- `add(Product p, double amount)` – dodaje do koszyka ilość (np. sztuki/kg/l).
- `getPrice(int year, int month)` – suma `amount * p.getPrice(year, month)` po wszystkich pozycjach.
- `getInflation(int y1, m1, y2, m2)` – **% zmiany**: `(price(y2,m2) - price(y1,m1)) / price(y1,m1) * 100`.
- Działa z `FoodProduct` (średnia) i `NonFoodProduct` (pojedynczy wektor).

## KROK-06: `Main` – demonstracja
- `Product.clearProducts()`
- `Product.addProducts(Paths.get("data/nonfood"), NonFoodProduct::fromCsv)`
- `Product.addProducts(Paths.get("data/food"), FoodProduct::fromCsv)`
- Test `FoodProduct::getPrice` (2- i 3-argumentowa) – np. `"MAZO"` → `MAZOWIECKIE`.
- Test `Product.byPrefix(...)` dla 3 przypadków:
  - 0 dopasowań: `"Abc"` → **IndexOutOfBoundsException**
  - 1 dopasowanie: `"Bu"` → **"Buraki - za 1 kg"**
  - >1 dopasowań: np. `"Ja"` → **lista** `"Jabłka - za 1 kg", "Jaja kurze świeże - za 1 szt"`
- Utwórz `Cart`, dodaj kilka produktów, wywołaj `getPrice` i `getInflation`.

> Wszystkie pliki mają **mega-komentarze** linia po linii, tak abyś mógł szybko ogarnąć co i jak.

Powodzenia! :)
