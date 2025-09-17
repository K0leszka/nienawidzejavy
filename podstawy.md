# README (spis treści do Ctrl+F)

Szukaj fraz **KROK-XX** (np. `KROK-05`) żeby błyskawicznie trafić do konkretu.

- **KROK-01**: Klasa, pola, metody, obiekt (`class`, `new`)
- **KROK-02**: Konstruktory + przeciążanie (overloading)
- **KROK-03**: Gettery/Settery + enkapsulacja
- **KROK-04**: `toString()` – ładny wydruk obiektu
- **KROK-05**: **Użycie `LinkedList`** (dodawanie, usuwanie, iteracja)
- **KROK-06**: Dziedziczenie (`extends`) + `super`
- **KROK-07**: Klasa abstrakcyjna vs. interfejs (`abstract`, `interface`, `implements`)
- **KROK-08**: Polimorfizm – wywołanie właściwej metody w runtime
- **KROK-09**: Generyki – własna klasa i metoda generyczna
- **KROK-10**: `equals()` i `hashCode()` – poprawne porównywanie obiektów (np. w `HashSet`)
- **KROK-11**: `Comparable` i `Comparator` – sortowanie obiektów
- **KROK-12**: Wyjątki – `try/catch`, własny wyjątek, `throws`
- **KROK-13**: Pliki – czytanie/zapisywanie (`Files`, `BufferedReader`, `try-with-resources`)
- **KROK-14**: Strumienie (`Stream`) – `filter`, `map`, `collect`
- **KROK-15**: Kolekcje – `ArrayList`, `HashMap`, `HashSet` – najważniejsze operacje
- **KROK-16**: Wzorce: Singleton + Factory (krótko i praktycznie)
- **KROK-17**: Builder – czytelne tworzenie obiektów
- **KROK-18**: Enum – bezpieczne stałe z logiką
- **KROK-19**: Serializacja (`Serializable`) – zapis/odczyt obiektów binarnie
- **KROK-20**: Mini-projekt „Biblioteka” – łączenie tematów

> **Tip**: Na kolosie często jest: utwórz klasy domenowe, dziedziczenie, sortuj listę, przefiltruj, zapisz do pliku. Poniżej masz gotowce z mega-komentarzami.

---

## KROK-01: Klasa, pola, metody, obiekt

**Jak to zrobić:**
1. Tworzysz plik `Osoba.java`.
2. Definiujesz pola (prywatne), metody (publiczne), konstruktor.
3. Tworzysz obiekt w `main` i wywołujesz metody.

```java
// Plik: Osoba.java
// Mega-komentarze: wszystko tłumaczę linia po linii
public class Osoba { // deklaruję klasę o nazwie Osoba
    // --- POLA (stan obiektu) ---
    private String imie;  // prywatne = enkapsulacja, dostęp przez get/set
    private int wiek;

    // --- KONSTRUKTOR ---
    public Osoba(String imie, int wiek) { // wywoływany przy new Osoba(...)
        this.imie = imie; // this.imie = pole klasy; imie = parametr konstruktora
        this.wiek = wiek;
    }

    // --- METODY ---
    public void przywitaj() {
        System.out.println("Cześć, jestem " + imie + " i mam " + wiek + " lat.");
    }
}

// Plik: Main01.java
public class Main01 {
    public static void main(String[] args) {
        Osoba o = new Osoba("Ala", 20); // tworzę obiekt (instancję) klasy Osoba
        o.przywitaj();                    // wywołuję metodę na obiekcie
    }
}
```

---

## KROK-02: Konstruktory + przeciążanie

```java
public class Punkt {
    private int x, y;

    // konstruktor podstawowy
    public Punkt(int x, int y) {
        this.x = x; this.y = y;
    }

    // przeciążony konstruktor (inne parametry) – ustawia (0,0)
    public Punkt() {
        this(0, 0); // wywołuje inny konstruktor tej samej klasy
    }
}
```

---

## KROK-03: Gettery/Settery + enkapsulacja

