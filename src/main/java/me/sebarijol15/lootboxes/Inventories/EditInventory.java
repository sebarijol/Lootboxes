package me.sebarijol15.lootboxes.Inventories;

import me.sebarijol15.lootboxes.Events.InventoryEvents;
import me.sebarijol15.lootboxes.LootBoxes;
import me.sebarijol15.lootboxes.Util.FileManager;
import me.sebarijol15.lootboxes.Util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class EditInventory implements InventoryHolder {
    String currentLootbox = InventoryEvents.getClickedLootbox();

    private FileManager fileManager = LootBoxes.getFileManager();
    private Inventory inventory;

    public EditInventory(Player player) {
        this.inventory = Bukkit.createInventory(player, 9, currentLootbox);
        addItems(player);
    }

    private void addItems(Player player) {
        new ItemBuilder(Material.ARROW)
                .setDisplayName("Volver")
                .setLocalizedName("go_back")
                .addToInventory(inventory,0);

        new ItemBuilder(Material.DIAMOND)
                .setDisplayName("Contenidos")
                .setLocalizedName("contents")
                .addToInventory(inventory, 1);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
