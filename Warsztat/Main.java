public class Main {
    public static void main(String[] args) {
        Samochod s1 = new Samochod("Toyota" , 2015);
        Samochod s2 = new Samochod("Mazda" , 2001);
        Samochod s3 = new Samochod("Mini-Cooper" , 2024);
        System.out.println(s1.wyswietlInfo());
        System.out.println(s1.czyStary());
        System.out.println(s2.wyswietlInfo());
        System.out.println(s2.czyStary());
        System.out.println(s3.wyswietlInfo());
        System.out.println(s3.czyStary());

        Osoba o1 = new Osoba("Jan", 20);
        o1.samochod = s1;
        System.out.println(o1.przedstawSie());
        System.out.println(o1.pokazSamochod());
        Osoba o2 = new Osoba("Arek", 18);
        o2.samochod = s2;
        System.out.println(o2.przedstawSie());
        System.out.println(o2.pokazSamochod());
        Osoba o3 = new Osoba("Pawel", 21);
        o3.samochod = s3;
        System.out.println(o3.przedstawSie());
        System.out.println(o3.pokazSamochod());

        Salon salon = new Salon();
        Samochod s4 = new Samochod("Honda" , 2015);
        Samochod s5 = new Samochod("BMW" , 2001);
        Samochod s6 = new Samochod("Audi" , 2024);
        salon.dodajSamochod(s4);
        salon.dodajSamochod(s5);
        salon.dodajSamochod(s6);
        salon.pokazWszystkie();
    }
}