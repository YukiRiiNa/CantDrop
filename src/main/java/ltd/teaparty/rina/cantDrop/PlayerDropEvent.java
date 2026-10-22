package ltd.teaparty.rina.cantDrop;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.List;

public class PlayerDropEvent implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        String currentWorld = player.getWorld().getName();
        CantDrop plugin = CantDrop.getInstance();

        // 1. 读取配置：禁止丢弃的世界列表
        List<String> disabledWorlds = plugin.getConfig().getStringList("worlds");
        // 2. 如果当前世界不在禁止列表，直接放行，不做后续判断
        if (!disabledWorlds.contains(currentWorld)) {
            return;
        }

        // 3. 核心新增：bypass权限判断
        boolean bypassEnabled = plugin.getConfig().getBoolean("bypass-enabled", true);
        if (bypassEnabled && player.hasPermission("cantdrop.bypass")) {
            // 拥有bypass权限，直接放行，不拦截丢弃
            return;
        }

        // 4. 无权限+在禁止世界，拦截丢弃
        event.setCancelled(true);
        // 发送提示消息（按配置开关）
        if (plugin.getConfig().getBoolean("send-message", true)) {
            player.sendMessage(plugin.getConfig().getString("message", "§c该世界禁止丢弃物品！"));
        }
    }

}
