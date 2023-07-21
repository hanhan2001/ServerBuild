package me.xiaoying.sb.listener.listeners;

import me.xiaoying.sb.constant.ClearEntityConstant;
import me.xiaoying.sb.entity.ClearEntityEntity;
import me.xiaoying.sb.event.ClearEntityEvent;
import me.xiaoying.sb.factory.VariableFactory;
import me.xiaoying.sb.service.ClearEntityService;
import me.xiaoying.sb.utils.ListUtil;
import me.xiaoying.sb.utils.ServerUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class ClearEntityListener implements Listener {
    @EventHandler
    public void onClearEntity(ClearEntityEvent event) {
        List<ClearEntityEntity> trueNode = new ArrayList<>();
        boolean hasBigOperator = false;
        for (ClearEntityEntity clearDown : ClearEntityService.getClearDowns()) {
            if (!this.compareInteger(event.getCount(), clearDown.getCount(), clearDown.getType()))
                continue;

            // 当满足多个条件判断用最大值还是最小值
            if (clearDown.getType().contains(">"))
                hasBigOperator = true;

            trueNode.add(clearDown);
        }

        List<String> message;
        List<Integer> list = new ArrayList<>();
        for (ClearEntityEntity clearEntityEntity : trueNode)
            list.add(clearEntityEntity.getCount());

        int time;
        if (hasBigOperator) {
            time = ListUtil.getListMaxNumber(list);
        } else
            time = ListUtil.getListMinNumber(list);

        ClearEntityEntity entity = null;
        for (ClearEntityEntity clearEntityEntity : trueNode) {
            if (time != clearEntityEntity.getCount())
                continue;

            entity = clearEntityEntity;
        }

        assert entity != null;
        message = entity.getMessage();

        ServerUtil.getOnlinePlayers().forEach(player -> {
            for (String s : message) {
                player.sendMessage(new VariableFactory(s)
                                .prefix(ClearEntityConstant.MESSAGE_PREFIX)
                                .date(ClearEntityConstant.SET_VARIABLE_DATEFORMAT)
                                .amount(String.valueOf(event.getCount()))
                                .player(player)
                                .placeholder(player)
                                .color()
                                .getString());
            }
        });
    }

    private boolean compareInteger(int num, int num2, String operator) {
        switch (operator) {
            case "=": {
                return num == num2;
            }
            case ">": {
                return num > num2;
            }
            case "<": {
                return num < num2;
            }
            case ">=": {
                return num >= num2;
            }
            case "=>": {
                return num >= num2;
            }
            case "<=": {
                return num <= num2;
            }
            case "=<": {
                return num <= num2;
            }
        }
        return false;
    }
}