public class Osoba {
    public String imie;
    public int wiek;
    public Samochod samochod;

    public Osoba(String imie, int wiek) {
        this.imie = imie;
        this.wiek = wiek;
    }

    public String przedstawSie(){
        System.out.println("\n");
        return "Mam na imie " + imie + " " + "i mam lat " + wiek;
    }
    public String pokazSamochod(){
        return samochod.wyswietlInfo();

    }
}
