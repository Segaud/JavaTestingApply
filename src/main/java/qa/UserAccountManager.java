
package qa;

// Uses the actual database or a stub/mock database
// to login or register a user.

public class UserAccountManager {

    IUserAccountDb userAccountDb;

    public UserAccountManager(IUserAccountDb userAccountDb) {
        this.userAccountDb = userAccountDb;
    }

    // Logs user in.
    // Username and password format already validated.
    public boolean login(User user) {

        // TODO: Implement maximum login attempts.
        // If number of login tries > 3,
        // throw TooManyLoginTriesException.

        if (!userAccountDb.isRegisteredUser(user)) {
            throw new IllegalArgumentException();
        }

        return userAccountDb.register(user);
    }

    public boolean register(User user) {

        if (userAccountDb.isRegisteredUser(user)) {
            throw new IllegalArgumentException();
        }

        userAccountDb.register(user);

        return true;
    }
}
