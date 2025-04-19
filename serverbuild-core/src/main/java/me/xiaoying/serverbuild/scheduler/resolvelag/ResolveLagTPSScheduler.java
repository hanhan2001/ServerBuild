package me.xiaoying.serverbuild.scheduler.resolvelag;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.module.ResolveLagModule;
import me.xiaoying.serverbuild.scheduler.Scheduler;

import java.util.ArrayDeque;
import java.util.Queue;

public class ResolveLagTPSScheduler extends Scheduler {
    private final Queue<Long> tickIntervals = new ArrayDeque<>();
    public long lastTickTime = System.currentTimeMillis();

    public ResolveLagTPSScheduler() {
        super(Type.SYNC_REPEAT);
    }

    @Override
    public Runnable getRunnable() {
        return () -> {
            long now = System.currentTimeMillis();
            long delta = now - this.lastTickTime;
            this.lastTickTime = now;

            synchronized (this.tickIntervals) {
                this.tickIntervals.add(delta);

                if (this.tickIntervals.size() <= 20 * 60 * 15)
                    return;

                this.tickIntervals.poll();
            }

            // 每 10 tick 更新一次 TPS（约 0.5 秒）
            if (this.tickIntervals.size() % 10 != 0)
                return;

            this.updateTPS();
        };
    }

    private void updateTPS() {
        synchronized (this.tickIntervals) {
            double factor1m = 1 - Math.exp(-1.0 / (20 * 60));
            double factor5m = 1 - Math.exp(-1.0 / (20 * 60 * 5));
            double factor15m = 1 - Math.exp(-1.0 / (20 * 60 * 15));

            double ema1m = 20.0, ema5m = 20.0, ema15m = 20.0;

            for (long delta : this.tickIntervals) {
                double currentTPS = 1000.0 / delta;
                ema1m = factor1m * currentTPS + (1 - factor1m) * ema1m;
                ema5m = factor5m * currentTPS + (1 - factor5m) * ema5m;
                ema15m = factor15m * currentTPS + (1 - factor15m) * ema15m;
            }

            ResolveLagModule module = (ResolveLagModule) SBPlugin.getModuleManager().getModule("ResolveLag");

            module.setTPS(new double[] {
                    Math.min(ema1m, 20.0),
                    Math.min(ema5m, 20.0),
                    Math.min(ema15m, 20.0)
            });
        }
    }
}