package me.dkflab.rankup.objects;

import me.dkflab.rankup.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Rank {

    private Rank next;
    private String name,group;
    private List<String> description;
    private int price;
    private Material material;
    private ItemStack item;

    public Rank(String name, Material material, List<String> description, int price, Rank next, String group) {
        this.name = Utils.color(name);
        this.group = group;
        this.description = description;
        this.price = price;
        this.next = next;
        this.material = material;
    }

    public ItemStack getItem() {
        if (item == null) {
            item = Utils.createItem(getMaterial(), 1, getName(), description, null, null, false);
        }
        return item;
    }

    public Rank getNextRank() {
        return this.next;
    }

    public int getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }

    public Material getMaterial() {
        return this.material;
    }

    public String getGroup() {
        return this.group;
    }

    public List<String> getDescription() {
        return this.description;
    }
}
