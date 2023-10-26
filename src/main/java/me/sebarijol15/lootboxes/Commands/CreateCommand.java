package me.sebarijol15.lootboxes.Commands;

import me.sebarijol15.lootboxes.LootBoxes;
import me.sebarijol15.lootboxes.Util.FileManager;
import me.sebarijol15.lootboxes.Util.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CreateCommand extends SubCommand {
    private FileManager fileManager = LootBoxes.getFileManager();

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public void run(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage("Especifica el identificador de la lootbox");
            return;
        }

        fileManager.getLootboxConfig().set(args[1] + ".name", "Lootbox");
        fileManager.getLootboxConfig().createSection(args[1] + ".contents");
        fileManager.saveConfig();
    }
}
