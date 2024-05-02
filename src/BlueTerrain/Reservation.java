package BlueTerrain;
/**
 * The Reservation class represents a reservation made for a table.
 * @author Preetham Venkatesh
 */
public class Reservation {
    private int slNo;
    private int tableType;
    private final String bookingDate;
    private final String bookingTime;
    private final String bookingStatus;

    /**
     * Constructor for the Reservation class.
     *
     * @param slNo         The serial number associated with the reservation.
     * @param bookingDate  The date of the reservation.
     * @param bookingTime  The time of the reservation.
     * @param tableType    The type of table reserved.
     * @param bookingStatus The status of the reservation.
     */
    public Reservation(int slNo, String bookingDate, String bookingTime, int tableType, String bookingStatus) {
    this.slNo = slNo; // Assigns the serial number of the reservation.
    this.bookingDate = bookingDate; // Assigns the booking date of the reservation.
    this.bookingTime = bookingTime; // Assigns the booking time of the reservation.
    this.tableType = tableType; // Assigns the table type of the reservation.
    this.bookingStatus = bookingStatus; // Assigns the booking status of the reservation.
    }

    public int getSlNo() {
        // Getter method for retrieving the serial number of the reservation.
        return slNo; // Returns the serial number.
    }

    public void setSlNo(int slNo) {
        // Setter method for updating the serial number of the reservation.
        this.slNo = slNo; // Sets the serial number to the provided value.
    }

    public String getBookingDate() {
        // Getter method for retrieving the booking date of the reservation.
        return bookingDate; // Returns the booking date.
    }

    public String getBookingTime() {
        // Getter method for retrieving the booking time of the reservation.
        return bookingTime; // Returns the booking time.
    }

    public String getBookingStatus() {
        // Getter method for retrieving the booking status of the reservation.
        return bookingStatus; // Returns the booking status.
    }

    public int getTableType() {
        // Getter method for retrieving the table type of the reservation.
        return tableType; // Returns the table type.
    }

    public void setTableType(int tableType) {
        // Setter method for updating the table type of the reservation.
        this.tableType = tableType; // Sets the table type to the provided value.
    }
}
