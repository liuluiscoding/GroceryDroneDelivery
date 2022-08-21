package edu.gatech.cs6310;

import java.time.LocalDateTime;

class Line
{
    private Item item;
    private int line_quantity;
    private int line_price;
    private int line_weight;
    private LocalDateTime createdTime;
    private LocalDateTime lastUpdatedTime;

    Line(Item _item, int _item_quantity, int _item_price)
    {
        this.item = _item;
        this.line_quantity = _item_quantity;
        this.line_price = _item_price * this.line_quantity;
        this.line_weight = _item.get_item_weight() * this.line_quantity;
        this.createdTime=LocalDateTime.now();
        this.lastUpdatedTime=LocalDateTime.now();
    }

    public void add_item_to_line(int _item_quantity, int _item_price)
    {
        this.line_quantity += _item_quantity;
        this.line_price += _item_quantity * _item_price;
        this.line_weight += _item_quantity * this.item.get_item_weight();
        this.lastUpdatedTime=LocalDateTime.now();
    }

    public String get_line_item_name()
    {
        return this.item.get_item_name();
    }

    public int get_line_quantity()
    {
        return this.line_quantity;
    }

    public int get_line_price()
    {
        return this.line_price;
    }

    public int get_line_weight()
    {
        return this.line_weight;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }
}