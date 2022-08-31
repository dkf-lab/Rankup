package me.dkflab.rankup.inventories;

import me.dkflab.rankup.Rankup;
import me.dkflab.rankup.Utils;
import me.dkflab.rankup.objects.Rank;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class DisplayScreen implements InventoryHolder {
    private final Inventory inv;
    private Rankup main;
    public DisplayScreen(Rankup main) {
        this.main = main;
        inv = Bukkit.createInventory(this, 9, Utils.color("&b&lRanks")); //54 max size
        init();
    }

    private void init() {
        inv.setItem(0, main.getRanks().get("wanderer").getItem());
        inv.setItem(1, main.getRanks().get("citizen").getItem());
        inv.setItem(2, main.getRanks().get("merchant").getItem());
        inv.setItem(3, main.getRanks().get("novice").getItem());
        inv.setItem(4, main.getRanks().get("apprentice").getItem());
        inv.setItem(5, main.getRanks().get("lord").getItem());

        for (int i = 0; i < 3; i++) {
            inv.setItem(inv.firstEmpty(), Utils.blankPane());
        }
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
