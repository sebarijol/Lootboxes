package me.sebarijol15.lootboxes.Util;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class FileManager {
    private final JavaPlugin plugin;
    private final File lootboxFile;
    private final File messagesFile;
    private FileConfiguration lootboxConfig;
    private FileConfiguration messagesConfig;

    public FileManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.messagesFile = new File(this.plugin.getDataFolder(), "messages.yml");
        this.messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        this.lootboxFile = new File(this.plugin.getDataFolder(), "lootboxes.yml");
        this.lootboxConfig = YamlConfiguration.loadConfiguration(lootboxFile);
    }

    public void setupConfig() {
        if (!lootboxFile.exists()) {
            plugin.saveResource("lootboxes.yml", false);
            lootboxFile.getParentFile().mkdirs();
        }
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
            messagesFile.getParentFile().mkdirs();
        }
    }

    public FileConfiguration getLootboxConfig() {
        return lootboxConfig;
    }
    public FileConfiguration getMessages() { return messagesConfig; }

    public void saveConfig() {
        try {
            lootboxConfig.save(lootboxFile);
            messagesConfig.save(messagesFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("Falló algo al guardar de la configuración de las lootboxes");
        }
    }

    public void reloadLootboxes() {
        lootboxConfig = YamlConfiguration.loadConfiguration(lootboxFile);
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }
}
