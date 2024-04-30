package BlueTerrain;

/**
 * Represents a customer with personal and contact information.
 */
public class Customer {

    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private int noOfVisits;

    // Constructor
    public Customer(int customerId, String firstName, String lastName, String email, String address, int noOfVisits) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.noOfVisits = noOfVisits;
    }

    // Getters
    public int getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getNoOfVisits() {
        return noOfVisits;
    }

    // Setters
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNoOfVisits(int noOfVisits) {
        this.noOfVisits = noOfVisits;
    }

}
