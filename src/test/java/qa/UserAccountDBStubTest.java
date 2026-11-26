
package qa;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserAccountDBStubTest {

    private UserAccountDBStub db;
    private User steven;

    @BeforeEach
    void setUp() {
        db = new UserAccountDBStub();

        steven = new User(
            "Steven", "Password1@", null
        );
    }

    // REQ-11: Registration stores a new user
    @Test
    void registersNewUser() {
        assertTrue(db.register(steven));
        assertTrue(db.isRegisteredUser(steven));
    }

    // REQ-03: Usernames must be unique, ignoring case
    @Test
    void rejectsDuplicateUsername() {
        db.register(steven);

        User duplicate = new User(
            "steven", "Different1@", null
        );

        assertFalse(db.register(duplicate));
    }

    // REQ-03: Username lookup is case-insensitive
    @Test
    void recognisesUsernameIgnoringCase() {
        db.register(steven);

        User sameUsername = new User(
            "STEVEN", "Different1@", null
        );

        assertTrue(db.isAnExistingUser(sameUsername));
    }

    // REQ-12: Password matching is case-sensitive
    @Test
    void keepsPasswordCaseSensitive() {
        db.register(steven);

        User wrongPassword = new User(
            "Steven", "password1@", null
        );

        assertTrue(db.isRegisteredUser(steven));
        assertFalse(db.isRegisteredUser(wrongPassword));
    }

    // REQ-12: Unknown users cannot authenticate
    @Test
    void rejectsUnknownUser() {
        assertFalse(db.isRegisteredUser(steven));
    }

    // REQ-11: Multiple users can be registered
    @Test
    void registersDifferentUsers() {
        User alice = new User(
            "Alice", "Password2@", null
        );

        assertTrue(db.register(steven));
        assertTrue(db.register(alice));

        assertTrue(db.isRegisteredUser(steven));
        assertTrue(db.isRegisteredUser(alice));
    }

    // REQ-10: Database should contain supported cities
    @Test
    void containsSupportedCities() {
        assertFalse(db.getCityNames().isEmpty());
    }
}
