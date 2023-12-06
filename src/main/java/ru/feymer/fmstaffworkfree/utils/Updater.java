package ru.feymer.fmstaffworkfree.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Updater {

    public void checkUpdate() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.github.com/repos/AuthMe/AuthMeReloaded/releases/latest"))
                .GET()
                .build();

        String body = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
        JsonObject json = new JsonParser().parse(body).getAsJsonObject();

        String latestVersion = json.get("tag_name").getAsString();
        String downloadLink = json
                .getAsJsonArray("assets")
                .get(0).getAsJsonObject()
                .get("browser_download_url").getAsString();

        if (!Bukkit.getVersion().equals(latestVersion)) {

            Bukkit.getConsoleSender().sendMessage(Hex.color(""));
            Bukkit.getConsoleSender().sendMessage(Hex.color("&6» &fВы используете старую версию плагина!"));
            Bukkit.getConsoleSender().sendMessage(Hex.color("&6» &fСкачайте новую по ссылке: &6" + downloadLink));
            Bukkit.getConsoleSender().sendMessage(Hex.color(""));

        }
    }
}
