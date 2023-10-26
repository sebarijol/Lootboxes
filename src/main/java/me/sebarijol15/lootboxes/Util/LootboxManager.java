package me.sebarijol15.lootboxes.Util;

import me.sebarijol15.lootboxes.LootBoxes;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Set;

public class LootboxManager {
    private HexUtil hexUtil = new HexUtil();
    private FileManager fileManager = LootBoxes.getFileManager();
    private Set<String > availableLootboxes = fileManager.getLootboxConfig().getKeys(false);
    public Set<String> getAvailableLootboxes() {
        return availableLootboxes;
    }

    public ItemStack getLootboxById(String lootboxId) {
        ConfigurationSection lootboxSection = fileManager.getLootboxConfig().getConfigurationSection(lootboxId);
        if (lootboxSection != null) {
            String lootboxName = hexUtil.translateHexCodes(lootboxSection.getString("name"));
            String textureURL = lootboxSection.getString("texture");
            ItemBuilder lootbox = new ItemBuilder(Material.PLAYER_HEAD)
                    .setSkullTexture(textureURL)
                    .setDisplayName(lootboxName)
                    .addLoreLine(hexUtil.translateHexCodes(fileManager.getMessages().getString("rightClickToOpen")))
                    .addLoreLine(hexUtil.translateHexCodes(fileManager.getMessages().getString("shiftRightClickToViewContents")))
                    .setLocalizedName(lootboxId);

            return lootbox.toItemStack();
        }
        return null;
    }
}
