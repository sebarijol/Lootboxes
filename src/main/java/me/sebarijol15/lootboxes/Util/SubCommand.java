package me.sebarijol15.lootboxes.Util;

import org.bukkit.entity.Player;

public abstract class SubCommand {
    public abstract String getName();
    public abstract void run(Player player, String[] args);
}
