package de.example.myplugin.listeners;

import de.example.myplugin.MyPlugin;
import de.example.myplugin.inventory.DiscordInventoryHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DiscordInventoryListener implements Listener {

    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();

    private final MyPlugin plugin;

    public DiscordInventoryListener(MyPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        // Nur unser Discord-Inventar bearbeiten
        if (!(event.getInventory().getHolder() instanceof DiscordInventoryHolder)) return;

        // Alle Klicks im Inventar abbrechen (verhindert Item-Herausnehmen)
        event.setCancelled(true);

        // Nur Klicks auf das Knowledge Book reagieren
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() != Material.KNOWLEDGE_BOOK) return;

        String link = plugin.getConfig().getString("discord.link", "https://discord.gg/example");
        String messageRaw = plugin.getConfig().getString("message.click-text",
                "&7Klicke &b&lhier &7um dem Discord beizutreten!");

        // Klickbare Chat-Nachricht – Hover zeigt den Link, Klick öffnet die URL
        Component message = LEGACY.deserialize(messageRaw)
                .clickEvent(ClickEvent.openUrl(link))
                .hoverEvent(HoverEvent.showText(
                        Component.text(link, NamedTextColor.AQUA)
                ));

        // Inventar schließen, dann Nachricht senden
        player.closeInventory();
        player.sendMessage(message);

        // Ping-Sound abspielen
        if (plugin.getConfig().getBoolean("sound.enabled", true)) {
            String soundName = plugin.getConfig().getString("sound.type", "BLOCK_NOTE_BLOCK_PLING");
            float volume = (float) plugin.getConfig().getDouble("sound.volume", 1.0);
            float pitch = (float) plugin.getConfig().getDouble("sound.pitch", 2.0);

            Sound sound;
            try {
                sound = Sound.valueOf(soundName.toUpperCase());
            } catch (IllegalArgumentException e) {
                sound = Sound.BLOCK_NOTE_BLOCK_PLING;
            }
            player.playSound(player.getLocation(), sound, volume, pitch);
        }
    }
}
