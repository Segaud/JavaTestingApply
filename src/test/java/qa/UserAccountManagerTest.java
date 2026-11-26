
package qa;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserAccountManagerTest {

    private IUserAccountDb mockDb;
    private UserAccountManager manager;
    private User user;

    @BeforeEach
    void setUp() {
        mockDb = mock(IUserAccountDb.class);
        manager = new UserAccountManager(mockDb);

        user = new User("Steven", "Password1@", null);
    }

    // UM-01: New user registration
    @Test
    void registersNewUser() {
        when(mockDb.register(user)).thenReturn(true);

        assertTrue(manager.register(user));

        verify(mockDb).register(user);
    }

    // UM-02: Reject duplicate usernames
    @Test
    void rejectsDuplicateUsername() {
        when(mockDb.isAnExistingUser(user)).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
            () -> manager.register(user));

        verify(mockDb, never()).register(any(User.class));
    }

    // UM-03: Report database registration failure
    @Test
    void reportsRegistrationFailure() {
        when(mockDb.isAnExistingUser(user)).thenReturn(false);
        when(mockDb.register(user)).thenReturn(false);

        assertFalse(manager.register(user));
    }

    // UM-04: Existing users log in without re-registering
    @Test
    void logsInRegisteredUser() {
        when(mockDb.isRegisteredUser(user)).thenReturn(true);

        assertTrue(manager.login(user));

        verify(mockDb).isRegisteredUser(user);
        verify(mockDb, never()).register(any(User.class));
    }

    // UM-05: Invalid credentials throw LoginException
    @Test
    void rejectsInvalidLogin() {
        when(mockDb.isRegisteredUser(user)).thenReturn(false);

        assertThrows(LoginException.class,
            () -> manager.login(user));
    }

    // UM-06: Fourth consecutive failed login
    @Test
    void limitsFailedLoginAttempts() {
        when(mockDb.isRegisteredUser(user)).thenReturn(false);

        // First three failures are permitted
        for (int i = 0; i < 3; i++) {
            assertThrows(IllegalArgumentException.class,
                () -> manager.login(user));
        }

        // Fourth failure triggers the limit
        assertThrows(TooManyLoginTriesException.class,
            () -> manager.login(user));
    }
}
