# CantDrop

CantDrop 是一个 Minecraft 服务端插件，用于禁止玩家在指定世界中丢弃物品。管理员可以通过配置文件指定世界、设置提示消息，并按需允许拥有绕过权限的玩家正常丢弃物品。

## 功能

- 按世界名称设置禁止丢弃物品的范围。
- 可配置是否向被拦截的玩家发送提示，以及提示内容。
- 可启用或关闭 `cantdrop.bypass` 绕过权限。
- 支持通过命令重载配置，无需重启服务器。

## 实现原理

插件启用时读取 `config.yml` 中的默认配置，并注册 `PlayerDropEvent` 监听器。玩家尝试丢弃物品时，监听器会依次检查：

1. 玩家当前所在世界是否包含在 `worlds` 列表中；不在列表中则允许丢弃。
2. 如果 `bypass-enabled` 开启，且玩家拥有 `cantdrop.bypass` 权限，则允许丢弃。
3. 其他情况下取消 Bukkit 的 `PlayerDropItemEvent`，阻止物品掉落；若 `send-message` 开启，则向玩家发送配置的提示消息。

配置重载命令由 `ReloadCommand` 处理，并使用 `reloadConfig()` 重新读取配置文件。

## 安装与配置

1. 从构建输出中取得 `CantDrop-1.0.0-shaded.jar`。
2. 将 JAR 放入服务端的 `plugins` 目录，然后启动或重启服务器。
3. 编辑 `plugins/CantDrop/config.yml`，在 `worlds` 中填写需要禁止丢弃物品的世界名称。
4. 执行 `/cantdrop reload` 应用配置更改。

默认配置示例：

```yaml
# 填写需要禁止丢弃物品的世界名称
worlds:
  - world
  - world_nether
  - world_the_end

# 是否启用 cantdrop.bypass 权限
bypass-enabled: true

# 是否在丢弃被拦截时发送提示
send-message: true
message: "§c这个世界无法丢弃物品!"
```

请将列表中的世界名称替换为服务器实际使用的名称，并移除不需要限制的世界。`message` 支持 Minecraft `§` 颜色代码。

## 命令与权限

| 命令 / 权限 | 说明 | 默认权限 |
| --- | --- | --- |
| `/cantdrop reload` | 重载插件配置 | 仅 OP（`cantdrop.reload`） |
| `cantdrop.reload` | 允许使用配置重载命令 | OP |
| `cantdrop.bypass` | 在受限制世界中绕过禁止丢弃限制 | OP |

即使玩家拥有 `cantdrop.bypass`，当 `bypass-enabled` 设置为 `false` 时，该权限也不会生效。

## 构建

构建环境需要 **JDK 21** 和 **Maven**。在项目根目录运行：

```bash
mvn clean package
```

Maven 会编译源码并打包插件。可部署的 Shade 打包产物位于：

```text
target/CantDrop-1.0.0-shaded.jar
```

项目使用 Spigot API `1.21.1-R0.1-SNAPSHOT` 编译，API 依赖以 `provided` 方式提供，不会打入插件包。
