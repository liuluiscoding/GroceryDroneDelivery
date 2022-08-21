package edu.gatech.cs6310;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.time.LocalDateTime;

public class Store
{
    private final String store_name;
    private int current_revenue;
    private LocalDateTime createdTime;
    private LocalDateTime cataloglastUpdatedTime;


    private SortedMap<String, Item> item_map = new TreeMap<>();
    private SortedMap<String, Drone> drone_map = new TreeMap<>();
    private SortedMap<String, Order> order_map = new TreeMap<>();



    public Store(String _store_name, int _initial_revenue)
    {
        this.store_name = _store_name;
        this.current_revenue = _initial_revenue;
        this.createdTime= LocalDateTime.now();
        System.out.println("OK:change_completed");

    }
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public String get_store_name()
    {
        return this.store_name;
    }

    public int get_current_revenue()
    {
        return this.current_revenue;
    }

    public void increment_current_revenue(int _increased_amount)
    {
        this.current_revenue += _increased_amount;
    }

    public void add_item_to_store(Item _item)
    {
        String item_name = _item.get_item_name();
        if(this.item_map.containsKey(item_name))
        {
            System.out.println("ERROR:item_identifier_already_exists");
        }
        else
        {
            this.item_map.put(item_name, _item);
            cataloglastUpdatedTime=LocalDateTime.now();
            System.out.println("OK:change_completed");
        }
    }

    public Item get_item(String _item_name)
    {
        return this.item_map.get(_item_name);
    }

    public void display_all_items()
    {
        for(SortedMap.Entry<String, Item> entry: item_map.entrySet())
        {
            Item value = entry.getValue();
            System.out.println(value.get_item_name() +","+ value.get_item_weight());
        }
        System.out.println("OK:display_completed");
    }

    public void audit_item_catalog()
    {
        for(SortedMap.Entry<String, Item> entry: item_map.entrySet())
        {
            Item value = entry.getValue();
            System.out.println(value.get_item_name() +","+ value.get_item_weight()
                    +", created time: "+ value.getCreatedTime());
        }
        System.out.println("Item catalog last updated time: "+ cataloglastUpdatedTime);
    }



    public void add_drone_to_store(Drone _drone)
    {
        String drone_id = _drone.get_drone_id();
        if(this.drone_map.containsKey(drone_id))
        {
            System.out.println("ERROR:drone_identifier_already_exists");
        }
        else
        {
            this.drone_map.put(drone_id, _drone);
            System.out.println("OK:change_completed");
        }
    }

    public SortedMap<String, Drone> get_drone_map()
    {
        return this.drone_map;
    }

    public void display_all_drones(SortedMap<String, DronePilot> _account_pilot_map)
    {
        for(SortedMap.Entry<String, Drone> drone_map: this.drone_map.entrySet())
        {
            Drone cur_drone = drone_map.getValue();
            System.out.print("droneID:" + cur_drone.get_drone_id() +
                    ",total_cap:" + cur_drone.get_total_lifting_capacity() +
                    ",num_orders:" + cur_drone.get_no_of_orders() +
                    ",remaining_cap:" + cur_drone.get_available_lifting_capacity() +
                    ",trips_left:" + cur_drone.get_no_of_trips_remaining());

            if(cur_drone.get_assigned_drone_pilot() != null
                    && _account_pilot_map.containsKey(cur_drone.get_assigned_drone_pilot()))
            {
                DronePilot cur_pilot = _account_pilot_map.get(cur_drone.get_assigned_drone_pilot());
                System.out.println(",flown_by:" + cur_pilot.get_first_name() +
                        "_" + cur_pilot.get_last_name());
            }
            else
            {
                System.out.println();
            }
        }
    }

    public void audit_all_drones(SortedMap<String, DronePilot> _account_pilot_map)
    {
        for(SortedMap.Entry<String, Drone> drone_map: this.drone_map.entrySet())
        {
            Drone cur_drone = drone_map.getValue();
            System.out.print("droneID:" + cur_drone.get_drone_id() +
                    ",total_cap:" + cur_drone.get_total_lifting_capacity() +
                    ",num_orders:" + cur_drone.get_no_of_orders() +
                    ",remaining_cap:" + cur_drone.get_available_lifting_capacity() +
                    ",trips_left:" + cur_drone.get_no_of_trips_remaining()+
                    ", created time: "+ cur_drone.getCreatedTime()+
                    ", last updated time: "+ cur_drone.getLastUpdatedTime());

            if(cur_drone.get_assigned_drone_pilot() != null
                    && _account_pilot_map.containsKey(cur_drone.get_assigned_drone_pilot()))
            {
                DronePilot cur_pilot = _account_pilot_map.get(cur_drone.get_assigned_drone_pilot());
                System.out.println(",flown_by:" + cur_pilot.get_first_name() +
                        "_" + cur_pilot.get_last_name() );
            }
            else
            {
                System.out.println();
            }
        }
    }

