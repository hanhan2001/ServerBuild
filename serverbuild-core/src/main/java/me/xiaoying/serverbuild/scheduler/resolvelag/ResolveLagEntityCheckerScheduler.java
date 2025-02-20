package me.xiaoying.serverbuild.scheduler.resolvelag;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.entity.resolvelag.ResolveLagClearDownEntity;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.resolvelag.FileResolveLag;
import me.xiaoying.serverbuild.file.resolvelag.FileResolveLagEntityChecker;
import me.xiaoying.serverbuild.manager.resolvelag.ResolveLagEntityCheckerManager;
import me.xiaoying.serverbuild.module.ResolveLagModule;
import me.xiaoying.serverbuild.scheduler.Scheduler;
import org.bukkit.Bukkit;

import java.util.concurrent.atomic.AtomicInteger;

public class ResolveLagEntityCheckerScheduler extends Scheduler {
    private final ResolveLagEntityCheckerManager manager;

    public ResolveLagEntityCheckerScheduler() {
        super(Type.SYNC_REPEAT, FileResolveLagEntityChecker.ENTITY_CHECKER_INTERVAL);

        this.manager = ((ResolveLagModule) (SBPlugin.getModuleManager().getModule("ResolveLag"))).getEntityCheckerManager();
    }

    @Override
    public Runnable getRunnable() {
        return () -> Bukkit.getWorlds().forEach(world  -> {
            if (FileResolveLagEntityChecker.ENTITY_CHECKER_EXCLUDE_WORLD.contains(world.getName()))
                return;

            AtomicInteger amount = new AtomicInteger();

            this.manager.getCheckerEntities().forEach(checker -> amount.addAndGet(checker.clear(world.getEntities())));

            if (FileResolveLagEntityChecker.ENTITY_CHECKER_MESSAGE_ENABLE && amount.get() > 0)
                this.clearDown(amount.get());
        });
    }

    private void clearDown(int amount) {
        ResolveLagClearDownEntity clearDown = null;

        for (ResolveLagClearDownEntity c : this.manager.getClearDowns()) {
            if (!c.match(amount))
                continue;

            if (clearDown == null) {
                clearDown = c;
                continue;
            }

            if ((c.getType().equalsIgnoreCase("<=") || c.getType().equalsIgnoreCase("<")) && c.getCount() < clearDown.getCount())
                clearDown = c;

            if ((c.getType().equalsIgnoreCase(">=") || c.getType().equalsIgnoreCase(">")) && c.getCount() > clearDown.getCount())
                clearDown = c;
        }

        if (clearDown == null)
            return;

        clearDown.getScripts().forEach(string -> SBPlugin.getScriptManager().performScript(new VariableFactory(string)
                .prefix(FileResolveLag.SETTING_PREFIX)
                .date(FileResolveLag.SETTING_DATEFORMAT)
                .amount(amount)
                .color()
                .toString(), Bukkit.getServer().getConsoleSender()));
    }
}