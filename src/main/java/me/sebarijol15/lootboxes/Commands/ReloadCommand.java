package me.sebarijol15.lootboxes.Commands;

import me.sebarijol15.lootboxes.Util.FileManager;
import me.sebarijol15.lootboxes.Util.SubCommand;
import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {
    private final FileManager fileManager;

    public ReloadCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public void run(Player player, String[] args) {
        fileManager.reloadLootboxes();
    }
}
