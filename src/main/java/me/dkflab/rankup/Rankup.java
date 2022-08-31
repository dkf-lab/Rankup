package me.dkflab.rankup;

import me.dkflab.rankup.commands.MainCommand;
import me.dkflab.rankup.events.InventoryEvents;
import me.dkflab.rankup.objects.Rank;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Rankup extends JavaPlugin {

    HashMap<String, Rank> ranks = new HashMap<>();
    public static Permission perms = null;
    public static Economy econ = null;

    public HashMap<String, Rank> getRanks() {
        return this.ranks;
    }

    @Override
    public void onEnable() {
        registerCommands();
        registerListeners();
        // Vault
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        // Create ranks
        // Lord
        List<String> lore = new ArrayList<>();
        lore.add("&ePerks:");
        lore.add("&7- 5 Auctions");
        lore.add("&7- 24 Chest Shops");
        lore.add("&7- 1 PW");
        lore.add("&7- /nation");
        lore.add("&7- /hat");
        lore.add("&7- /kit Lord");
        lore.add("&6Cost: $200k");
        ranks.put("lord", new Rank("&eLord", Material.DIAMOND, lore, 200000, null, "lord"));
        // Apprentice
        List<String> l1 = new ArrayList<>();
        l1.add("&aPerks:");
        l1.add("&7- 20 Chest Shops");
        l1.add("&7- /enderchest");
        l1.add("&6Cost: $150k");
        ranks.put("apprentice", new Rank("&aApprentice", Material.EMERALD, l1, 150000, ranks.get("lord"), "apprentice"));
        // Novice
        List<String> l2 = new ArrayList<>();
        l2.add("&3Perks:");
        l2.add("&7- 3 Auctions");
        l2.add("&7- 16 Chest Shops");
        l2.add("&7- /pet");
        l2.add("&7- /kit Novice");
        l2.add("&6Cost: $110k");
        ranks.put("novice", new Rank("&3Novice", Material.GOLD_INGOT, l2, 110000, ranks.get("apprentice"), "novice"));
        // Merchant
        List<String> l3 = new ArrayList<>();
        l3.add("&9Perks:");
        l3.add("&7- 2 Auctions");
        l3.add("&7- 3 Homes");
        l3.add("&7- 12 Chest Shops");
        l3.add("&7- 2 Jobs");
        l3.add("&7- Crawl");
        l3.add("&7- Lay");
        l3.add("&6Cost: $65k");
        ranks.put("merchant", new Rank("&9Merchant", Material.IRON_INGOT, l3, 65000, ranks.get("novice"), "merchant"));
        // Citizen
        List<String> l4 = new ArrayList<>();
        l4.add("&fPerks:");
        l4.add("&7- 2 Lottery Tickets");
        l4.add("&7- 2 Homes");
        l4.add("&7- /art");
        l4.add("&7- /marry");
        l4.add("&6Cost: $10k");
        ranks.put("citizen", new Rank("&fCitizen", Material.STONE, l4, 10000, ranks.get("merchant"), "citizen"));
        // Wanderer
        ranks.put("wanderer", new Rank("&7Wanderer", Material.STRING, Collections.singletonList("&7Default rank"),0,ranks.get("citizen"), "default"));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public Rank getPlayerRank (Player p) {
        String rank = "wanderer";
        List<String> groups = Arrays.asList(perms.getPlayerGroups(p));
        if (groups.contains("citizen")) {
            rank = "citizen";
        }
        if (groups.contains("merchant")) {
            rank = "merchant";
        }
        if (groups.contains("novice")) {
            rank = "novice";
        }
        if (groups.contains("apprentice")) {
            rank = "apprentice";
        }
        if (groups.contains("lord")) {
            rank = "lord";
        }
        return ranks.get(rank);
    }

    private void registerCommands() {
        MainCommand mc = new MainCommand(this);
        getCommand("rankup").setExecutor(mc);
        getCommand("ranks").setExecutor(mc);
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new InventoryEvents(this), this);
    }

    @Override
    public void onDisable() {

    }
}
