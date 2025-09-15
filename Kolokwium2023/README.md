# Programowanie Obiektowe — Kolokwium 1

Implementacja kroków 1–16 z PDF „Programowanie obiektowe - kolokwium 1 (12.05.2023)”.  
Kod jest w Javie 17, projekt Maven.

## Zawartość
- `Point` — punkt 2D (x, y).
- `Polygon` — wielokąt z metodą `inside(Point)` (ray casting).
- `Land` — ląd (dziedziczy po `Polygon`) + lista miast i `addCity` z walidacją.
- `City` — miasto (kwadrat osiowo ustawiony), pole `center`, nazwa + port, zbiór `resources`.
- `Resource` — zasób (`Type {Coal, Wood, Fish}`) + pozycja.
- `MapParser` — prosty parser SVG (DOM) czytający lądy (`polygon` zielony), miasta (`rect` czerwony) i `text` (etykiety). Ma metody `addCitiesToLands()` i `matchLabelsToTowns()`.
- `Main` — przykład użycia: wczytuje `map.svg` i wypisuje lądy z miastami (⚓ dla portów).
- Testy JUnit: `PolygonInsideTest`, `CityExceptionTest`, `CityResourceParamTest`.

## Uruchomienie
```
mvn -q -DskipTests exec:java   # jeżeli dodasz plugin exec
mvn test                       # uruchom testy
```
Albo po prostu uruchom klasę `pl.kolokwium.Main` w IDE.

## Plik mapy
W katalogu `src/main/resources` jest `map.svg` skopiowany z dostarczonego pliku. Parser:
- lądy: `<polygon>` z zielonym wypełnieniem,
- miasta: `<rect>` z czerwonym wypełnieniem (środek = (x+width/2, y+height/2)),
- nazwy: `<text>`.

## Notatki
- `inside(Point)` używa półprostej w lewo i zlicza przecięcia (nieparzysta → wewnątrz).
- Port określany jest w `Land.evaluatePort(city)`: jeśli *którykolwiek* wierzchołek kwadratu miasta leży poza lądem, `city.port = true`.
- Dodawanie zasobów w `City.addResourcesInRange(...)`: Coal/Wood zawsze; Fish tylko jeśli miasto portowe.
