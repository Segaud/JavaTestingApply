
package qa;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UserAccountManager {

    private final IUserAccountDb userAccountDb;

    // Track consecutive failed attempts per username
    private final Map<String, Integer> failedLoginAttempts =
        new HashMap<>();

    public UserAccountManager(IUserAccountDb userAccountDb) {
        this.userAccountDb = userAccountDb;
    }

    // Authenticate an existing user
    public boolean login(User user) {

        if (user == null) {
            throw new LoginException();
        }

        String username =
            user.userName.toLowerCase(Locale.ROOT);

        if (userAccountDb.isRegisteredUser(user)) {

            // Reset failed attempts following successful login
            failedLoginAttempts.remove(username);

            return true;
        }

        // Record the failed login
        int attempts =
            failedLoginAttempts.getOrDefault(username, 0) + 1;

        failedLoginAttempts.put(username, attempts);

        if (attempts > 3) {
            throw new TooManyLoginTriesException();
        }

        throw new LoginException();
    }

    // Register a new user
    public boolean register(User user) {

        if (user == null ||
            userAccountDb.isAnExistingUser(user)) {

            throw new IllegalArgumentException(
                "Username already exists or is invalid."
            );
        }

        // Return the actual database registration result
        return userAccountDb.register(user);
    }
}