```java
public class Konto {
    private double saldo; // pole prywatne – nie można ustawić byle jak z zewnątrz

    public double getSaldo() { // getter – zwracam aktualną wartość
        return saldo;
    }

    public void setSaldo(double saldo) { // setter – kontrola nad zmianą
        if (saldo < 0) throw new IllegalArgumentException("Saldo nie może być ujemne");
        this.saldo = saldo;
    }
}
```

---

## KROK-04: `toString()` – ładny wydruk obiektu

```java
public class Książka {
    private final String tytul;
    private final String autor;

    public Książka(String tytul, String autor) {
        this.tytul = tytul; this.autor = autor;
    }

    @Override public String toString() { // jak obiekt ma wyglądać po wydruku
        return "Książka{tytuł='" + tytul + "', autor='" + autor + "'}";
    }
}
```

---

## KROK-05: Użycie `LinkedList` (dodawanie, usuwanie, iteracja)

```java
import java.util.LinkedList;

public class PrzykladLinkedList {
    public static void main(String[] args) {
        // Tworzę listę dwukierunkową – szybkie dodawanie/usuwanie na końcach
        LinkedList<String> kolejka = new LinkedList<>();

        // --- DODAWANIE ---
        kolejka.add("Ala");            // dodaj na koniec
        kolejka.addFirst("Tomek");     // dodaj na początek (typowe dla kolejki/stacku)
        kolejka.addLast("Zosia");      // jawnie na koniec

        // --- PODGLĄD I USUWANIE ---
        String pierwszy = kolejka.peekFirst(); // podejrzyj pierwszy, nie usuwa
        String ostatni  = kolejka.peekLast();  // podejrzyj ostatni, nie usuwa
        System.out.println(pierwszy + " ... " + ostatni);

        String usuniety = kolejka.pollFirst(); // pobiera i usuwa pierwszy (null gdy pusta)
        System.out.println("Usunięto: " + usuniety);

        // --- ITERACJA ---
        for (String s : kolejka) { // for-each – czytelne przejście po elementach
            System.out.println(s);
        }
    }
}
```

---

## KROK-06: Dziedziczenie (`extends`) + `super`

```java
class Zwierze { // klasa bazowa (superklasa)
    protected String imie; // protected = widoczne w podklasach
    public Zwierze(String imie) { this.imie = imie; }
    public void dzwiek() { System.out.println("???"); }
}

class Pies extends Zwierze { // Pies dziedziczy po Zwierze
    public Pies(String imie) { super(imie); } // super(...) wywołuje konstruktor rodzica
    @Override public void dzwiek() { System.out.println("Hau! Nazywam się " + imie); }
}
```

---

## KROK-07: Abstrakcja vs Interfejs

```java
// Abstrakcja: może mieć pola, metody z ciałem i abstrakcyjne
abstract class Figura {
    public abstract double pole(); // "musisz nadpisać"
    public double obwod() { return 0; } // domyślna implementacja
}

// Interfejs: kontrakt – co klasa POTRAFI
interface Rysowalne {
    void narysuj();
}

class Kolo extends Figura implements Rysowalne {
    private final double r;
    public Kolo(double r) { this.r = r; }
    @Override public double pole() { return Math.PI * r * r; }
    @Override public void narysuj() { System.out.println("(kółko)"); }
}
```

---

## KROK-08: Polimorfizm

```java
Figura f = new Kolo(2.0); // typ zmiennej = Figura, ale obiekt = Kolo
System.out.println(f.pole()); // wywoła Kolo.pole() (dynamiczne wiązanie)
```

---

## KROK-09: Generyki – klasa i metoda

```java
// Klasa generyczna: Pudełko na dowolny typ T
class Pudelko<T> {
    private T wartosc;
    public void wloz(T v) { this.wartosc = v; }
    public T wyjmij() { return wartosc; }
}

// Metoda generyczna: <U> przed zwracanym typem
class Narzedzia {
    public static <U> U echo(U x) { return x; }
}
```

---

## KROK-10: `equals()` i `hashCode()`

