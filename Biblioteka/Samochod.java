public class Samochod {
    private String marka;
    private String model;
    private int rokProdukcji;

    public Samochod(String marka, String model, int rokProdukcji) {
        this.marka = marka;
        this.model = model;
        this.rokProdukcji = rokProdukcji;
    }

    public String getMarka() {
        return marka;
    }

    public String getModel() {
        return model;
    }

    public int getRokProdukcij() {
        return rokProdukcji;
    }

    public void wyswietlInfo(){
        System.out.println("Marka: " + marka + " " + "Model: " + model + " " + "RokProdukcij: " + rokProdukcji);
    }
}
