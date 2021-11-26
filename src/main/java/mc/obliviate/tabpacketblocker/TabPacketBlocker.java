package mc.obliviate.tabpacketblocker;

import com.comphenix.protocol.ProtocolLibrary;
import mc.obliviate.tabpacketblocker.listeners.PacketListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class TabPacketBlocker extends JavaPlugin {

	public static YamlConfiguration config;
	public static File logFile;

	public void onEnable() {

		if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
			Bukkit.getLogger().severe("ProtocolLib plugin could not found. TabPacketBlocker disabling...");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		loadConfig();
		logFile = new File(getDataFolder().getPath() + File.separator + "log" + new SimpleDateFormat("MMM-yyyy").format(new Date()) +".log");
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketListener(this));
		Bukkit.getLogger().severe("TabPacketBlocker enabled successfully.");
		Bukkit.getLogger().severe("Coded by Mr_Obliviate.");
	}


	public void loadConfig() {
		File file = new File(getDataFolder().getPath() + File.separator + "config.yml");
		config = YamlConfiguration.loadConfiguration(file);
		if (config.getKeys(false).size() == 0) {

			config.set("block-commands", Arrays.asList("/to", "/for"));
			config.set("kick-message", ("|-\n" +
					"  &cCrash korumamız bağlantınızı kesti.\n" +
					"  &r\n" +
					"  &7Eğer bunun bir &o'false-positive'&7(yanlış tespit)\n" +
					"  &7olduğunu düşünüyorsanız lütfen Discord üzerinden\n" +
					"  &7destek talebi oluşturup bize bildirin.\n" +
					"  &7\n" +
					"  &eBu işlem &6Blok Dünyası&e'na tekrar girmenizi engellemez.\n" +
					"  &7\n" +
					"  &a&nwww.blokdunyasi.net &8- &dDiscord: &fYWAjK6p"));
			config.set("log.enabled", (true));
			config.set("log.all-tab-completes", (true));
			config.set("log.format", ("[{date}] {player}: {tab} ({is-blocked})"));
			config.set("staff-notify.enabled", (true));
			config.set("staff-notify.format", ("&4[TPF]&c packet blocked that's {player} sent: &7{tab}"));
			config.set("staff-notify.non-op-list", Arrays.asList("Mr_Obliviate", "ByKusH"));

			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}


}
