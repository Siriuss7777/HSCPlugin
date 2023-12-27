import org.bukkit.plugin.java.JavaPlugin;

public class ChunkPopulator extends JavaPlugin {
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventListeners(), this);
    }
}