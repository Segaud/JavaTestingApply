
package qa;

public class Address {

    IUserAccountDb userAccountDb;

    public String number;
    public String addressLine;
    public String postCode;
    public String city;

    public Address(
            String number,
            String addressLine,
            String postCode,
            String city,
            IUserAccountDb userAccountDb) {

        this.userAccountDb = userAccountDb;

        // Validate house number: 12 or 11-15
        if (number == null ||
                !number.matches("[0-9]+(-[0-9]+)?")) {
            throw new InvalidUKAddressException(
                "Invalid house or flat number."
            );
        }

        // Address line must not be null
        if (addressLine == null) {
            throw new InvalidUKAddressException(
                "Address line cannot be null."
            );
        }

        // City must exist in the supported cities database
        if (!isValidCity(city)) {
            throw new InvalidUKAddressException(
                "City is not recognised."
            );
        }

        // Postcode must be valid and non-null
        if (postCode == null ||
                !Utils.isValidUKPostCode(postCode)) {
            throw new InvalidUKAddressException(
                "Invalid UK postcode."
            );
        }

        this.number = number;
        this.addressLine = addressLine;
        this.postCode = postCode;
        this.city = city;
    }

    public boolean isValidCity(String city) {
        return userAccountDb.getCityNames().contains(city);
    }
}
