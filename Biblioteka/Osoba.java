public class Osoba {
    private String imie;
    private String nazwisko;
    private int wiek;
    private Samochod samochod;

    public Osoba(String imie, String nazwisko, int wiek, Samochod samochod) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.wiek = wiek;
        this.samochod = samochod;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public int getWiek() {
        return wiek;
    }

    public Samochod getSamochod() {
        return samochod;
    }

    public void pokazDane(Samochod samochod) {
           System.out.println("Imie: " + imie + " " + nazwisko + " " + wiek);
           samochod.wyswietlInfo();
    }

    public void setSamochod(String marka, String model, int rokProdukcji) {
        this.samochod = new Samochod(marka, model, rokProdukcji);
    }
}
