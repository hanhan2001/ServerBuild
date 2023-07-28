package me.xiaoying.sb.service;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Teleport
 */
public class TeleportService {
    private static final Map<String, List<Player>> teleportRequest = new HashMap<>();

    public static List<Player> getTeleportRequest(String player) {
        return teleportRequest.get(player);
    }

    public static List<Player> getTeleportRequest(Player player) {
        return teleportRequest.get(player.getName());
    }

    public static void removeTeleportRequest(String player) {
        teleportRequest.remove(player);
    }

    public static void removeTeleportRequest(Player player) {
        teleportRequest.remove(player.getName());
    }

    public static void removeTeleportRequest(String player, Player sendPlayer) {
        for (Player player1 : teleportRequest.get(player)) {
            if (!sendPlayer.getName().equalsIgnoreCase(player1.getName()))
                continue;

            teleportRequest.get(player).remove(player1);
        }
    }

    public static void removeTeleportRequest(Player player, Player sendPlayer) {
        for (Player player1 : teleportRequest.get(player.getName())) {
            if (!sendPlayer.getName().equalsIgnoreCase(player1.getName()))
                continue;

            teleportRequest.get(player.getName()).remove(player1);
        }
    }

    public static Map<String, List<Player>> getTeleportRequest() {
        return teleportRequest;
    }
}