    public void set_drone_pilot(String drone_id, DronePilot pilot)
    {
        if(this.drone_map.containsKey(drone_id))
        {
            Drone cur_drone = this.drone_map.get(drone_id);
            if(!Objects.equals(pilot.get_assigned_drone_id(), drone_id) && pilot.get_assigned_drone_id()!= null)
            {
                // If pilot is reassigned to another drone, we must remove pilot from old drone
                Drone old_drone = pilot.get_assigned_drone();
                old_drone.remove_pilot_from_drone();
            }
            cur_drone.set_assigned_drone_pilot(pilot.get_account_id());
            pilot.set_assigned_drone(cur_drone);
            System.out.println("OK:change_completed");
        }
        else
        {
            System.out.println("ERROR:drone_identifier_does_not_exist");
        }
    }

    public void add_order_to_store(Order _order)
    {
        if(this.order_map.containsKey(_order.get_order_id()))
        {
            System.out.println("ERROR:order_identifier_already_exists");
        }
        else
        {
            this.order_map.put(_order.get_order_id(), _order);
            Drone cur_drone = this.drone_map.get(_order.get_drone_id());
            cur_drone.add_order_to_drone(_order);
            System.out.println("OK:change_completed");
        }
    }

    public void display_all_orders()
    {
        for(SortedMap.Entry<String, Order> entry: order_map.entrySet())
        {
            Order cur_order = entry.getValue();
            System.out.println("orderID:" + cur_order.get_order_id());
            cur_order.print_lines();
        }
    }

    public void audit_all_orders()
    {
        for(SortedMap.Entry<String, Order> entry: order_map.entrySet())
        {
            Order cur_order = entry.getValue();
            System.out.println("orderID:" + cur_order.get_order_id()+
                    ", created time: "+ cur_order.getCreatedTime()+
                    ", last updated time: "+ cur_order.getLastUpdatedTime());
            cur_order.audit_lines();
        }
    }

    public void add_item_to_order(String _order_id, String _item_name, int _quantity, int _unit_price, SortedMap<String, Customer> _account_customer_map)
    {
        if(!order_map.containsKey(_order_id))
        {
            System.out.println("ERROR:order_identifier_does_not_exist");
        }
        else if (!item_map.containsKey(_item_name))
        {
            System.out.println("ERROR:item_identifier_does_not_exist");
        }
        else
        {
            Order cur_order = this.get_order(_order_id);
            Item cur_item = this.get_item(_item_name);
            cur_order.add_item_to_order(cur_item, _quantity, _unit_price);
        }
    }

    public Boolean order_exist(String _order_id)
    {
        return this.order_map.containsKey(_order_id);
    }

    public Order get_order(String _order_id)
    {
        return this.order_map.get(_order_id);
    }

    public void execute_order(String _order_id, SortedMap<String, DronePilot> _account_pilot_map)
    {
        if(!this.order_map.containsKey(_order_id))
        {
            System.out.println("ERROR:order_identifier_does_not_exist");
        }
        else
        {
            Order cur_order = this.order_map.get(_order_id);
            Drone cur_drone = cur_order.get_drone();
            if(cur_drone.get_assigned_drone_pilot() == null)
            {
                System.out.println("ERROR:drone_needs_pilot");
            }
            else if(cur_drone.get_no_of_trips_remaining() <= 0)
            {
                System.out.println("ERROR:drone_needs_fuel");
            }
            else
            {
                Customer cur_customer = cur_order.get_customer();
                cur_customer.increase_credit(-cur_order.get_total_price());
                this.increment_current_revenue(cur_order.get_total_price());
                cur_drone.increase_no_of_trips_remaining(-1);
                cur_drone.remove_order_from_drone(cur_order);
                _account_pilot_map.get(cur_drone.get_assigned_drone_pilot()).increment_no_of_successful_deliveries();
                this.cancel_order(cur_order);
            }
        }
    }

    public void cancel_order(Order _order)
    {
        String order_id = _order.get_order_id();
        if(!this.order_map.containsKey(order_id))
        {
            System.out.println("ERROR:order_identifier_does_not_exist");
        }
        else
        {
            Drone cur_drone = _order.get_drone();
            cur_drone.remove_order_from_drone(_order);
            this.order_map.remove(order_id);
            System.out.println("OK:change_completed");
        }
    }
}