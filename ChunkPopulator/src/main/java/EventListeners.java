

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class EventListeners implements Listener {
    @EventHandler
    public void onInit(WorldInitEvent event) {
        event.getWorld().getPopulators().add(new ChunkPopulatorListenerBigVein(Material.DIAMOND_ORE, 4500));
        event.getWorld().getPopulators().add(new ChunkPopulatorListenerBigVein(Material.IRON_ORE, 4500));
        event.getWorld().getPopulators().add(new ChunkPopulatorListenerBigVein(Material.GOLD_ORE, 3000));
        event.getWorld().getPopulators().add(new ChunkPopulatorListenerSmallVein(Material.LAPIS_ORE, 3000));
        event.getWorld().getPopulators().add(new ChunkPopulatorListenerSmallVein(Material.OBSIDIAN, 4000));
    }
}
