package BlueTerrain;

public abstract class Report {

    protected Orders orders;

    public Report(Orders orders) {
        this.orders = orders;
    }

    public abstract void generateReport();

}
