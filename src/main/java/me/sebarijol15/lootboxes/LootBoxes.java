package me.sebarijol15.lootboxes;

import me.sebarijol15.lootboxes.Util.CommandManager;
import me.sebarijol15.lootboxes.Util.FileManager;
import me.sebarijol15.lootboxes.Util.LootboxItem;
import org.bukkit.plugin.java.JavaPlugin;

public final class LootBoxes extends JavaPlugin {
    @Override
    public void onEnable() {
        FileManager fileManager = new FileManager(this);
        fileManager.setupConfig();

        LootboxItem lootbox = new LootboxItem(fileManager, this);

        getCommand("lootbox").setExecutor(new CommandManager(this, fileManager, lootbox));
        getCommand("lootbox").setTabCompleter(new CommandManager(this, fileManager, lootbox));
        getServer().getPluginManager().registerEvents(new LootboxItem(fileManager, this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
