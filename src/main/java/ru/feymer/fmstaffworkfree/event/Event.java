package ru.feymer.fmstaffworkfree.event;

import litebans.api.Entry;
import litebans.api.Events;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.feymer.fmstaffworkfree.utils.DataConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event {

    public static void registerEvents() {

        Events.get().register(new Events.Listener() {

            @Override
            public void entryAdded(Entry entry) {

                if (entry.getType().equalsIgnoreCase("ban")) {

                    String executor = entry.getExecutorName();
                    String removed = entry.getRemovedByName();

                    if (executor != null && executor.equalsIgnoreCase(removed)) {
                        return;
                    }

                    Player player = null;

                    for (Player p : Bukkit.getOnlinePlayers()) {

                        if (p.getName().equalsIgnoreCase(executor)) {

                            player = p;
                            break;

                        }

                    }

                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String dataTime = now.format(formatter);
                    DataConfig dataConfig = new DataConfig(dataTime, player);

                    if (!dataConfig.contains("bans")) {

                        dataConfig.set("bans", 0);

                    }

                    dataConfig.set("bans", dataConfig.getInt("bans") + 1);
                    DataConfig.saveData();

                    return;

                }

                if (entry.getType().equalsIgnoreCase("mute")) {

                    String executor = entry.getExecutorName();
                    String removed = entry.getRemovedByName();

                    if (executor != null && executor.equalsIgnoreCase(removed)) {
                        return;
                    }

                    Player player = null;

                    for (Player p : Bukkit.getOnlinePlayers()) {

                        if (p.getName().equalsIgnoreCase(executor)) {

                            player = p;
                            break;

                        }

                    }

                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String dataTime = now.format(formatter);
                    DataConfig dataConfig = new DataConfig(dataTime, player);

                    if (!dataConfig.contains("mutes")) {

                        dataConfig.set("mutes", 0);

                    }

                    dataConfig.set("mutes", dataConfig.getInt("mutes") + 1);
                    DataConfig.saveData();

                    return;

                }

                if (entry.getType().equalsIgnoreCase("kick")) {

                    String executor = entry.getExecutorName();
                    String removed = entry.getRemovedByName();

                    if (executor != null && executor.equalsIgnoreCase(removed)) {
                        return;
                    }

                    Player player = null;

                    for (Player p : Bukkit.getOnlinePlayers()) {

                        if (p.getName().equalsIgnoreCase(executor)) {

                            player = p;
                            break;

                        }

                    }

                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String dataTime = now.format(formatter);
                    DataConfig dataConfig = new DataConfig(dataTime, player);

                    if (!dataConfig.contains("kicks")) {

                        dataConfig.set("kicks", 0);

                    }

                    dataConfig.set("kicks", dataConfig.getInt("kicks") + 1);
                    DataConfig.saveData();

                }
            }
        });
    }
}
