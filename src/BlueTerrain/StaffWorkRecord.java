package BlueTerrain;

import java.time.LocalDate;
import java.time.LocalTime;

public class StaffWorkRecord {

    // Attributes
    private int staffId;
    private String name;
    private LocalDate dateOfWork;
    private LocalTime startTime;
    private LocalTime endTime;
    private double hoursWorked;
    private String notes;

    // Constructor
    public StaffWorkRecord(int staffId, String name, LocalDate dateOfWork, LocalTime startTime, LocalTime endTime, String notes) {
        this.staffId = staffId;
        this.name = name;
        this.dateOfWork = dateOfWork;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hoursWorked = calculateHoursWorked(startTime, endTime);
        this.notes = notes;
    }

    // Getters and Setters
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfWork() {
        return dateOfWork;
    }

    public void setDateOfWork(LocalDate dateOfWork) {
        this.dateOfWork = dateOfWork;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
        this.hoursWorked = calculateHoursWorked(startTime, this.endTime);
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
        this.hoursWorked = calculateHoursWorked(this.startTime, endTime);
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Method to calculate hours worked
    private double calculateHoursWorked(LocalTime start, LocalTime end) {
        return java.time.Duration.between(start, end).toMinutes() / 60.0;
    }

}
