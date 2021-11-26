package mc.obliviate.tabpacketblocker.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import mc.obliviate.tabpacketblocker.TabPacketBlocker;
import mc.obliviate.tabpacketblocker.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import static mc.obliviate.tabpacketblocker.TabPacketBlocker.*;

public class PacketListener extends PacketAdapter {

    public PacketListener(Plugin plugin) {
        super(plugin, ListenerPriority.HIGHEST, PacketType.Play.Client.TAB_COMPLETE);
    }



    @Override
    public void onPacketReceiving(PacketEvent event) {


        String tabString = event.getPacket().getSpecificModifier(String.class).read(0).toLowerCase();
        for (String tab : config.getStringList("block-commands")) {
            if (tabString.contains(tab)) {
                //when caught
                event.setCancelled(true);
                if (config.getBoolean("staff-notify.enabled")) {
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        event.getPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&', config.getString("kick-message")));
                        for (Player staff : Bukkit.getOnlinePlayers()) {
                            if (staff.isOp() || config.getStringList("staff-notify.non-op-list").contains(staff.getName())) {
                                staff.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("staff-notify.format"))
                                        .replace("{date}", Utils.getDate())
                                        .replace("{tab}", tabString)
                                        .replace("{player}", event.getPlayer().getName()));
                            }
                        }
                    });
                }
                break;
            }
        }

        if ((event.isCancelled() || config.getBoolean("log.all-tab-completes")) && TabPacketBlocker.config.getBoolean("log.enabled")) {
            String message = config.getString("log.format")
                    .replace("{date}", Utils.getDate())
                    .replace("{tab}", tabString)
                    .replace("{player}", event.getPlayer().getName())
                    .replace("{is-blocked}", event.isCancelled() + "");
            Utils.log(message, logFile);
        }
    }
}
