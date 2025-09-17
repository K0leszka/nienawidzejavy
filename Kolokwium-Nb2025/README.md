# Drawing App – README

## Opis
Aplikacja JavaFX pozwalająca rysować **linie, prostokąty i elipsy** na `Canvas`.  
Każda figura jest reprezentowana jako obiekt klasy (`Line`, `Rectangle`, `Ellipse`), a wszystkie są trzymane w jednej liście (`ArrayList<Shape>`).  
Program umożliwia zapis figur do pliku CSV i ponowne wczytanie ich z pliku.

## Klasy
- `Shape` – abstrakcyjna klasa, pola: współrzędne i kolor obrysu, metoda abstrakcyjna `draw(GraphicsContext gc)`.
- `FilledShape` – rozszerza `Shape`, dodaje kolor wypełnienia.
- `Line` – implementacja `draw()` do rysowania linii.
- `Rectangle` – implementacja `draw()` do rysowania prostokąta (stroke + fill).
- `Ellipse` – implementacja `draw()` do rysowania elipsy (stroke + fill).
- `DrawingController` – logika GUI (podpięta w FXML):
  - obsługa myszki (zapamiętywanie punktu startowego i końcowego),
  - tworzenie obiektów figur,
  - `redraw()` – odrysowanie wszystkich figur z listy,
  - obsługa przycisków: `New`, `Open`, `Save`.
- `Main` – klasa startowa, uruchamia aplikację JavaFX i ładuje widok `view.fxml`.

## Format CSV
Figury zapisywane są w formacie:
```
TYPE;x1;y1;x2;y2;stroke;fill
```
Przykłady:
- `LINE;10;10;50;50;#000000;-`
- `RECT;20;20;60;40;#000000;#FF0000`
- `ELLIPSE;30;30;70;50;#000000;#00FF00`

Pole `fill` dla linii to zawsze `-`.

## Jak uruchomić
1. Skompiluj wszystkie pliki w `src/`:
   ```bash
   javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml src/*.java
   ```
2. Uruchom aplikację:
   ```bash
   java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -cp src Main
   ```

## FXML (view.fxml)
Zawiera układ: przyciski (`New`, `Open`, `Save`), radio buttony do wyboru figury (`Line`, `Rectangle`, `Ellipse`), dwa `ColorPicker`y (kolor obrysu i wypełnienia), oraz `Canvas` do rysowania.
