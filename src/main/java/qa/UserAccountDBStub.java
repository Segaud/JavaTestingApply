
package qa;

import java.util.ArrayList;

public class UserAccountDBStub implements IUserAccountDb {

    // Database in memory
    ArrayList<String> cities;
    ArrayList<User> users;

    public UserAccountDBStub() throws IllegalArgumentException {

        users = new ArrayList<>();
        cities = new ArrayList<>();

        // TODO: Seed the database in memory
        // with cities and users.
    }

    public ArrayList<String> getCityNames() {
        return cities;
    }

    public boolean isAnExistingUser(User user) {

        // TODO: Check whether username exists
        // in the users list.

        return false;
    }

    public boolean isRegisteredUser(User user) {

        // TODO: Check whether username and password
        // exist in the users list.

        return false;
    }

    public boolean register(User user) {

        if (isAnExistingUser(user)) {
            return false;
        }

        // TODO: Add user to the in-memory database.

        return true;
    }
}
