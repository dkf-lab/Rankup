package me.dkflab.rankup.events;

import me.dkflab.rankup.Rankup;
import me.dkflab.rankup.Utils;
import me.dkflab.rankup.inventories.DisplayScreen;
import me.dkflab.rankup.inventories.RankupScreen;
import me.dkflab.rankup.objects.Rank;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryEvents implements Listener {

    private Rankup main;
    public InventoryEvents(Rankup main) {
        this.main = main;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) { return; }
        if (e.getClickedInventory().getHolder() instanceof DisplayScreen) {
            e.setCancelled(true);
        }

        if (e.getClickedInventory().getHolder() instanceof RankupScreen) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            Rank rank = null;
            for (Rank r : main.getRanks().values()) {
                if (e.getClickedInventory().contains(r.getItem())) {
                    rank = r;
                }
            }
            if (rank == null) {
                Utils.error(player, "Invalid rank.");
            }
            if (e.getCurrentItem() == null) { return; }
            if (e.getCurrentItem().getType() == Material.LIME_STAINED_GLASS_PANE) {
                // Accept purchase
                EconomyResponse r = main.econ.withdrawPlayer(player, rank.getPrice());
                // Set rank
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "lp user " + player.getName() + " parent add " + rank.getGroup());
                Utils.success(player, "&7Bought " + rank.getName() + "&7 successfully.");
                player.closeInventory();
            }
            else if (e.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE) {
                // Deny purchase
                player.closeInventory();
            }
        }
    }
}