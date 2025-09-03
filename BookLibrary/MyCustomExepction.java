public class MyCustomExepction extends Exception {
    private int errorCode;


    public MyCustomExepction(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
