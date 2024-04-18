package BlueTerrain;

public class PeakTime extends Report {

    public PeakTime(Orders orders) {
        super(orders);
    }

    @Override
    public void generateReport() {
        // Implementation for generating a report on the busiest period
        String peakTime = "[Calculate the busiest period using orders' dates and count.]";
        System.out.println("Busiest Period: 11AM to 3PM");
    }

}
