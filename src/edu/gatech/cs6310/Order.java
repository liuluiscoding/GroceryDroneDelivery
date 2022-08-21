package edu.gatech.cs6310;
import java.util.SortedMap;
import java.util.TreeMap;
import java.time.LocalDateTime;

class Order
{
    private final String store_name;
    private final String order_id;
    private Drone drone;
    private Customer customer;
    private SortedMap<String, Line> item_map = new TreeMap<>();
    private LocalDateTime createdTime;
    private LocalDateTime lastUpdatedTime;

    Order( String _store_name, String _order_id, Drone _drone, Customer _customer )
    {
        this.store_name = _store_name;
        this.order_id = _order_id;
        this.drone = _drone;
        this.customer = _customer;
        this.createdTime=LocalDateTime.now();
        this.lastUpdatedTime=LocalDateTime.now();
    }

    public Customer get_customer()
    {
        return this.customer;
    }

    public String get_order_id()
    {
        return this.order_id;
    }

    public String get_drone_id()
    {
        return this.drone.get_drone_id();
    }

    public Drone get_drone()
    {
        return this.drone;
    }

    public void add_item_to_order(Item _item, int _quantity, int _unit_price)
    {
        String item_name = _item.get_item_name();
        if(this.item_map.containsKey(item_name))
        {
            System.out.println("ERROR:item_already_ordered");
        }
        else
        {
            Line cur_line = item_map.get(item_name);
            if (cur_line == null)
            {
                cur_line = new Line(_item, _quantity, _unit_price);
            }
            else
            {
                cur_line.add_item_to_line(_quantity, _unit_price);
            }
            int weight_increment = cur_line.get_line_weight();
            int price_increment = cur_line.get_line_price();
            if(weight_increment > this.drone.get_available_lifting_capacity())
            {
                System.out.println("ERROR:drone_cant_carry_new_item");
            }
            else if(price_increment > this.customer.get_credit())
            {
                System.out.println("ERROR:customer_cant_afford_new_item");
            }
            else
            {
                item_map.put(_item.get_item_name(), cur_line);
                System.out.println("OK:change_completed");
                this.lastUpdatedTime=LocalDateTime.now();
            }

        }
    }

    public void print_lines()
    {
        for(SortedMap.Entry<String, Line> entry: item_map.entrySet())
        {
            Line cur_line = entry.getValue();
            System.out.println("item_name:" + cur_line.get_line_item_name() +
                               ",total_quantity:" + cur_line.get_line_quantity() +
                               ",total_cost:" + cur_line.get_line_price() +
                               ",total_weight:" + cur_line.get_line_weight());
        }
    }
    public void audit_lines()
    {
        for(SortedMap.Entry<String, Line> entry: item_map.entrySet())
        {
            Line cur_line = entry.getValue();
            System.out.println("item_name:" + cur_line.get_line_item_name() +
                    ",total_quantity:" + cur_line.get_line_quantity() +
                    ",total_cost:" + cur_line.get_line_price() +
                    ",total_weight:" + cur_line.get_line_weight()
                    +", created time: "+ cur_line.getCreatedTime()+
                    ", last updated time: "+ cur_line.getLastUpdatedTime()
            );
        }
    }

    public int get_total_weight()
    {
        int total_weight = 0;
        for(SortedMap.Entry<String, Line> entry: item_map.entrySet())
        {
            Line cur_line = entry.getValue();
            total_weight += cur_line.get_line_weight();
        }
        return total_weight;
    }

    public int get_total_price()
    {
        int total_price = 0;
        for(SortedMap.Entry<String, Line> entry: item_map.entrySet())
        {
            Line cur_line = entry.getValue();
            total_price += cur_line.get_line_price();
        }
        return total_price;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }
}