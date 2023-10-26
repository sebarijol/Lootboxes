package me.sebarijol15.lootboxes.Commands;

import me.sebarijol15.lootboxes.Inventories.ListInventory;
import me.sebarijol15.lootboxes.Util.SubCommand;
import org.bukkit.entity.Player;

public class GUICommand extends SubCommand {
    @Override
    public String getName() {
        return "gui";
    }

    @Override
    public void run(Player player, String[] args) {
        ListInventory listInventory = new ListInventory(player);
        player.openInventory(listInventory.getInventory());
    }
}
