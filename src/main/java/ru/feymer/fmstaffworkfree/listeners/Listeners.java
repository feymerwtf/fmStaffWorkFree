package ru.feymer.fmstaffworkfree.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import ru.feymer.fmstaffworkfree.utils.DataConfig;
import ru.feymer.fmstaffworkfree.utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Listeners implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        if (e.getDamager() instanceof Player) {

            if (Utils.getBoolean("settings.disable-entity-damage")) {

                Player damager = (Player) e.getDamager();
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String dataTime = now.format(formatter);
                DataConfig dataConfig = new DataConfig(dataTime, damager);

                if (dataConfig.contains("work")) {

                    if (dataConfig.getBoolean("work")) {

                        e.setCancelled(true);
                        Utils.sendMessage(damager, Utils.getString("messages.entity-damage-disabled"));

                    }

                } else {

                    String startDayString = dataTime.substring(0, 10) + " 00:00:00";
                    LocalDateTime startDay = LocalDateTime.parse(startDayString, formatter);
                    LocalDateTime yesterDay = startDay.minusHours(1);
                    DataConfig dataConfigYesterDay = new DataConfig(yesterDay.format(formatter), damager);

                    if (dataConfigYesterDay.contains("work")) {

                        if (dataConfigYesterDay.getBoolean("work")) {

                            e.setCancelled(true);
                            Utils.sendMessage(damager, Utils.getString("messages.entity-damage-disabled"));

                        }
                    }

                }
            }
        }

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        if (Utils.getBoolean("settings.disable-block-place")) {

            Player player = e.getPlayer();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String dataTime = now.format(formatter);
            DataConfig dataConfig = new DataConfig(dataTime, player);

            if (dataConfig.contains("work")) {

                if (dataConfig.getBoolean("work")) {

                    e.setCancelled(true);
                    Utils.sendMessage(player, Utils.getString("messages.block-place-disabled"));

                }

            } else {

                String startDayString = dataTime.substring(0, 10) + " 00:00:00";
                LocalDateTime startDay = LocalDateTime.parse(startDayString, formatter);
                LocalDateTime yesterDay = startDay.minusHours(1);
                DataConfig dataConfigYesterDay = new DataConfig(yesterDay.format(formatter), player);

                if (dataConfigYesterDay.contains("work")) {

                    if (dataConfigYesterDay.getBoolean("work")) {

                        e.setCancelled(true);
                        Utils.sendMessage(player, Utils.getString("messages.block-place-disabled"));

                    }
                }

            }
        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        if (Utils.getBoolean("settings.disable-block-break")) {

            Player player = e.getPlayer();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String dataTime = now.format(formatter);
            DataConfig dataConfig = new DataConfig(dataTime, player);

            if (dataConfig.contains("work")) {

                if (dataConfig.getBoolean("work")) {

                    e.setCancelled(true);
                    Utils.sendMessage(player, Utils.getString("messages.block-break-disabled"));

                }

            } else {

                String startDayString = dataTime.substring(0, 10) + " 00:00:00";
                LocalDateTime startDay = LocalDateTime.parse(startDayString, formatter);
                LocalDateTime yesterDay = startDay.minusHours(1);
                DataConfig dataConfigYesterDay = new DataConfig(yesterDay.format(formatter), player);

                if (dataConfigYesterDay.contains("work")) {

                    if (dataConfigYesterDay.getBoolean("work")) {

                        e.setCancelled(true);
                        Utils.sendMessage(player, Utils.getString("messages.block-break-disabled"));

                    }
                }

            }
        }

    }
}
