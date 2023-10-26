package me.sebarijol15.lootboxes;

import me.sebarijol15.lootboxes.Events.InventoryEvents;
import me.sebarijol15.lootboxes.Events.LootboxEvents;
import me.sebarijol15.lootboxes.Inventories.ListInventory;
import me.sebarijol15.lootboxes.Util.CommandManager;
import me.sebarijol15.lootboxes.Util.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public final class LootBoxes extends JavaPlugin {
    private static FileManager fileManager;
    private ListInventory listInventory;
    @Override
    public void onEnable() {
        fileManager = new FileManager(this);
        fileManager.setupConfig();

        getCommand("lootbox").setExecutor(new CommandManager(getFileManager()));
        getCommand("lootbox").setTabCompleter(new CommandManager(getFileManager()));
        getServer().getPluginManager().registerEvents(new LootboxEvents(), this);
        getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FileManager getFileManager() {
        return fileManager;
    }
}
