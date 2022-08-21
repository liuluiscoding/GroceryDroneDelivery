package edu.gatech.cs6310;
import java.io.*;
import java.util.*;

public class DeliveryService
{
    private SortedMap<String, Store> store_map = new TreeMap<>();
    private SortedMap<String, DronePilot> account_pilot_map = new TreeMap<>();
    private SortedMap<String, Customer> account_customer_map = new TreeMap<>();
    private Set<String> pilot_license_set = new HashSet<>();
    public void commandLoop(String role)
    {
        Scanner commandLineInput = new Scanner(System.in);
        final String DELIMITER = ",";

        while (true)
        {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                String wholeInputLine = commandLineInput.nextLine();
                String[] tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);

                if (tokens[0].equals("make_store"))
                {
                    if (role.equals("admin")) {
                        String store_name = tokens[1];
                        String revenue = tokens[2];
                        if (store_map.containsKey(store_name)) {
                            System.out.println("ERROR:store_identifier_already_exists");
                        } else {
                            Store store = new Store(store_name, Integer.parseInt(revenue));
                            store_map.put(store_name, store);
                        }
                    } else {
                        System.out.println("Permission denied.");
                    }
                }

                else if (tokens[0].equals("display_stores"))
                {
                    if (role.equals("admin")) {
                        for (SortedMap.Entry<String, Store> entry : store_map.entrySet()) {
                            Store value = entry.getValue();
                            System.out.println("name:" + value.get_store_name() +
                                    ",revenue:" + value.get_current_revenue());
                        }
                        System.out.println("OK:display_completed");
                    } else {
                        System.out.println("Permission denied.");
                    }
                }
                else if (tokens[0].equals("audit_stores"))
                {   if (role.equals("admin")) {
                        for(SortedMap.Entry<String, Store> entry: store_map.entrySet()) {
                            Store value = entry.getValue();
                            System.out.println("name:" + value.get_store_name() +
                            ",revenue:" + value.get_current_revenue()+
                            ",created time:" + value.getCreatedTime());

                        }
                        System.out.println("OK:audit_completed");

                    }else {
                        System.out.println("Permission denied.");
                }

                }
                else if (tokens[0].equals("sell_item"))
                {
                    if (role.equals("admin") || role.equals("manager")) {
                        String store_name = tokens[1];
                        String item_name = tokens[2];
                        String weight = tokens[3];
                        if (!store_map.containsKey(store_name)) {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        } else {
                            Store cur_store = store_map.get(store_name);
                            Item cur_item = new Item(item_name, Integer.parseInt(weight));
                            cur_store.add_item_to_store(cur_item);
                        }
                    } else {
                        System.out.println("Permission denied.");
                    }
                }

                else if (tokens[0].equals("display_items"))
                {
                    if (role.equals("admin") || role.equals("manager")) {
                        String store_name = tokens[1];
                        Store cur_store = store_map.get(store_name);
                        if (cur_store != null) {
                            cur_store.display_all_items();
                        } else {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        }
                    } else {
                        System.out.println("Permission denied.");
                    }
                }

                else if (tokens[0].equals("audit_item_catalog"))
                {
                    if (role.equals("admin") || role.equals("manager")) {
                        String store_name = tokens[1];
                        Store cur_store = store_map.get(store_name);
                        if (cur_store != null)
                        {
                            cur_store.audit_item_catalog();
                        }
                        else
                        {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        }

                    }else {
                        System.out.println("Permission denied.");
                    }

                }


                else if (tokens[0].equals("make_pilot"))
                {
                    if (role.equals("admin") || role.equals("manager")) {
                        String account = tokens[1];
                        String first_name = tokens[2];
                        String last_name = tokens[3];
                        String phone = tokens[4];
                        String tax = tokens[5];
                        String license = tokens[6];
                        String experience = tokens[7];
                        if (account_pilot_map.containsKey(account)) {
                            System.out.println("ERROR:pilot_identifier_already_exists");
                        } else if (pilot_license_set.contains(license)) {
                            System.out.println("ERROR:pilot_license_already_exists");
                        } else {
                            DronePilot pilot = new DronePilot(account, first_name, last_name, phone, tax, license, Integer.parseInt(experience));
                            account_pilot_map.put(account, pilot);
                            pilot_license_set.add(license);
                        }
                    } else {
                        System.out.println("Permission denied.");
                    }
                }

                else if (tokens[0].equals("display_pilots"))
                {
                    if (role.equals("admin") || role.equals("manager")) {
                        for (SortedMap.Entry<String, DronePilot> entry : account_pilot_map.entrySet()) {
                            DronePilot value = entry.getValue();
                            System.out.println("name:" + value.get_first_name()
                                    + "_" + value.get_last_name()
                                    + ",phone:" + value.get_phone_number()
                                    + ",taxID:" + value.get_ssn()
                                    + ",licenseID:" + value.get_license_id()
                                    + ",experience:" + value.get_no_of_successful_deliveries());
                        }
                        System.out.println("OK:display_completed");
                    }  else {
                        System.out.println("Permission denied.");
                    }
                }

                else if (tokens[0].equals("audit_pilots"))
                {
                    if (role.equals("admin") || role.equals("manager")) {
                        for(SortedMap.Entry<String, DronePilot> entry: account_pilot_map.entrySet())
                        {
                            DronePilot value = entry.getValue();
                            System.out.println("name:" + value.get_first_name()
                                    + "_" + value.get_last_name()
                                    + ",phone:" + value.get_phone_number()
                                    + ",taxID:" + value.get_ssn()
                                    + ",licenseID:" + value.get_license_id()
                                    + ",experience:" + value.get_no_of_successful_deliveries()
                                    +", created time: "+ value.getCreatedTime()+
                                    ", last updated time: "+ value.getLastUpdatedTime());
                        }
                        System.out.println("OK:audit_completed");
                    }else {
                        System.out.println("Permission denied.");
                    }

                }


                else if (tokens[0].equals("make_drone"))
                {
                    if (role.equals("admin") || role.equals("manager")) {
                        String store_name = tokens[1];
                        String drone_name = tokens[2];
                        String capacity = tokens[3];
                        String fuel = tokens[4];
                        if (!store_map.containsKey(store_name)) {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        } else {
                            Store cur_store = store_map.get(store_name);
                            Drone cur_drone = new Drone(drone_name, Integer.parseInt(capacity), Integer.parseInt(fuel));
                            cur_store.add_drone_to_store(cur_drone);
                        }
                    } else {
                        System.out.println("Permission denied.");
                    }
                }

                else if (tokens[0].equals("display_drones"))
                {
                    if (role.equals("admin") || role.equals("manager")) {
                        String store_name = tokens[1];
                        if (!store_map.containsKey(store_name)) {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        } else {
                            Store cur_store = store_map.get(store_name);
                            cur_store.display_all_drones(account_pilot_map);
                            System.out.println("OK:display_completed");
                        }
                    } else {
                        System.out.println("Permission denied.");
                    }
                }

                else if (tokens[0].equals("audit_all_drones"))
                {
                    if (role.equals("admin") || role.equals("manager")) {
                        String store_name = tokens[1];
                        if(!store_map.containsKey(store_name))
                        {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        }
                        else
                        {
                            Store cur_store = store_map.get(store_name);
                            cur_store.audit_all_drones(account_pilot_map);
                            System.out.println("OK:audit_completed");
                        }
                    }else {
                        System.out.println("Permission denied.");
                    }

                }


                else if (tokens[0].equals("fly_drone"))
                {
                    if (role.equals("admin") || role.equals("pilot")) {
                        String store_name = tokens[1];
                        String drone_id = tokens[2];
                        String pilot_id = tokens[3];
                        if (!store_map.containsKey(store_name)) {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        } else if (!account_pilot_map.containsKey(pilot_id)) {
                            System.out.println("ERROR:pilot_identifier_does_not_exist");
                        } else {
                            Store cur_store = store_map.get(store_name);
                            DronePilot cur_pilot = account_pilot_map.get(pilot_id);
                            cur_store.set_drone_pilot(drone_id, cur_pilot);
                        }
                    } else {
                        System.out.println("Permission denied.");
                    }
                }

                else if (tokens[0].equals("make_customer"))
                {
                    if (role.equals("admin")) {
                        String customer_account = tokens[1];
                        String first_name = tokens[2];
                        String last_name = tokens[3];
                        String phone = tokens[4];
                        String rating = tokens[5];
                        String credit = tokens[6];
                        if (account_customer_map.containsKey(customer_account)) {
                            System.out.println("ERROR:customer_identifier_already_exists");
                        } else {
                            Customer cur_customer = new Customer(customer_account,
                                    first_name,
                                    last_name,
                                    phone,
                                    Integer.parseInt(rating),
                                    Integer.parseInt(credit));
                            account_customer_map.put(customer_account, cur_customer);
                        }
                    } else {
                        System.out.println("Permission denied.");
                    }
                }

                else if (tokens[0].equals("display_customers"))
                {
                    if (role.equals("admin") || role.equals("manager")) {
                        for (SortedMap.Entry<String, Customer> entry : account_customer_map.entrySet()) {
                            Customer value = entry.getValue();
                            System.out.println("name:" + value.get_first_name() + "_" + value.get_last_name()
                                    + ",phone:" + value.get_phone_number()
                                    + ",rating:" + value.get_rating()
                                    + ",credit:" + value.get_total_credit());
                        }
                        System.out.println("OK:display_completed");
                    } else {
                        System.out.println("Permission denied.");
                    }
                }

                else if (tokens[0].equals("audit_customers"))
                {
                    if (role.equals("admin") || role.equals("manager")) {
                        for(SortedMap.Entry<String, Customer> entry: account_customer_map.entrySet())
                        {
                            Customer value = entry.getValue();
                            System.out.println("name:" + value.get_first_name() + "_" + value.get_last_name()
                                    + ",phone:" + value.get_phone_number()
                                    + ",rating:" + value.get_rating()
                                    + ",credit:" + value.get_total_credit()
                                    +", created time: "+ value.getCreatedTime()+
                                    ", last updated time: "+ value.getLastUpdatedTime())
                            ;
                        }
                        System.out.println("OK:display_completed");
                    }
                    else {
                        System.out.println("Permission denied.");
                    }

                }

                else if (tokens[0].equals("start_order"))
                {
                    if (role.equals("admin") || role.equals("customer")) {
                        String store_id = tokens[1];
                        String order_id = tokens[2];
                        String drone_id = tokens[3];
                        String customer_id = tokens[4];
                        if (!store_map.containsKey(store_id)) {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        } else if (!account_customer_map.containsKey(customer_id)) {
                            System.out.println("ERROR:customer_identifier_does_not_exist");
                        } else {
                            Store cur_store = store_map.get(store_id);
                            if (!cur_store.get_drone_map().containsKey(drone_id)) {
                                System.out.println("ERROR:drone_identifier_does_not_exist");
                            } else {
                                Drone cur_drone = cur_store.get_drone_map().get(drone_id);
                                Customer cur_customer = account_customer_map.get(customer_id);
                                Order cur_order = new Order(cur_store.get_store_name(), order_id, cur_drone, cur_customer);
                                cur_customer.add_order_to_customer(cur_order);
                                cur_store.add_order_to_store(cur_order);
                            }
                        }
                    } else {
                        System.out.println("Permission denied.");
                    }
                }

                else if (tokens[0].equals("display_orders"))
                {
                    if (role.equals("admin") || role.equals("manager")) {
                        String store_id = tokens[1];
                        if (!store_map.containsKey(store_id)) {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        } else {
                            Store cur_store = store_map.get(store_id);
                            cur_store.display_all_orders();
                            System.out.println("OK:display_completed");
                        }
                    } else {
                        System.out.println("Permission denied.");
                    }
                }

                else if (tokens[0].equals("audit_all_orders"))
                {
                    if (role.equals("admin") || role.equals("manager")) {
                        String store_id = tokens[1];
                        if(!store_map.containsKey(store_id))
                        {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        }
                        else
                        {
                            Store cur_store = store_map.get(store_id);
                            cur_store.audit_all_orders();
                            System.out.println("OK:audit_completed");
                        }
                    }
                    else {
                        System.out.println("Permission denied.");
                    }

                }

                else if (tokens[0].equals("request_item"))
                {
                    if (role.equals("admin") || role.equals("customer")) {
                        String store_name = tokens[1];
                        String order = tokens[2];
                        String item = tokens[3];
                        String quantity = tokens[4];
                        String unit_price = tokens[5];
                        if (!store_map.containsKey(store_name)) {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        } else {
                            Store cur_store = store_map.get(store_name);
                            cur_store.add_item_to_order(order, item, Integer.parseInt(quantity), Integer.parseInt(unit_price), this.account_customer_map);
                        }
                    } else {
                        System.out.println("Permission denied.");
                    }
                }

                else if (tokens[0].equals("purchase_order"))
                {
                    if (role.equals("admin") || role.equals("customer")) {
                        String store_id = tokens[1];
                        String order_id = tokens[2];
                        if (!store_map.containsKey(store_id)) {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        } else {
                            Store cur_store = store_map.get(store_id);
                            cur_store.execute_order(order_id, this.account_pilot_map);
                        }
                    } else {
                        System.out.println("Permission denied.");
                    }
                }

                else if (tokens[0].equals("cancel_order"))
                {
                    if (role.equals("admin") || role.equals("manager") || role.equals("customer")) {
                        String store_name = tokens[1];
                        String order_id = tokens[2];
                        if (!store_map.containsKey(store_name)) {
                            System.out.println("ERROR:store_identifier_does_not_exist");
                        } else {
                            Store cur_store = store_map.get(store_name);
                            if (cur_store.order_exist(order_id)) {
                                cur_store.cancel_order(cur_store.get_order(order_id));
                            } else {
                                System.out.println("ERROR:order_identifier_does_not_exist");
                            }
                        }
                    } else {
                        System.out.println("Permission denied.");
                    }
                }

                else if (tokens[0].equals("logout")) {
                    role = Util.authenticate();
                }

                else if (tokens[0].equals("stop"))
                {
                    System.out.println("stop acknowledged");
                    break;
                }
                else
                {
                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }
}
