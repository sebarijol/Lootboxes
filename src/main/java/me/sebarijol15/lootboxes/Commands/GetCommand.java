package me.sebarijol15.lootboxes.Commands;

import me.sebarijol15.lootboxes.Util.LootboxItem;
import me.sebarijol15.lootboxes.Util.FileManager;
import me.sebarijol15.lootboxes.Util.SubCommand;
import org.bukkit.entity.Player;

public class GetCommand extends SubCommand {
    private LootboxItem lootbox;
    private FileManager fileManager;

    public GetCommand(FileManager fileManager, LootboxItem lootbox) {
        this.fileManager = fileManager;
        this.lootbox = lootbox;
    }

    @Override
    public String getName() {
        return "get";
    }

    @Override
    public void run(Player player, String[] args) {
        String lootboxId = args[1];
        if (!(fileManager.getLootboxConfig().getKeys(false).contains(lootboxId))) {
            player.sendMessage("¡La lootbox especificada no existe!");
            return;
        }
        player.getInventory().addItem(lootbox.getLootboxById(lootboxId, fileManager));
    }
}
