package edu.gatech.cs6310;
import java.time.LocalDateTime;

class Item
{
    private final String item_name;
    private final int item_weight;
    private LocalDateTime createdTime;


    public Item(String _item_name, int _item_weight)
    {
        item_name = _item_name;
        item_weight = _item_weight;
        this.createdTime=LocalDateTime.now();
    }

    public String get_item_name()
    {
        return item_name;
    }

    public int get_item_weight()
    {
        return item_weight;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
}