package mc.obliviate.tabpacketblocker;

import com.comphenix.protocol.ProtocolLibrary;
import mc.obliviate.tabpacketblocker.listeners.PacketListener;
import mc.obliviate.tabpacketblocker.utils.Utils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class TabPacketBlocker extends JavaPlugin {

    public static YamlConfiguration config;
    public static File logFile;

    public void onEnable() {
        loadConfig();
        logFile = new File(getDataFolder().getPath() + File.separator + "log.yml");
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketListener(this));
    }


    public void loadConfig() {
        File file = new File(getDataFolder().getPath() + File.separator + "config.yml");
        config = YamlConfiguration.loadConfiguration(file);
        if (config.getKeys(false).size() == 0) {

            config.set("block-commands", Arrays.asList("/to", "/sa"));
            config.set("kick-message", ("&cRahmetli Ales de öyle söylerdi."));
            config.set("log.enabled", (true));
            config.set("log.all-tab-completes", (false));
            config.set("log.format", ("[{date}] {player}: {tab} ({is-blocked})"));
            config.set("staff-notify.enabled", (true));
            config.set("staff-notify.format", ("&4[TPF]&c {player} executed: &7{tab}"));
            config.set("staff-notify.non-op-list", Arrays.asList("Vasily","Mr_Obliviate","Carloss","ByKusH"));

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
