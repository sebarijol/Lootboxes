package me.sebarijol15.lootboxes.Util;

import me.sebarijol15.lootboxes.LootBoxes;
import me.sebarijol15.lootboxes.Util.FileManager;
import me.sebarijol15.lootboxes.Util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class LootboxItem implements Listener {
    private LootBoxes plugin;
    private FileManager fileManager;
    private List<ItemBuilder> contents;

    public LootboxItem(FileManager fileManager, LootBoxes plugin) {
        this.fileManager = fileManager;
        this.plugin = plugin;
    }

    public ItemStack getLootboxById(String lootboxId, FileManager fileManager) {
        return new ItemBuilder(Material.PLAYER_HEAD)
                .setSkullTexture(Objects.requireNonNull(fileManager.getLootboxConfig().getString(lootboxId + ".texture")))
                .setPersistentData(new NamespacedKey(plugin, lootboxId), PersistentDataType.STRING, lootboxId)
                .setDisplayName(fileManager.getLootboxConfig().getString(lootboxId + ".name"))
                .addLoreLine(fileManager.getMessages().getString("rightClickToOpen"))
                .addLoreLine(fileManager.getMessages().getString("shiftRightClickToViewContents"))
                .toItemStack();
    }

    @EventHandler
    public void onLootboxOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().isRightClick() && event.getClickedBlock() == null) {
            ItemStack item = event.getItem();
            if (item == null) return;
            PersistentDataContainer persistentDataContainer = item.getItemMeta().getPersistentDataContainer();

            Set<String > lootboxes = fileManager.getLootboxConfig().getKeys(false);
            for (String lootboxId : lootboxes) {
                for (NamespacedKey key : persistentDataContainer.getKeys()) {
                    if (key.getKey().startsWith(lootboxId)) {
                        List<String> contents = fileManager.getLootboxConfig().getStringList(lootboxId + ".contents");
                        player.getInventory().setItemInMainHand(null);
                        for (String content : contents) {
                            String[] parts = content.split(";");

                            String material = parts[0];
                            int amount = Integer.parseInt(parts[1]);
                            double probability = Double.parseDouble(parts[2]);
                            double targetProbability = Math.random() * 100;

                            if (targetProbability < probability) {
                                ItemStack reward = new ItemBuilder(Material.valueOf(material))
                                        .setAmount(amount)
                                        .toItemStack();

                                player.getInventory().addItem(reward);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onLootboxPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        PersistentDataContainer persistentDataContainer = item.getItemMeta().getPersistentDataContainer();

        Set<String> lootboxes = fileManager.getLootboxConfig().getKeys(false);
        for (String lootboxId : lootboxes) {
            for (NamespacedKey key : persistentDataContainer.getKeys()) {
                if (key.getKey().startsWith(lootboxId)) {
                    event.getPlayer().sendMessage("You can't place a lootbox.");
                    event.setCancelled(true);
                }
            }
        }
    }
}