```java
import java.util.Objects;

class Student {
    private final String nrIndeksu;
    private final String nazwisko;

    public Student(String nrIndeksu, String nazwisko) {
        this.nrIndeksu = nrIndeksu; this.nazwisko = nazwisko;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;              // ten sam obiekt
        if (!(o instanceof Student)) return false; // inny typ -> false
        Student s = (Student) o;
        return Objects.equals(nrIndeksu, s.nrIndeksu); // równe jeśli indeks ten sam
    }

    @Override public int hashCode() {
        return Objects.hash(nrIndeksu); // spójne z equals!
    }
}
```

---

## KROK-11: `Comparable` i `Comparator`

```java
import java.util.*;

class Pracownik implements Comparable<Pracownik> {
    String nazwisko; double pensja;
    public Pracownik(String n, double p) { nazwisko = n; pensja = p; }
    @Override public int compareTo(Pracownik o) { // sortowanie naturalne po nazwisku
        return this.nazwisko.compareTo(o.nazwisko);
    }
}

class PensjaMalejaco implements Comparator<Pracownik> {
    @Override public int compare(Pracownik a, Pracownik b) {
        return Double.compare(b.pensja, a.pensja); // malejąco
    }
}

class SortDemo {
    public static void main(String[] args) {
        List<Pracownik> lista = new ArrayList<>();
        lista.add(new Pracownik("Kowalski", 5000));
        lista.add(new Pracownik("Nowak", 7000));
        Collections.sort(lista); // po nazwisku (Comparable)
        lista.sort(new PensjaMalejaco()); // po pensji (Comparator)
    }
}
```

---

## KROK-12: Wyjątki – własny wyjątek + `try-with-resources`

```java
class ZaMaloSrodkowException extends Exception { // checked exception
    public ZaMaloSrodkowException(String msg) { super(msg); }
}

class Rachunek {
    private double saldo = 100;
    public void wyplac(double kwota) throws ZaMaloSrodkowException {
        if (kwota > saldo) throw new ZaMaloSrodkowException("Brak środków");
        saldo -= kwota;
    }
}

// try-with-resources (automatycznie zamknie zasób)
// (pełny przykład z plikami w KROK-13)
```

---

## KROK-13: Pliki – czytanie/zapisywanie

```java
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PlikiDemo {
    public static void main(String[] args) {
        Path sciezka = Paths.get("dane.txt");

        // ZAPIS – szybki sposób (nadpisze plik)
        try {
            Files.write(sciezka, List.of("Ala", "ma", "kota"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ODCZYT – klasyczny BufferedReader
        try (BufferedReader br = Files.newBufferedReader(sciezka)) { // auto-close
            String linia;
            while ((linia = br.readLine()) != null) {
                System.out.println(linia);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

## KROK-14: Strumienie (`Stream`)

```java
import java.util.*;
import java.util.stream.*;

class StreamDemo {
    public static void main(String[] args) {
        List<Integer> liczby = Arrays.asList(1,2,3,4,5,6);
        List<Integer> parzysteKwadraty = liczby.stream()   // zamieniam listę w strumień
            .filter(x -> x % 2 == 0)                      // biorę tylko parzyste
            .map(x -> x * x)                              // podnoszę do kwadratu
            .collect(Collectors.toList());                // wracam do listy
        System.out.println(parzysteKwadraty);
    }
}
```

---

## KROK-15: Kolekcje – skrót użycia

```java
import java.util.*;

