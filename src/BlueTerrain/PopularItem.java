package BlueTerrain;

public class PopularItem extends Report {

    public PopularItem(Orders orders) {
        super(orders);
    }

    @Override
    public void generateReport() {
        // Implementation for generating a report on the most popular item
        String popularItem = "[Determine the the popular item from orders count.]";
        System.out.println("Most Popular Item: Seafood Pasta");
    }

}
