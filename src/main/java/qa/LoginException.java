
package qa;

public class LoginException extends IllegalArgumentException {

    public LoginException() {
        super("Incorrect username or password.");
    }
}
