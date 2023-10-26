package me.sebarijol15.lootboxes.Util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.codehaus.plexus.util.Base64;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {
    private HexUtil hexUtil = new HexUtil();
    private int amount;
    private ItemStack itemStack;
    private String displayName;
    private String localizedName;
    private List<String> lore;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.lore = new ArrayList<>();
    }

    public ItemBuilder setDisplayName(String displayName) {
        this.displayName = hexUtil.translateHexCodes(displayName);
        return this;
    }

    public ItemBuilder setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
        return this;
    }

    public ItemBuilder addLoreLine(String loreLine) {
        this.lore.add(hexUtil.translateHexCodes(loreLine));
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemStack toItemStack() {
        ItemMeta meta = itemStack.getItemMeta();
        if (displayName != null) {
            meta.setDisplayName(displayName);
        }
        if (localizedName != null) {
            meta.setLocalizedName(localizedName);
        }
        if (!lore.isEmpty()) {
            meta.setLore(lore);
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public void addToInventory(Inventory inventory, int slot) {
        ItemStack itemStack = toItemStack();
        if (inventory != null && slot >= 0 && slot < inventory.getSize()) {
            inventory.setItem(slot, itemStack);
        }
    }

    public void addToInventory(Inventory inventory) {
        ItemStack itemStack = toItemStack();
        inventory.setItem(inventory.firstEmpty(), itemStack);
    }

        public static void fillEmptySlots(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
                inventory.setItem(i, createEmptyItem());
            }
        }
    }

    private static ItemStack createEmptyItem() {
        return new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                .setDisplayName(" ")
                .setLocalizedName("static_item")
                .toItemStack();
    }

    public ItemBuilder setSkullTexture(String url) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD);

        if (url.isEmpty()) {
            return itemBuilder;
        }

        ItemMeta headMeta = head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;

        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        profileField.setAccessible(true);

        try {
            profileField.set(headMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        head.setItemMeta(headMeta);
        itemBuilder.itemStack = head;
        return itemBuilder;
    }
}
