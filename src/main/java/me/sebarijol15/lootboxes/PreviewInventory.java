package me.sebarijol15.lootboxes;

import me.sebarijol15.lootboxes.Util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class PreviewInventory implements InventoryHolder {
    private Inventory inventory;

    public PreviewInventory() {
        this.inventory = Bukkit.createInventory(null, 9, "Vista previa de la Lootbox");
        addItems(this.getInventory());
        ItemBuilder.fillEmptySlots(inventory);
    }

    public void addItems(Inventory inventory) {

    }

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
