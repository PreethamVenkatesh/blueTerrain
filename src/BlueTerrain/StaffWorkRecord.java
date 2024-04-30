package BlueTerrain;

public class StaffWorkRecord {

    private int staffId;
    private String firstName;
    private String lastName;
    private String email;
    private String profileType;
    private boolean approved;
    private int hoursWorked;

    // Constructor to initialize the StaffWorkRecord object
    public StaffWorkRecord(int staffId, String firstName, String lastName, String email, String profileType, boolean approved, int hoursWorked) {
        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profileType = profileType;
        this.approved = approved;
        this.hoursWorked = hoursWorked;
    }

    // Getters
    public int getStaffId() {
        return staffId;
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

    public String getProfileType() {
        return profileType;
    }

    public boolean getApproved() {
        return approved;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    // Setters
    public void setStaffId(int staffId) {
        this.staffId = staffId;
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

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
}
