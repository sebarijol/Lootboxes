package me.sebarijol15.lootboxes.Util;

import me.sebarijol15.lootboxes.Commands.CreateCommand;
import me.sebarijol15.lootboxes.Commands.GUICommand;
import me.sebarijol15.lootboxes.Commands.GetCommand;
import me.sebarijol15.lootboxes.Commands.ReloadCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements TabExecutor {
    private ArrayList<SubCommand> subCommands = new ArrayList<>();
    private LootboxManager lootboxManager;
    public CommandManager(FileManager fileManager) {
        lootboxManager = new LootboxManager();
        subCommands.add(new GetCommand());
        subCommands.add(new CreateCommand());
        subCommands.add(new GUICommand());
        subCommands.add(new ReloadCommand(fileManager));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player = (Player) sender;
        if (args == null || args.length == 0) {
            player.sendMessage("Please specify a subcommand");
            return true;
        }
        for (int i = 0; i < getSubCommands().size(); i++) {
            if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                getSubCommands().get(i).run(player, args);
            }
        }
        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            String subCommandPrefix = args[0].toLowerCase();
            for (SubCommand subCommand : subCommands) {
                if (subCommand.getName().toLowerCase().startsWith(subCommandPrefix)) {
                    suggestions.add(subCommand.getName());
                }
            }
        }
        if (args[0].equalsIgnoreCase("get")) {
            suggestions.addAll(lootboxManager.getAvailableLootboxes());
        }

        return suggestions.isEmpty() ? null : suggestions;
    }

    private SubCommand getSubCommandByName(String name) {
        for (SubCommand subCommand : subCommands) {
            if (subCommand.getName().equalsIgnoreCase(name)) {
                return subCommand;
            }
        }
        return null;
    }
}
