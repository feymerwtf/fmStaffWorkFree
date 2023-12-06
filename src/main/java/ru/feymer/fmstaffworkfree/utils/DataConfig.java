package ru.feymer.fmstaffworkfree.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataConfig {

    public String ParentNode;

    public static FileConfiguration data;
    public static File file;

    public DataConfig(String parentNode) {

        ParentNode = parentNode;

        if (!parentNode.isEmpty()) {

            ParentNode += ".";

        }

    }

    public DataConfig(String dateTime, Player player) {

        ParentNode = dateTime.substring(0, 10) + "." + player.getName() + ".";

    }

    public static void loadYamlFile(Plugin plugin) {

        file = new File(plugin.getDataFolder(), "data.yml");

        if (!file.exists()) {

            try {

                file.createNewFile();

            } catch (IOException e) {

                throw new RuntimeException(e);

            }

        }

        data = YamlConfiguration.loadConfiguration(file);

    }

    public static void saveData() {

        try {
            getData().save(file);
        }

        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static FileConfiguration getData() {
        return data;
    }

    public static void reloadData(Plugin plugin) {

        if (!file.exists()) {

            plugin.getDataFolder().mkdirs();
            plugin.saveResource("data.yml", true);

        }
        try {

            data.load(file);

        } catch (IOException | InvalidConfigurationException e) {

            Bukkit.getConsoleSender().sendMessage("Не удалось перезагрузить дату!");

        }
    }

    public void set(String path, Object value) {
        getData().set(ParentNode + path, value);
    }

    public boolean contains(String path) {
        return getData().contains(ParentNode + path);
    }

    public String getString(String path) {
        return (getData().getString(ParentNode + path));
    }

    public LocalDateTime getDateTime(String path) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(getString(path), formatter);
    }

    public List<String> getStringList(String path) {
        return (getData().getStringList(ParentNode + path));
    }

    public List<String> getKeys(String path) {
        return new ArrayList<>((getData().getConfigurationSection(ParentNode + path).getKeys(false)));
    }

    public int getInt(String path) {
        return getData().getInt(ParentNode + path);
    }

    public boolean getBoolean(String path) {
        return getData().getBoolean(ParentNode + path);
    }
}
