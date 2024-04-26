package BlueTerrain;

public class Profile {
    private int slNo;
    private String fullName;

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
