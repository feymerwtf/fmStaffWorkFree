package ru.feymer.fmstaffworkfree.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WorkUtils {

    public static boolean offWork(Player player, boolean fromCommand) {

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

                if (fromCommand) {

                    Utils.sendMessage(player, Utils.getString("messages.not-working"));

                }

                return true;

            }

            if (!dataConfigYesterDay.getBoolean("work")) {

                if (fromCommand) {

                    Utils.sendMessage(player, Utils.getString("messages.not-working"));

                }

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

            if (fromCommand) {

                Utils.sendMessage(player, Utils.getString("messages.not-working"));

            }

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

        return true;
    }
}
