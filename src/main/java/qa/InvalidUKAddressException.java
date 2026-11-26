package qa;

public class InvalidUKAddressException
        extends IllegalArgumentException {

    public InvalidUKAddressException(String message) {
        super(message);
    }
}
