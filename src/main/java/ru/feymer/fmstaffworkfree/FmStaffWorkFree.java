package ru.feymer.fmstaffworkfree;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.feymer.fmstaffworkfree.commands.FmStaffWorkCommand;
import ru.feymer.fmstaffworkfree.commands.StaffWorkCommand;
import ru.feymer.fmstaffworkfree.event.Event;
import ru.feymer.fmstaffworkfree.listeners.Listeners;
import ru.feymer.fmstaffworkfree.utils.Config;
import ru.feymer.fmstaffworkfree.utils.DataConfig;
import ru.feymer.fmstaffworkfree.utils.Hex;
import ru.feymer.fmstaffworkfree.utils.Updater;

import java.io.IOException;
import java.net.URISyntaxException;

public final class FmStaffWorkFree extends JavaPlugin {

    public static FmStaffWorkFree instance;

    @Override
    public void onEnable() {

        instance = this;
        Bukkit.getConsoleSender().sendMessage(Hex.color(""));
        Bukkit.getConsoleSender().sendMessage(Hex.color("&6» &fПлагин &6" + getPlugin(FmStaffWorkFree.class).getName() + " &fвключился&f!"));
        Bukkit.getConsoleSender().sendMessage(Hex.color("&6» &fВерсия: &6v" + getPlugin(FmStaffWorkFree.class).getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(Hex.color(""));

        this.getCommand("staffwork").setExecutor(new StaffWorkCommand());
        this.getCommand("fmstaffwork").setExecutor(new FmStaffWorkCommand());
        Config.loadYamlFile(this);
        DataConfig.loadYamlFile(this);
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        Event.registerEvents();

        Updater updater = new Updater();
        try {

            updater.checkUpdate();

        } catch (URISyntaxException | IOException | InterruptedException e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public void onDisable() {

        Bukkit.getConsoleSender().sendMessage(Hex.color(""));
        Bukkit.getConsoleSender().sendMessage(Hex.color("&6» &fПлагин &6" + getPlugin(FmStaffWorkFree.class).getName() + " &fвыключился&f!"));
        Bukkit.getConsoleSender().sendMessage(Hex.color("&6» &fВерсия: &6v" + getPlugin(FmStaffWorkFree.class).getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(Hex.color(""));

    }

    public static FmStaffWorkFree getInstance() {
        return instance;
    }
}
