
package qa;

public class TooManyLoginTriesException
        extends IllegalArgumentException {

    public TooManyLoginTriesException() {
        super("Too many failed login attempts.");
    }
}
