package edu.gatech.cs6310;

import java.util.SortedMap;
import java.util.TreeMap;
import java.time.LocalDateTime;

class Drone
{
    private final String drone_id;
    private String assigned_drone_pilot;
    private final int total_lifting_capacity;
    private int available_lifting_capacity;
    private int no_of_trips_remaining;
    private SortedMap<String, Order> order_map = new TreeMap<>();
    private LocalDateTime createdTime;
    private LocalDateTime lastUpdatedTime;

    public Drone(String _drone_id, int _lifting_capacity, int _no_of_trips_remaining)
    {
        this.drone_id = _drone_id;
        this.total_lifting_capacity = _lifting_capacity;
        this.available_lifting_capacity = _lifting_capacity;
        this.createdTime=LocalDateTime.now();
        this.lastUpdatedTime=LocalDateTime.now();
    }

    public String get_drone_id()
    {
        return this.drone_id;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void add_order_to_drone(Order _order)
    {
        this.order_map.put(_order.get_order_id(), _order);
        this.lastUpdatedTime=LocalDateTime.now();
    }

    public int get_available_lifting_capacity()
    {
        int total_weight = 0;
        for(SortedMap.Entry<String, Order> order_entry: this.order_map.entrySet())
        {
            Order order = order_entry.getValue();
            total_weight += order.get_total_weight();
        }
        this.available_lifting_capacity = this.total_lifting_capacity - total_weight;
        return this.available_lifting_capacity;
    }

    public int get_no_of_trips_remaining()
    {
        return this.no_of_trips_remaining;
    }

    public String get_assigned_drone_pilot()
    {
        return this.assigned_drone_pilot;
    }

    public int get_no_of_orders()
    {
        return this.order_map.size();
    }

    public int get_total_lifting_capacity()
    {
        return this.total_lifting_capacity;
    }

    public void set_assigned_drone_pilot(String _assigned_drone_pilot)
    {
        if(this.assigned_drone_pilot == null)
        {
            this.assigned_drone_pilot = _assigned_drone_pilot;
            this.lastUpdatedTime=LocalDateTime.now();
        }
    }

    public void increase_no_of_trips_remaining(int _number_of_trips)
    {
        this.no_of_trips_remaining += _number_of_trips;
        this.lastUpdatedTime=LocalDateTime.now();
    }

    public void remove_order_from_drone(Order _order)
    {
        this.order_map.remove(_order.get_order_id());
        this.lastUpdatedTime=LocalDateTime.now();
    }

    public void remove_pilot_from_drone()
    {
        this.assigned_drone_pilot = null;
    }
}