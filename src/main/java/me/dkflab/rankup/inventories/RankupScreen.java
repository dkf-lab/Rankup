package me.dkflab.rankup.inventories;

import me.dkflab.rankup.Utils;
import me.dkflab.rankup.objects.Rank;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankupScreen implements InventoryHolder {

    private final Inventory inv;
    private Rank rank;
    public RankupScreen(Rank rank) {
        inv = Bukkit.createInventory(this, 9, Utils.color("&lRankup Confirmation")); //54 max size
        this.rank = rank;
        init();
    }

    private void init() {
        ItemStack item;
        for (int i = 0; i < 4; i++) {
            item = createItem("§a§lAccept", Material.LIME_STAINED_GLASS_PANE, Collections.singletonList("§7Accepts the request!"));
            inv.setItem(i, item);
        }

        inv.setItem(inv.firstEmpty(), rank.getItem());

        for (int i = 5; i < 9; i++) {
            item = createItem("§c§lDeny", Material.RED_STAINED_GLASS_PANE, Collections.singletonList("§7Denies the request!"));
            inv.setItem(i, item);
        }
    }

    private ItemStack createItem(String name, Material mat, List<String> lore) {
        ItemStack item = new ItemStack(mat, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
