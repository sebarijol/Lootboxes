package me.sebarijol15.lootboxes.Events;

import me.sebarijol15.lootboxes.Commands.GetCommand;
import me.sebarijol15.lootboxes.LootBoxes;
import me.sebarijol15.lootboxes.Util.FileManager;
import me.sebarijol15.lootboxes.Util.HexUtil;
import me.sebarijol15.lootboxes.Util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LootboxEvents implements Listener {
    private HexUtil hexUtil = new HexUtil();
    private FileManager fileManager;
    public LootboxEvents() {
        fileManager = LootBoxes.getFileManager();
    }

    @EventHandler
    public void onLootboxOpen(PlayerInteractEvent event) {
        if (event.getAction().isRightClick()) {
            ItemStack item = event.getItem();
            Player player = event.getPlayer();
            if (event.getItem() == null) {
                return;
            }
            String lootboxId = event.getItem().getItemMeta().getLocalizedName();
            player.sendMessage(hexUtil.translateHexCodes(fileManager.getMessages().getString("lootboxOpen")));

            if (!(item.getItemMeta().getLocalizedName().equals(lootboxId))) {
                return;
            }

            FileConfiguration lootboxConfig = fileManager.getLootboxConfig();
            List<String> contents = lootboxConfig.getStringList( lootboxId + ".contents");
            for (String content : contents) {
                String[] contentParts = content.split(";");

                if (contentParts.length == 3) {
                    String materialName = contentParts[0].trim();
                    int amount = Integer.parseInt(contentParts[1].trim());
                    double probability = Double.parseDouble(contentParts[2].trim());
                    double targetProbability = Math.random() * 100;
                    if (targetProbability < probability) {
                        ItemStack reward = new ItemBuilder(Material.valueOf(materialName))
                                .setAmount(amount)
                                .toItemStack();

                        player.getInventory().addItem(reward);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onLootboxPlace(BlockPlaceEvent event) {
        String lootboxId = GetCommand.getLootboxId();
        ItemStack itemInHand = event.getItemInHand();
        if (itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasDisplayName()) {
            String localizedName = itemInHand.getItemMeta().getLocalizedName();
            if (localizedName.equals(lootboxId)) {
                event.setCancelled(true);
            }
        }
    }
}
