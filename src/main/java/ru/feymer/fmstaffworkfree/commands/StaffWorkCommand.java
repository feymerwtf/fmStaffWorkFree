package ru.feymer.fmstaffworkfree.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.feymer.fmstaffworkfree.utils.DataConfig;
import ru.feymer.fmstaffworkfree.utils.Hex;
import ru.feymer.fmstaffworkfree.utils.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StaffWorkCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {

            Bukkit.getConsoleSender().sendMessage(Hex.color("&cКоманда доступна только игрокам!"));
            return true;

        }

        Player player = (Player) sender;

        if (args.length > 0) {

            if (args[0].equalsIgnoreCase("on")) {

                if (!player.hasPermission("fmstaffwork.on")) {

                    Utils.sendMessage(player, Utils.getString("messages.no-permission"));
                    return true;

                } else {

                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String dataTime = now.format(formatter);
                    DataConfig dataConfig = new DataConfig(dataTime, player);

                    if (dataConfig.contains("work") && dataConfig.getBoolean("work")) {

                        Utils.sendMessage(player, Utils.getString("messages.already-working"));
                        return true;

                    }


                    if (!dataConfig.contains("on-work")) {

                        dataConfig.set("time-work", 0);

                    }

                    dataConfig.set("on-work", dataTime);
                    dataConfig.set("work", true);
                    DataConfig.saveData();

                    for (Player players : Bukkit.getOnlinePlayers()) {

                        Utils.sendMessage(players, Utils.getString("messages.on-all").replace("%player%", player.getName()));

                    }
                    Utils.sendMessage(player, Utils.getString("messages.on-work"));

                    for (String commands : Utils.getStringList("settings.commands-on")) {

                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commands.replace("%player%", player.getName()));

                    }
                }

                return true;

            } else if (args[0].equalsIgnoreCase("off")) {

                if (!player.hasPermission("fmstaffwork.off")) {

                    Utils.sendMessage(player, Utils.getString("messages.no-permission"));
                    return true;

                } else {

                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String dataTime = now.format(formatter);
                    DataConfig dataConfig = new DataConfig(dataTime, player);

                    if (!dataConfig.contains("on-work")) {

                        String startDayString = dataTime.substring(0, 10) + " 00:00:00";
                        LocalDateTime startDay = LocalDateTime.parse(startDayString, formatter);
                        LocalDateTime yesterDay = startDay.minusHours(1);
                        DataConfig dataConfigYesterDay = new DataConfig(yesterDay.format(formatter), player);

                        if (!dataConfigYesterDay.contains("on-work")) {

                            Utils.sendMessage(player, Utils.getString("messages.not-working"));
                            return true;

                        }

                        if (!dataConfigYesterDay.getBoolean("work")) {

                            Utils.sendMessage(player, Utils.getString("messages.not-working"));
                            return true;

                        }

                        LocalDateTime work_startYesterDay = dataConfigYesterDay.getDateTime("on-work");
                        Duration durationYesterDay = Duration.between(work_startYesterDay, startDay);
                        dataConfigYesterDay.set("time-work", dataConfigYesterDay.getInt("time-work") + (int) durationYesterDay.getSeconds());
                        dataConfigYesterDay.set("work", false);
                        dataConfig.set("time-work", 0);
                        dataConfig.set("on-work", startDayString);

                    }

                    if (!dataConfig.getBoolean("work")) {

                        Utils.sendMessage(player, Utils.getString("messages.not-working"));
                        return true;

                    }

                    LocalDateTime work_start = dataConfig.getDateTime("on-work");
                    Duration duration = Duration.between(work_start, now);
                    dataConfig.set("time-work", dataConfig.getInt("time-work") + (int) duration.getSeconds());
                    dataConfig.set("work", false);
                    DataConfig.saveData();

                    for (Player players : Bukkit.getOnlinePlayers()) {

                        Utils.sendMessage(players, Utils.getString("messages.off-all").replace("%player%", player.getName()));

                    }
                    Utils.sendMessage(player, Utils.getString("messages.off-work"));

                    for (String commands : Utils.getStringList("settings.commands-off")) {

                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commands.replace("%player%", player.getName()));

                    }

                }

                return true;

            } else if (args[0].equalsIgnoreCase("stats")) {

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String dataTime = now.format(formatter).substring(0, 10);
                String playerName = "";

                if (args.length == 1) {

                    playerName = player.getName();

                } else if (args.length == 2) {

                    playerName = args[1];

                }
                if (playerName.equalsIgnoreCase("all")) {

                    DataConfig dataConfig = new DataConfig("");
                    List<String> keys = dataConfig.getKeys(dataTime);

                    for (String username : keys) {

                        int bans = 0;
                        if (dataConfig.contains(dataTime + "." + username + ".bans")) {

                            bans = dataConfig.getInt(dataTime + "." + username + ".bans");

                        }

                        int mutes = 0;
                        if (dataConfig.contains(dataTime + "." + username + ".mutes")) {

                            mutes = dataConfig.getInt(dataTime + "." + username + ".mutes");

                        }

                        int kicks = 0;
                        if (dataConfig.contains(dataTime + "." + username + ".kicks")) {

                            kicks = dataConfig.getInt(dataTime + "." + username + ".kicks");

                        }

                        int timeWork;
                        String timeWorkString = "";
                        if (dataConfig.contains(dataTime + "." + username + ".time-work")) {

                            timeWork = dataConfig.getInt(dataTime + "." + username + ".time-work");
                            int hours = timeWork / 3600;
                            int minutes = (timeWork - hours * 3600) / 60;
                            int seconds = timeWork % 60;
                            timeWorkString = hours + " час " + minutes + " мин " + seconds + " сек";

                        }

                        for (String stats : Utils.getStringList("messages.stats")) {

                            Utils.sendMessage(player, stats.replace("%bans%", Integer.toString(bans)).replace("%mutes%", Integer.toString(mutes)).replace("%kicks%", Integer.toString(kicks)).replace("%player%", username).replace("%time%", timeWorkString));

                        }

                    }
                } else {
                    DataConfig dataConfig = new DataConfig(dataTime + "." + playerName + ".");

                    boolean hasWork = false;

                    int bans = 0;
                    if (dataConfig.contains("bans")) {

                        bans = dataConfig.getInt("bans");
                        hasWork = true;

                    }

                    int mutes = 0;
                    if (dataConfig.contains("mutes")) {

                        mutes = dataConfig.getInt("mutes");
                        hasWork = true;

                    }

                    int kicks = 0;
                    if (dataConfig.contains("kicks")) {

                        kicks = dataConfig.getInt("kicks");
                        hasWork = true;

                    }

                    int timeWork;
                    String timeWorkString = "";
                    if (dataConfig.contains("time-work")) {

                        timeWork = dataConfig.getInt("time-work");
                        int hours = timeWork / 3600;
                        int minutes = (timeWork - hours * 3600) / 60;
                        int seconds = timeWork % 60;
                        timeWorkString = hours + " час " + minutes + " мин " + seconds + " сек";
                        hasWork = true;

                    }

                    if (hasWork) {

                        for (String stats : Utils.getStringList("messages.stats")) {

                            Utils.sendMessage(player, stats.replace("%bans%", Integer.toString(bans)).replace("%mutes%", Integer.toString(mutes)).replace("%kicks%", Integer.toString(kicks)).replace("%player%", playerName).replace("%time%", timeWorkString));

                        }

                    } else {

                        Utils.sendMessage(player, Utils.getString("messages.wasnot-working"));

                    }

                }
            } else {

                for (String help : Utils.getStringList("messages.help")) {
                    Utils.sendMessage(player, help);
                }
                return true;

            }

        } else {

            for (String help : Utils.getStringList("messages.help")) {
                Utils.sendMessage(player, help);
            }

        }

        return false;
    }
}
