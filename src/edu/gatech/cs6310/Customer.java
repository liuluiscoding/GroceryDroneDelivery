package edu.gatech.cs6310;
import java.util.SortedMap;
import java.util.TreeMap;
import java.time.LocalDateTime;

class Customer extends User
{
    private String account_id;
    private int rating;
    private int total_credit;
    private LocalDateTime createdTime;
    private LocalDateTime lastUpdatedTime;
    private SortedMap<String, Order> order_map = new TreeMap<>();

    Customer(String _account_id, String _first_name, String _last_name, String _phone_number, int _rating, int _credit)
    {
        super(_last_name, _first_name, _phone_number);
        this.account_id = _account_id;
        this.rating = _rating;
        this.total_credit = _credit;
        this.createdTime=LocalDateTime.now();
        this.lastUpdatedTime=LocalDateTime.now();
        System.out.println("OK:change_completed");
    }

    public void add_order_to_customer(Order _order)
    {
        this.order_map.put(_order.get_order_id(), _order);
        this.lastUpdatedTime=LocalDateTime.now();
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public int get_rating()
    {
        return this.rating;
    }

    public int get_credit()
    {
        int total_cost = 0;
        for(SortedMap.Entry<String, Order> order_entry: this.order_map.entrySet())
        {
            Order order = order_entry.getValue();
            total_cost += order.get_total_price();
        }
        return this.total_credit - total_cost;
    }

    public int get_total_credit()
    {
        return this.total_credit;
    }

    public void increase_credit(int _increased_amount)
    {
        this.total_credit += _increased_amount;
        this.lastUpdatedTime=LocalDateTime.now();
    }
}

