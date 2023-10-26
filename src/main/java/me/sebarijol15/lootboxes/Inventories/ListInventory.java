package me.sebarijol15.lootboxes.Inventories;

import me.sebarijol15.lootboxes.LootBoxes;
import me.sebarijol15.lootboxes.Util.FileManager;
import me.sebarijol15.lootboxes.Util.HexUtil;
import me.sebarijol15.lootboxes.Util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class ListInventory implements InventoryHolder {
    private HexUtil hexUtil = new HexUtil();
    private FileManager fileManager = LootBoxes.getFileManager();
    private Inventory inventory;

    public ListInventory(Player player) {
        this.inventory = Bukkit.createInventory(player, 54, "Lootboxes");
        addItems(player);
        ItemBuilder.fillEmptySlots(inventory);
    }

    private void addItems(Player player) {
        Set<String> lootboxKeys = fileManager.getLootboxConfig().getKeys(false);
        for (String lootboxId : lootboxKeys) {
            new ItemBuilder(Material.PLAYER_HEAD)
                    .setSkullTexture(fileManager.getLootboxConfig().getString(lootboxId + ".texture"))
                    .setDisplayName(hexUtil.translateHexCodes(fileManager.getLootboxConfig().getString(lootboxId + ".name")))
                    .setLocalizedName("lootbox")
                    .addLoreLine(hexUtil.translateHexCodes(fileManager.getMessages().getString("id") + "&f" +lootboxId))
                    .addLoreLine(hexUtil.translateHexCodes(fileManager.getMessages().getString("clickToEdit")))
                    .setLocalizedName("static_item")
                    .addToInventory(inventory);
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
