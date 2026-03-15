package de.example.myplugin;

import de.example.myplugin.commands.DiscordCommand;
import de.example.myplugin.listeners.DiscordInventoryListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MyPlugin extends JavaPlugin {

    private static MyPlugin instance;

    private File discordConfigFile;
    private FileConfiguration discordConfig;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        getCommand("discord").setExecutor(new DiscordCommand(this));
        getServer().getPluginManager().registerEvents(new DiscordInventoryListener(this), this);

        getLogger().info(".discord Plugin wurde aktiviert!");
    }

    @Override
    public void onDisable() {
        getLogger().info(".discord Plugin wurde deaktiviert!");
    }

    // Lädt .discord.yml statt config.yml
    @Override
    public FileConfiguration getConfig() {
        if (discordConfig == null) {
            reloadConfig();
        }
        return discordConfig;
    }

    @Override
    public void reloadConfig() {
        if (discordConfigFile == null) {
            discordConfigFile = new File(getDataFolder(), ".discord.yml");
        }
        discordConfig = YamlConfiguration.loadConfiguration(discordConfigFile);
        InputStream defStream = getResource(".discord.yml");
        if (defStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(defStream, StandardCharsets.UTF_8));
            discordConfig.setDefaults(defConfig);
        }
    }

    @Override
    public void saveDefaultConfig() {
        if (discordConfigFile == null) {
            discordConfigFile = new File(getDataFolder(), ".discord.yml");
        }
        if (!discordConfigFile.exists()) {
            saveResource(".discord.yml", false);
        }
    }

    public static MyPlugin getInstance() {
        return instance;
    }
}
