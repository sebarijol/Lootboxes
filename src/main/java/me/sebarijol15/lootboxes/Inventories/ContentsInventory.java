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

public class ContentsInventory implements InventoryHolder {
    private FileManager fileManager = LootBoxes.getFileManager();
    private Inventory inventory;
    private String currentLootbox;

    public ContentsInventory(Player player) {
        this.inventory = Bukkit.createInventory(player, 54, "Lootboxes");
        this.currentLootbox = InventoryEvents.getClickedLootbox();
        addItems(player);
        ItemBuilder.fillEmptySlots(inventory);
    }

    private void addItems(Player player) {
        new ItemBuilder(Material.ARROW)
                .setDisplayName("Volver")
                .setLocalizedName("go_back")
                .addToInventory(inventory, 45);


        for (String content : fileManager.getLootboxConfig().getStringList(currentLootbox + ".contents")) {
            new ItemBuilder(Material.valueOf(content.split(";")[0]))
                    .addLoreLine("Cantidad: " + Integer.parseInt(content.split(";")[1]))
                    .addLoreLine("Probabilidad: " + Double.parseDouble(content.split(";")[2]))
                    .addToInventory(inventory);
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
