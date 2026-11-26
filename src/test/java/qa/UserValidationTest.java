
package qa;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class UserValidationTest {

    // REQ-01: Valid alphabetic username
    @Test
    void acceptsValidUsername() {
        assertDoesNotThrow(() ->
            new User("Steven", "Password1@", null)
        );
    }

    // REQ-01: Username must be longer than 2 characters
    @Test
    void rejectsShortUsername() {
        assertThrows(IllegalArgumentException.class, () ->
            new User("Jo", "Password1@", null)
        );
    }

    // REQ-01: Username cannot contain digits
    @Test
    void rejectsNumericUsername() {
        assertThrows(IllegalArgumentException.class, () ->
            new User("Steven123", "Password1@", null)
        );
    }

    // REQ-01: Null username must be rejected
    @Test
    void rejectsNullUsername() {
        assertThrows(IllegalArgumentException.class, () ->
            new User(null, "Password1@", null)
        );
    }

    // REQ-04: Password must contain at least one digit
    @Test
    void rejectsPasswordWithoutDigit() {
        assertThrows(IllegalArgumentException.class, () ->
            new User("Steven", "Password@ABC", null)
        );
    }

    // REQ-05: Password requires an allowed special character
    @Test
    void rejectsPasswordWithoutSpecialCharacter() {
        assertThrows(IllegalArgumentException.class, () ->
            new User("Steven", "Password123", null)
        );
    }

    // REQ-05: Tilde and pound sign are permitted
    @ParameterizedTest
    @ValueSource(strings = {
        "Password1~",
        "Password1£"
    })
    void acceptsAllowedSpecialCharacters(String password) {
        assertDoesNotThrow(() ->
            new User("Steven", password, null)
        );
    }

    // REQ-06: Prohibited password characters and sequences
    @ParameterizedTest
    @ValueSource(strings = {
        "Password1<@",
        "Password1>@",
        "Password1;@",
        "Password1=@",
        "Password1'@",
        "Password1--@"
    })
    void rejectsProhibitedPasswordCharacters(String password) {
        assertThrows(IllegalArgumentException.class, () ->
            new User("Steven", password, null)
        );
    }

    // REQ-05: Question mark is not an allowed special character
    @Test
    void rejectsUnapprovedSpecialCharacter() {
        assertThrows(IllegalArgumentException.class, () ->
            new User("Steven", "Password1?", null)
        );
    }

    // REQ-06: The letters "or" are not prohibited
    @Test
    void acceptsPasswordContainingOr() {
        assertDoesNotThrow(() ->
            new User("Steven", "Password1@or", null)
        );
    }
}
