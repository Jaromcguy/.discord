package de.example.myplugin.commands;

import de.example.myplugin.MyPlugin;
import de.example.myplugin.inventory.DiscordInventoryHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class DiscordCommand implements CommandExecutor {

    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();

    private final MyPlugin plugin;

    public DiscordCommand(MyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Nur Spieler können diesen Befehl verwenden!");
            return true;
        }

        // Inventar-Titel aus Config
        String titleRaw = plugin.getConfig().getString("inventory.title", "&9&lDiscord");
        Component title = LEGACY.deserialize(titleRaw);

        // 3-reihiges Inventar (27 Slots) mit eigenem Holder zur sicheren Erkennung
        Inventory inv = Bukkit.createInventory(new DiscordInventoryHolder(), 27, title);

        // Füller-Slots
        String fillerMatName = plugin.getConfig().getString("inventory.filler-material", "GRAY_STAINED_GLASS_PANE");
        Material fillerMaterial;
        try {
            fillerMaterial = Material.valueOf(fillerMatName.toUpperCase());
        } catch (IllegalArgumentException e) {
            fillerMaterial = Material.GRAY_STAINED_GLASS_PANE;
        }

        ItemStack filler = new ItemStack(fillerMaterial);
        ItemMeta fillerMeta = filler.getItemMeta();
        // Leerer Name, kein Italic
        fillerMeta.displayName(Component.text(" ").decoration(TextDecoration.ITALIC, false));
        filler.setItemMeta(fillerMeta);

        for (int i = 0; i < 27; i++) {
            inv.setItem(i, filler.clone());
        }

        // Knowledge Book in der Mitte (Slot 13 = Reihe 2, Spalte 5)
        String itemNameRaw = plugin.getConfig().getString("inventory.item-name", "&9&lDiscord Server");
        Component itemName = LEGACY.deserialize(itemNameRaw)
                .decoration(TextDecoration.ITALIC, false);

        ItemStack book = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta bookMeta = book.getItemMeta();
        bookMeta.displayName(itemName);
        book.setItemMeta(bookMeta);

        inv.setItem(13, book);

        player.openInventory(inv);
        return true;
    }
}
