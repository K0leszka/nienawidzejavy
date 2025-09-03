public class VehicleNotFoundException extends Exception {
    private int errorCode;

    public int getErrorCode(){
        return errorCode;
    }

    public VehicleNotFoundException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
