import java.util.ArrayList;

public class Salon {
    public ArrayList<Samochod> samochody = new ArrayList<>();

    public void dodajSamochod(Samochod s){
        samochody.add(s);
    }
    public void pokazWszystkie(){
        for(Samochod s: samochody){
            System.out.println(s.wyswietlInfo());
        }
    }

}
