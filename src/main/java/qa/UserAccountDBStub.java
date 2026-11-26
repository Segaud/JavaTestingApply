
package qa;

import java.util.ArrayList;

public class UserAccountDBStub implements IUserAccountDb {

    // Simulated database stored in memory
    ArrayList<String> cities;
    ArrayList<User> users;

    public UserAccountDBStub() {
        users = new ArrayList<>();
        cities = new ArrayList<>();

        // Seed recognised UK cities
        cities.add("Edinburgh");
        cities.add("Glasgow");
    }

    // Return recognised cities
    @Override
    public ArrayList<String> getCityNames() {
        return cities;
    }

    // Check if username already exists (case-insensitive)
    @Override
    public boolean isAnExistingUser(User user) {

        if (user == null) {
            return false;
        }

        for (User existingUser : users) {
            if (existingUser.userName.equalsIgnoreCase(user.userName)) {
                return true;
            }
        }

        return false;
    }

    // Check username and password for authentication
    @Override
    public boolean isRegisteredUser(User user) {

        if (user == null) {
            return false;
        }

        for (User existingUser : users) {

            boolean usernameMatches =
                existingUser.userName.equalsIgnoreCase(user.userName);

            boolean passwordMatches =
                existingUser.password.equals(user.password);

            if (usernameMatches && passwordMatches) {
                return true;
            }
        }

        return false;
    }

    // Register a new user
    @Override
    public boolean register(User user) {

        if (user == null || isAnExistingUser(user)) {
            return false;
        }

        users.add(user);

        return true;
    }
}
