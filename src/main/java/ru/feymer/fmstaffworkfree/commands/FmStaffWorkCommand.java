package ru.feymer.fmstaffworkfree.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.feymer.fmstaffworkfree.FmStaffWorkFree;
import ru.feymer.fmstaffworkfree.utils.Config;
import ru.feymer.fmstaffworkfree.utils.DataConfig;
import ru.feymer.fmstaffworkfree.utils.Utils;

public class FmStaffWorkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("reload")) {

                if (sender.hasPermission("fmstaffwork.reload")) {

                    Config.reloadConfig(FmStaffWorkFree.getInstance());
                    DataConfig.reloadData(FmStaffWorkFree.getInstance());
                    Utils.sendMessage(sender, Utils.getString("messages.reload"));

                } else {

                    Utils.sendMessage(sender, Utils.getString("messages.no-permission"));

                }

                return true;
            } else {

                for (String help : Utils.getStringList("messages.help")) {
                    Utils.sendMessage(sender, help);
                }

            }

            return true;
        } else {

            for (String help : Utils.getStringList("messages.help")) {
                Utils.sendMessage(sender, help);
            }

        }
        return false;
    }
}
