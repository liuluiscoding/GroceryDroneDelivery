package edu.gatech.cs6310;

import java.time.LocalDateTime;

class DronePilot extends Employee
{
    private final String account_id;
    private final String license_id;
    private String assigned_drone_id;
    private Drone assigned_drone;
    private int no_of_successful_deliveries;
    private LocalDateTime createdTime;
    private LocalDateTime lastUpdatedTime;

    DronePilot(String _account_id, String _first_name, String _last_name, String _phone_number, String _ssn, String _license_id, int _no_of_successful_deliveries)
    {
        super(_last_name, _first_name, _phone_number, _ssn);
        this.account_id = _account_id;
        this.license_id = _license_id;
        this.no_of_successful_deliveries = _no_of_successful_deliveries;
        this.createdTime=LocalDateTime.now();
        this.lastUpdatedTime=LocalDateTime.now();
        System.out.println("OK:change_completed");
    }

    public String get_account_id()
    {
        return this.account_id;
    }

    public String get_license_id()
    {
        return this.license_id;
    }

    public void set_assigned_drone(Drone _drone)
    {
        this.assigned_drone = _drone;
        this.assigned_drone_id = _drone.get_drone_id();
        this.lastUpdatedTime=LocalDateTime.now();
    }

    public String get_assigned_drone_id()
    {
        return this.assigned_drone_id;
    }

    public Drone get_assigned_drone()
    {
        return this.assigned_drone;
    }

    public int get_no_of_successful_deliveries()
    {
        return this.no_of_successful_deliveries;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void increment_no_of_successful_deliveries()
    {
        this.no_of_successful_deliveries += 1;
        this.lastUpdatedTime=LocalDateTime.now();
    }
}