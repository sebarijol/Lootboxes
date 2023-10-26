package me.sebarijol15.lootboxes.Commands;

import me.sebarijol15.lootboxes.LootBoxes;
import me.sebarijol15.lootboxes.Util.LootboxManager;
import me.sebarijol15.lootboxes.Util.FileManager;
import me.sebarijol15.lootboxes.Util.SubCommand;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GetCommand extends SubCommand {
    private static String lootboxId;

    private LootboxManager lootbox;

    private FileManager fileManager = LootBoxes.getFileManager();

    public GetCommand() {
        this.lootbox = new LootboxManager();

    }

    @Override
    public String getName() {
        return "get";
    }

    @Override
    public void run(Player player, String[] args) {
        lootboxId = args[1];
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
        player.getInventory().addItem(lootbox.getLootboxById(args[1]));
    }

    public static String getLootboxId() {
        return lootboxId;
    }
}
