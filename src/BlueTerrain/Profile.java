package BlueTerrain;

/**
 * The Profile class represents a user profile.
 * @author Preetham Venkatesh
 */
public class Profile {
    private int slNo;
    private String fullName;

    /**
     * Constructor for the Profile class.
     *
     * @param slNo     The serial number associated with the profile.
     * @param fullName The full name of the user associated with the profile.
     */
    public Profile(int slNo, String fullName) {
        this.slNo = slNo;
        this.fullName = fullName;
    }

    public int getSlNo() {
        return slNo;
    }

    public void setSlNo(int slNo) {
        this.slNo = slNo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
