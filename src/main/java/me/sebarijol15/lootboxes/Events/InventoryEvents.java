package me.sebarijol15.lootboxes.Events;

import me.sebarijol15.lootboxes.Inventories.ContentsInventory;
import me.sebarijol15.lootboxes.Inventories.EditInventory;
import me.sebarijol15.lootboxes.LootBoxes;
import me.sebarijol15.lootboxes.Util.FileManager;
import me.sebarijol15.lootboxes.Util.HexUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import java.util.Set;

public class InventoryEvents implements Listener {
    private FileManager fileManager = LootBoxes.getFileManager();
    private Inventory fromInventory, toInventory;
    private static String clickedLootbox;
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        InventoryView view = event.getView();
        String originalTitle = view.getOriginalTitle();
        Set<String> lootboxKeys = fileManager.getLootboxConfig().getKeys(false);
        for (String lootboxKey : lootboxKeys) {
            if (originalTitle.contains(lootboxKey) || originalTitle.equals("Lootboxes")) {
                if (toInventory != null) {
                    fromInventory = toInventory;
                }
                toInventory = event.getInventory();
            }
        }
    }
    @EventHandler
    public void onInventoryClick (InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null || !(event.getCurrentItem().getItemMeta().hasLocalizedName())) { return; }
        String localizedName = event.getCurrentItem().getItemMeta().getLocalizedName();
        switch (localizedName) {
            case "go_back":
                player.openInventory(fromInventory);
                break;
            case "static_item":
                event.setCancelled(true);
                break;
            case "contents":
                ContentsInventory contentsInventory = new ContentsInventory(player);
                player.openInventory(contentsInventory.getInventory());
                break;
            case "lootbox":
                clickedLootbox = event.getCurrentItem().getItemMeta().getDisplayName();
                EditInventory editInventory = new EditInventory(player);
                player.openInventory(editInventory.getInventory());
                break;
        }
    }

    public static String getClickedLootbox() {
        return clickedLootbox;
    }
}
