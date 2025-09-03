public class EBook extends Book {
    private int fileMbsize;

    public EBook(String title, String author, int year, int fileMbsize) {
        super(title, author, year);
        this.fileMbsize = fileMbsize;
    }

    public int getFileMbsize() {
        return fileMbsize;
    }

    @Override

    public String toString(){
        return  getTitle() + ";" + getAuthor() + ";" + getYear() + ";" + fileMbsize;
    }

}