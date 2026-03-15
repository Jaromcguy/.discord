package de.example.myplugin.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Marker-Klasse um das Discord-Inventar eindeutig zu identifizieren.
 */
public class DiscordInventoryHolder implements InventoryHolder {

    @Override
    public @NotNull Inventory getInventory() {
        // Wird nicht verwendet – dient nur als Marker
        throw new UnsupportedOperationException();
    }
}
