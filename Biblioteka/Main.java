//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Samochod s1 = new Samochod("BMW", "M5", 2015);
        Samochod s2 = new Samochod("Audi", "A5", 2001);
        Samochod s3 = new Samochod("Volvo", "V40", 2002);

        s1.wyswietlInfo();
        s2.wyswietlInfo();
        s3.wyswietlInfo();


        Osoba o1 = new Osoba("Dawid", "Matwi", 21, s1);
        Osoba o2 = new Osoba("Kapi", "Harley", 21, s2);
        Osoba o3 = new Osoba("Bartolemu", "Ass", 21, s3);

        o1.pokazDane(s1);
        o2.pokazDane(s2);
        o3.pokazDane(s3);

        Osoba o5 = new Osoba("Ktos", "Nowy", 30, null);
        o5.setSamochod("Skoda", "Rapid", 2001);
        o5.pokazDane(o5.getSamochod());

        Biblioteka b = new Biblioteka();
        b.dodajKsiazki("d",  2001);
        b.dodajKsiazki("a",  2001);
        b.dodajKsiazki("c",  2001);

        b.wyswietlKsiazki();




    }
}