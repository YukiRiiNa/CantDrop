package ltd.teaparty.rina.cantDrop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("cantdrop.reload")) {
            sender.sendMessage("§c你没有权限使用此命令");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            CantDrop.getInstance().reloadConfig();
            sender.sendMessage("§a配置已成功重载！");
            return true;
        }

        sender.sendMessage("§e用法: /cantdrop reload");
        return true;
    }
}
