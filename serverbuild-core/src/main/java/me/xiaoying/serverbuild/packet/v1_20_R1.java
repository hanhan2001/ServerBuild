package me.xiaoying.serverbuild.packet;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.packet.entities.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Optional;

public class v1_20_R1 implements Packet {
    private ArmorStand armorStand;

    @Override
    public void sendHologram(Player player, String text) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SBPlugin.getInstance(), () -> {
            if (this.armorStand == null || this.armorStand.isDead())
                this.armorStand = (ArmorStand) Bukkit.getWorld(player.getWorld().getName()).spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);

            try {
                SCraftPlayer craftPlayer = SBPlugin.getProxyProvider().constructorClass(SCraftPlayer.class, player);
                SEntityPlayer entityPlayer = SBPlugin.getProxyProvider().constructorClass(SEntityPlayer.class, craftPlayer.getHandleV1_20_R1());

                SPlayerConnection playerConnection = SBPlugin.getProxyProvider().constructorClass(SPlayerConnection.class, entityPlayer.getPlayConnectionV1_20_R1());
                SNetworkManager networkManager = SBPlugin.getProxyProvider().constructorClass(SNetworkManager.class, playerConnection.getNetworkManagerV1_20_R1());
                SPacketPlayOutEntityMetadata packetPlayOutEntityMetadata = SBPlugin.getProxyProvider().constructorClass(SPacketPlayOutEntityMetadata.class, null);
                SCraftArmorStand craftArmorStand = SBPlugin.getProxyProvider().constructorClass(SCraftArmorStand.class, this.armorStand);

                SEntity entity = SBPlugin.getProxyProvider().constructorClass(SEntity.class, craftArmorStand.getHandleV1_20_R1());
                SDataWatcher dataWatcher = SBPlugin.getProxyProvider().constructorClass(SDataWatcher.class, entity.getDataWatcherV1_20_R1());

                // 获取 名称 IChatBaseComponent
                SIChatBaseComponent iChatBaseComponent = SBPlugin.getProxyProvider().constructorClass(SIChatBaseComponent.class, null);
                SIChatBaseComponent chatComponent = SBPlugin.getProxyProvider().constructorClass(SIChatBaseComponent.class, iChatBaseComponent.string("{\"text\":\"asdasdais\"}"));

                Optional<Object> optionalIChatBaseComponent = Optional.of(chatComponent.getInstance());

                // 设置名称
                dataWatcher.setV1_20_R1(entity.get_CUSTOM_NAME_V1_20_R1(), optionalIChatBaseComponent);
                // 设置名称可见性
                dataWatcher.setV1_20_R1(entity.get_CUSTOM_NAME_VISIBLE_V1_20_R1(), true);

                networkManager.sendPacketV1_20_R1(packetPlayOutEntityMetadata.getPacketV1_20_R1(this.armorStand.getEntityId(), dataWatcher.getItemListV1_20_R1()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
    }
}