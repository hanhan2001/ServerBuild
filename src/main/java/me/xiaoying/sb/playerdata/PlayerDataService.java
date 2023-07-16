package me.xiaoying.sb.playerdata;

import me.xiaoying.mf.SqlFactory;
import me.xiaoying.sb.exception.NotFoundPlayerDataException;
import me.xiaoying.sb.utils.ExceptionUtil;

import java.util.HashMap;
import java.util.Map;

public class PlayerDataService {
    Map<String, SubPlayerData> data = new HashMap<>();

    public void registerPlayerData(String handle, SubPlayerData playerData) {
        this.data.put(handle.toUpperCase(), playerData);
    }

    public void unregisterPlayerData(String handle) {
        if (this.data.get(handle.toUpperCase()) == null)
            return;

        this.data.remove(handle.toUpperCase());
    }

    public void unregisterPlayerDatas() {
        this.data.clear();
    }

    public SubPlayerData getData(String handle) {
        if (this.data.get(handle.toUpperCase()) == null)
            ExceptionUtil.throwException(new NotFoundPlayerDataException("Can't find PlayerData '" + handle + "', please check is registered this PlayerData."));

        return this.data.get(handle.toUpperCase());
    }

    public SqlFactory getSqlFactory() {
        return new SqlFactory("jdbc:sqlite", "plugins/ServerBuild/ServerBuild.db");
    }
}