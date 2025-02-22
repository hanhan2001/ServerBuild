package me.xiaoying.serverbuild.packet.spigot;

import me.xiaoying.serverbuild.packet.Packet;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class V1_20_R1 implements Packet {
    private ArmorStand armorStand;

    @Override
    public void sendHologram(Player player, String text) {
//        if (this.armorStand == null)
//            this.armorStand = (ArmorStand) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
//        List<DataWatcher.c<?>> watchers = new ArrayList<>();
//        EntityArmorStand entityArmorStand = ((CraftArmorStand) this.armorStand).getHandle();
//        PacketPlayOutEntityMetadata packetPlayOutEntityMetadata = new PacketPlayOutEntityMetadata(this.armorStand.getEntityId(), watchers);
//        CraftPlayer craftPlayer = (CraftPlayer) player;
//        craftPlayer.getHandle().c.sendPacket(packetPlayOutEntityMetadata);
    }
}