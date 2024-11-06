package client;

public class Order {
    private int courierId;
    private String nearestStation;
    int limit;
    int page;



    public Order(int courierId, int limit, String nearestStation, int page) {
        this.courierId = courierId;
        this.limit = limit;
        this.nearestStation = nearestStation;
        this.page = page;
    }

}