class KolekcjeDemo {
    public static void main(String[] args) {
        // ArrayList – szybki dostęp po indeksie
        List<String> a = new ArrayList<>();
        a.add("A"); a.add(0, "START"); a.remove("A");

        // HashSet – unikalne elementy, brak duplikatów
        Set<String> s = new HashSet<>();
        s.add("A"); s.add("A"); // duplikat zignorowany

        // HashMap – para klucz->wartość
        Map<String,Integer> m = new HashMap<>();
        m.put("Ala", 3); m.put("Kot", 1);
        Integer ile = m.getOrDefault("Pies", 0);
        for (Map.Entry<String,Integer> e : m.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }
}
```

---

## KROK-16: Wzorce – Singleton + Factory

```java
// Singleton: jedna instancja w aplikacji
class Config {
    private static final Config INSTANCE = new Config(); // eager
    private Config() {} // prywatny konstruktor – nie da się new z zewnątrz
    public static Config getInstance() { return INSTANCE; }
}

// Factory: tworzenie obiektów na podstawie parametru/typu
interface Pojazd { String start(); }
class Auto implements Pojazd { public String start(){return "Brum";} }
class Rower implements Pojazd { public String start(){return "Pstryk";} }

class PojazdFactory {
    public static Pojazd stworz(String typ) {
        return switch (typ) {
            case "auto" -> new Auto();
            case "rower" -> new Rower();
            default -> throw new IllegalArgumentException("Nieznany typ");
        };
    }
}
```

---

## KROK-17: Builder – czytelne tworzenie obiektów

```java
class Uzytkownik {
    private final String login; private final String email; private final boolean admin;
    private Uzytkownik(Builder b){ this.login=b.login; this.email=b.email; this.admin=b.admin; }
    public static class Builder {
        private String login; private String email; private boolean admin;
        public Builder login(String v){this.login=v; return this;}
        public Builder email(String v){this.email=v; return this;}
        public Builder admin(boolean v){this.admin=v; return this;}
        public Uzytkownik build(){ return new Uzytkownik(this); }
    }
}

// użycie:
// Uzytkownik u = new Uzytkownik.Builder().login("ala").email("a@b").admin(true).build();
```

---

## KROK-18: Enum

```java
enum StatusZamowienia {
    NOWE, W_REALIZACJI, WYSŁANE, ANULOWANE;
    public boolean aktywne(){ return this == NOWE || this == W_REALIZACJI; }
}
```

---

## KROK-19: Serializacja (`Serializable`)

```java
import java.io.*;

class Dane implements Serializable { // marker interface: zgoda na serializację
    String txt; int n;
    Dane(String t, int n){ this.txt=t; this.n=n; }
}

class SerDemo {
    public static void main(String[] args) throws Exception {
        // ZAPIS do pliku
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dane.bin"))) {
            out.writeObject(new Dane("hello", 42));
        }
        // ODCZYT z pliku
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("dane.bin"))) {
            Dane d = (Dane) in.readObject();
            System.out.println(d.txt + " " + d.n);
        }
    }
}
```

---

## KROK-20: Mini-projekt „Biblioteka” – łączymy wszystko (komentarze gęste)

**Zadanie typowe na kolosa:**
- Stwórz hierarchię: `Publikacja` (abstr.), `Ksiazka`, `Czasopismo`.
- Przechowuj w kolekcji (np. `LinkedList`), dodawaj/usuń/sortuj.
- Filtruj po autorze, zapisz do pliku tytuły, wczytaj.
- Załaduj/wyjątek, ładny `toString`, `equals/hashCode` po ISBN.

```java
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

// --- 1) Abstrakcyjna baza ---
abstract class Publikacja {
    protected final String tytul;      // chronione – dostęp w podklasach
    protected final String autor;
    protected final String isbn;
    public Publikacja(String t, String a, String i){ this.tytul=t; this.autor=a; this.isbn=i; }
    public abstract String typ();      // wymusza implementację w podklasach
    public String getAutor(){ return autor; }
    public String getIsbn(){ return isbn; }
    @Override public String toString(){ return typ()+": "+tytul+" ("+autor+") ["+isbn+"]"; }
    @Override public boolean equals(Object o){
        if(this==o) return true; if(!(o instanceof Publikacja)) return false; Publikacja p=(Publikacja)o; return Objects.equals(isbn,p.isbn);
    }
    @Override public int hashCode(){ return Objects.hash(isbn); }
}

