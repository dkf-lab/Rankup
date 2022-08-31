package me.dkflab.rankup.commands;

import me.dkflab.rankup.Rankup;
import me.dkflab.rankup.inventories.DisplayScreen;
import me.dkflab.rankup.inventories.RankupScreen;
import me.dkflab.rankup.objects.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static me.dkflab.rankup.Utils.*;

public class MainCommand implements CommandExecutor, TabCompleter{

    private Rankup main;
    public MainCommand (Rankup main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            notPlayer(sender);
            return true;
        }
        Player p = (Player) sender;
        if (command.getName().equalsIgnoreCase("rankup")) {
            // Get players current rank
            Rank rank = main.getPlayerRank(p);
            Bukkit.getLogger().info("Current Rank: " + rank.getGroup());
            // Check if player has sufficient balance to rankup
            if (rank.getNextRank() == null) {
                error(sender, "You cannot rank up any further!");
                return true;
            }
            if (main.econ.getBalance(p) < rank.getNextRank().getPrice()) {
                error(sender, "Insufficient balance. You need at least &6$" + rank.getNextRank().getPrice() + "&7.");
                return true;
            }
            // Open rankup GUI
            p.openInventory(new RankupScreen(rank.getNextRank()).getInventory());
        }
        if (command.getName().equalsIgnoreCase("ranks")) {
            // Open RANK GUI
            p.openInventory(new DisplayScreen(main).getInventory());
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
