public class Samochod {
    public String marka;
    public int rokProdukcji;
    public int CurrentYear = 2025;
    public int CarYears;

    public Samochod(String marka, int rokProdukcji) {
        this.marka = marka;
        this.rokProdukcji = rokProdukcji;
    }
    public String wyswietlInfo(){
        return "Marka:" + marka + " " + "Rok produkcji:" + rokProdukcji;
    }

    public boolean czyStary(){
        CarYears = CurrentYear - rokProdukcji;
        if (CarYears <= 11){
            return false;
        } else  {
            return true;
        }
    }
}