// --- 2) Dwie konkretne klasy ---
class Ksiazka extends Publikacja {
    private final int strony;
    public Ksiazka(String t, String a, String i, int s){ super(t,a,i); this.strony=s; }
    @Override public String typ(){ return "Książka"; }
}
class Czasopismo extends Publikacja {
    private final int numer; // np. numer wydania
    public Czasopismo(String t,String a,String i,int n){ super(t,a,i); this.numer=n; }
    @Override public String typ(){ return "Czasopismo"; }
}

// --- 3) Repozytorium – LinkedList + operacje ---
class BibliotekaRepo {
    private final LinkedList<Publikacja> lista = new LinkedList<>(); // KROK-05 w akcji

    public void dodaj(Publikacja p){ lista.add(p); }
    public boolean usunPoIsbn(String isbn){ return lista.removeIf(p -> p.getIsbn().equals(isbn)); }
    public List<Publikacja> wszystkie(){ return new ArrayList<>(lista); } // kopia, by nie wypłynęło na zewnątrz

    public List<Publikacja> filtrAutor(String autor){
        return lista.stream().filter(p -> p.getAutor().equalsIgnoreCase(autor)).collect(Collectors.toList());
    }

    public List<Publikacja> sortujPoTytule(){
        return lista.stream().sorted(Comparator.comparing(p -> p.tytul)).collect(Collectors.toList());
    }
}

// --- 4) I/O – zapis samych tytułów, potem odczyt ---
class PlikSerwis {
    public static void zapiszTytuly(Path plik, List<Publikacja> pub) throws IOException {
        List<String> tytuly = pub.stream().map(p -> p.toString()).collect(Collectors.toList());
        Files.write(plik, tytuly); // nadpisze
    }

    public static List<String> wczytajLinie(Path plik) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(plik)) {
            List<String> out = new ArrayList<>();
            String l; while((l=br.readLine())!=null) out.add(l);
            return out;
        }
    }
}

// --- 5) Main – demonstrujemy wszystko ---
public class MainBiblioteka {
    public static void main(String[] args) {
        try {
            BibliotekaRepo repo = new BibliotekaRepo();
            repo.dodaj(new Ksiazka("Java od zera", "Kowalski", "ISBN-1", 350));
            repo.dodaj(new Czasopismo("Programista", "Redakcja", "ISBN-2", 9));
            repo.dodaj(new Ksiazka("Strumienie w praktyce", "Nowak", "ISBN-3", 200));

            // Filtr po autorze (Stream filter)
            List<Publikacja> kowalski = repo.filtrAutor("Kowalski");
            System.out.println("Filtr: " + kowalski);

            // Sortowanie (Comparator.comparing)
            List<Publikacja> posortowane = repo.sortujPoTytule();
            System.out.println("Sort: " + posortowane);

            // Zapis do pliku i odczyt (try/catch + I/O)
            Path plik = Paths.get("tytuly.txt");
            PlikSerwis.zapiszTytuly(plik, posortowane);
            System.out.println("Zapisano do: " + plik.toAbsolutePath());

            List<String> wczytane = PlikSerwis.wczytajLinie(plik);
            System.out.println("Odczytane: " + wczytane);

            // Usuwanie po ISBN
            boolean usunieto = repo.usunPoIsbn("ISBN-2");
            System.out.println("Usunięto czasopismo? " + usunieto);
        } catch (IOException e) { // obsługa checked exception z plików
            System.err.println("Błąd I/O: " + e.getMessage());
        }
    }
}
```

---

# Jak używać tego zestawu podczas nauki / kolosa

- Masz **spis treści** – wpisujesz `KROK-XX` w IDE lub w przeglądarce.
- Kopiuj minimalny wzorzec, dopasuj nazwy (np. "Student" zamiast "Publikacja").
- Pamiętaj: **enkapsulacja** (pola prywatne), **`toString`**, **`equals/hashCode`**, **`Comparable`/`Comparator`**, **kolekcje**, **wyjątki**, **I/O**, **Stream**.

Powodzenia! Jeśli chcesz, dopiszę Ci pod konkretną listę zadań jeszcze bardziej dopasowane gotowce (wrzuć treść zadań).

