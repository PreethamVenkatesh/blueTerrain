package BlueTerrain;

public class Reservation {
    private int slNo;
    private int tableType;
    private final String bookingDate;
    private final String bookingTime;
    private final String bookingStatus;

    public Reservation(int slNo, String bookingDate, String bookingTime, int tableType, String bookingStatus) {
        this.slNo = slNo;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.tableType = tableType;
        this.bookingStatus = bookingStatus;
    }

    public int getSlNo() {
        return slNo;
    }

    public void setSlNo(int slNo) {
        this.slNo = slNo;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public int getTableType() {
        return tableType;
    }

    public void setTableType(int tableType) {
        this.tableType = tableType;
    }
}
