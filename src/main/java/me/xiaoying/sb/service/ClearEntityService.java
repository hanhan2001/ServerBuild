package me.xiaoying.sb.service;

import me.xiaoying.sb.constant.ClearEntityConstant;
import me.xiaoying.sb.entity.ClearEntityEntity;
import me.xiaoying.sb.file.files.FileClearEntity;
import me.xiaoying.sb.utils.ListUtil;
import me.xiaoying.sb.utils.YamlUtil;
import org.bukkit.World;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClearEntityService {
    private static final List<ClearEntityEntity> entityEntityList = new ArrayList<>();

    public static void registerClearDown(String key) {
        String type = FileClearEntity.clearEntity.getString("ClearMessage.ClearDown." + key + ".Type");
        List<String> message = YamlUtil.getStringList(FileClearEntity.clearEntity, "ClearMessage.ClearDown." + key + ".Message");
        entityEntityList.add(new ClearEntityEntity(Integer.parseInt(key), type, message));
    }

    public static void unregisterClearDown(String key) {
        Iterator<ClearEntityEntity> iterator = entityEntityList.iterator();
        ClearEntityEntity clearEntityEntity;
        while ((clearEntityEntity = iterator.next()) != null) {
            if (clearEntityEntity.getCount() != Integer.parseInt(key))
                continue;

            iterator.remove();
        }
    }

    public static void unregisterClearDown(ClearEntityEntity entity) {
        entityEntityList.remove(entity);
    }

    public static List<ClearEntityEntity> getClearDowns() {
        return entityEntityList;
    }

    public static List<Entity> getWorldEntity(World world) {
        List<Entity> list = new ArrayList<>();
        for (Entity entity : world.getEntities()) {
            if (entity.getCustomName() != null && !ClearEntityConstant.CLEAR_NAMED)
                continue;

            if (entity instanceof Item && ListUtil.contains(ClearEntityConstant.CLEAR_ENTITY, "Item", false)) {
                if (ListUtil.contains(ClearEntityConstant.CLEAR_NOTCLEAR_ITEM, ((Item) entity).getItemStack().getType().toString(), false))
                    continue;

                list.add(entity);
            }
            if (entity instanceof Animals && ListUtil.contains(ClearEntityConstant.CLEAR_ENTITY, "Animals", false)) {
                // 判断是否为宠物
                if (entity instanceof Tameable && ((Tameable) entity).isTamed() && !ClearEntityConstant.CLEAR_PET)
                    continue;

                if (ListUtil.contains(ClearEntityConstant.CLEAR_NOTCLEAR_ANIMAL, entity.getName(), false))
                    continue;

                list.add(entity);
            }
            if (entity instanceof Monster && ListUtil.contains(ClearEntityConstant.CLEAR_ENTITY, "Monster", false)) {
                if (ListUtil.contains(ClearEntityConstant.CLEAR_NOTCLEAR_MONSTER, entity.getName(), false))
                    continue;

                list.add(entity);
            }

            if (ListUtil.contains(ClearEntityConstant.CLEAR_ENTITY, "Other", false))
                list.add(entity);
        }
        return list;
    }
}