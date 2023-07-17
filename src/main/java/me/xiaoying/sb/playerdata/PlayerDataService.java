package me.xiaoying.sb.playerdata;

import me.xiaoying.mf.SqlFactory;
import me.xiaoying.sb.constant.ConfigConstant;
import me.xiaoying.sb.exception.NotFoundPlayerDataException;
import me.xiaoying.sb.exception.SamePlayerData;
import me.xiaoying.sb.utils.ExceptionUtil;

import java.util.*;

public class PlayerDataService {
    Map<String, List<SubPlayerData>> data = new HashMap<>();

    public void filePlayerData(String handle) {
        if (this.data.get(handle.toUpperCase()) == null)
            return;

        for (SubPlayerData value : this.data.get(handle.toUpperCase()))
            value.fileData();
    }

    public void filePlayerData(String handle, String playerData) {
        for (SubPlayerData value : this.data.get(handle.toUpperCase())) {
            if (!playerData.equalsIgnoreCase(value.getName()))
                continue;

            value.fileData();
        }
    }

    public void filePlayerDatas() {
        for (List<SubPlayerData> value : this.data.values())
            value.forEach(SubPlayerData::fileData);
    }

    public void registerPlayerData(String handle, SubPlayerData playerData) {
        List<SubPlayerData> list;
        if (this.data.get(handle.toUpperCase()) == null)
            list = new ArrayList<>();
        else
            list = this.data.get(handle.toUpperCase());

        if (list.contains(playerData))
            ExceptionUtil.throwException(new SamePlayerData("Already registered same name PlayerData, please check your code."));

        list.add(playerData);
        this.data.put(handle.toUpperCase(), list);
    }

    public void unregisterPlayerData(String handle) {
        if (this.data.get(handle.toUpperCase()) == null)
            return;

        this.data.remove(handle.toUpperCase());
    }

    public void unregisterPlayerData(String handle, SubPlayerData playerData) {
        if (this.data.get(handle.toUpperCase()) == null)
            return;

        Iterator<SubPlayerData> iterator = this.data.get(handle.toUpperCase()).iterator();
        SubPlayerData subPlayerData;
        while ((subPlayerData = iterator.next()) != null) {
            if (subPlayerData != playerData)
                continue;

            iterator.remove();
        }
    }

    public void unregisterPlayerDatas() {
        this.data.clear();
    }

    public List<SubPlayerData> getData(String handle) {
        if (this.data.get(handle.toUpperCase()) == null)
            ExceptionUtil.throwException(new NotFoundPlayerDataException("Can't find PlayerData '" + handle + "', please check is registered this PlayerData."));

        return this.data.get(handle.toUpperCase());
    }

    public SubPlayerData getData(String handle, String playerData) {
        for (SubPlayerData subPlayerData : this.data.get(handle.toUpperCase())) {
            if (!subPlayerData.getName().equalsIgnoreCase(playerData))
                continue;

            return subPlayerData;
        }

        ExceptionUtil.throwException(new NotFoundPlayerDataException("Can't find PlayerData '" + handle + "', please check is registered this PlayerData."));
        return null;
    }

    public SqlFactory getSqlFactory() {
        return new SqlFactory("jdbc:sqlite", ConfigConstant.SET_DATAPATH);
    }
}