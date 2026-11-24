
package qa;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AddressValidationTest {

    private IUserAccountDb mockDb;

    @BeforeEach
    void setUp() {
        // Simulate the database of supported cities
        mockDb = mock(IUserAccountDb.class);

        when(mockDb.getCityNames()).thenReturn(
            new ArrayList<>(List.of("Edinburgh", "Glasgow"))
        );
    }

    private Address createAddress(
            String number,
            String addressLine,
            String postCode,
            String city) {

        return new Address(
            number, addressLine, postCode, city, mockDb
        );
    }

    // REQ-07: House number validation

    @Test
    void acceptsNumericHouseNumber() {
        assertDoesNotThrow(() ->
            createAddress("12", "High Street", "EH1 1AA", "Edinburgh")
        );
    }

    @Test
    void acceptsHouseNumberRange() {
        assertDoesNotThrow(() ->
            createAddress("11-15", "High Street", "EH1 1AA", "Edinburgh")
        );
    }

    @Test
    void rejectsAlphabeticHouseNumber() {
        assertThrows(IllegalArgumentException.class, () ->
            createAddress("abc", "High Street", "EH1 1AA", "Edinburgh")
        );
    }

    @Test
    void rejectsAlphanumericHouseNumber() {
        assertThrows(IllegalArgumentException.class, () ->
            createAddress("12A", "High Street", "EH1 1AA", "Edinburgh")
        );
    }

    @Test
    void rejectsUnsupportedHouseNumberCharacters() {
        assertThrows(IllegalArgumentException.class, () ->
            createAddress("12/14", "High Street", "EH1 1AA", "Edinburgh")
        );
    }

    @Test
    void rejectsNullHouseNumber() {
        assertThrows(IllegalArgumentException.class, () ->
            createAddress(null, "High Street", "EH1 1AA", "Edinburgh")
        );
    }

    // REQ-08: Address line validation

    @Test
    void acceptsAddressLineWithPunctuation() {
        assertDoesNotThrow(() ->
            createAddress("12", "King's Road, East",
                          "EH1 1AA", "Edinburgh")
        );
    }

    @Test
    void rejectsNullAddressLine() {
        assertThrows(IllegalArgumentException.class, () ->
            createAddress("12", null, "EH1 1AA", "Edinburgh")
        );
    }

    // REQ-09: UK postcode validation

    @ParameterizedTest
    @ValueSource(strings = {"EH1 1AA", "SW1A 1AA"})
    void acceptsValidPostcodes(String postcode) {
        assertDoesNotThrow(() ->
            createAddress("12", "High Street",
                          postcode, "Edinburgh")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "EH11AA",
        "EH1 1A",
        "12345"
    })
    void rejectsInvalidPostcodes(String postcode) {
        assertThrows(IllegalArgumentException.class, () ->
            createAddress("12", "High Street",
                          postcode, "Edinburgh")
        );
    }

    @Test
    void rejectsNullPostcode() {
        assertThrows(IllegalArgumentException.class, () ->
            createAddress("12", "High Street", null, "Edinburgh")
        );
    }

    // REQ-10: City validation using Mockito

    @Test
    void acceptsRecognisedCity() {
        assertDoesNotThrow(() ->
            createAddress("12", "High Street", "EH1 1AA", "Glasgow")
        );

        verify(mockDb).getCityNames();
    }

    @Test
    void rejectsUnrecognisedCity() {
        assertThrows(IllegalArgumentException.class, () ->
            createAddress("12", "High Street", "EH1 1AA", "Paris")
        );
    }

    @Test
    void rejectsNullCity() {
        assertThrows(IllegalArgumentException.class, () ->
            createAddress("12", "High Street", "EH1 1AA", null)
        );
    }
}
