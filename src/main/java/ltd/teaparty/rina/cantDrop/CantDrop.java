package ltd.teaparty.rina.cantDrop;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CantDrop extends JavaPlugin {
    public static CantDrop instance;
    @Override
    public void onEnable() {
        instance = this;
        // 保存默认配置文件(如果不存在则生成)
        saveDefaultConfig();

        getLogger().info("§a禁止丢弃物品插件已加载!");
        System.out.println("loading");
        getLogger().info("====================================");
        Bukkit.getPluginManager().registerEvents(new PlayerDropEvent(), this);
        getLogger().info("关闭世界掉落，启动！");
        getLogger().info("====================================");
        getLogger().info("加载完成");
        // Plugin startup logic
        // 注册命令
        this.getCommand("cantdrop").setExecutor(new ReloadCommand());

    }
    public static CantDrop getInstance() {
        return instance;
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
