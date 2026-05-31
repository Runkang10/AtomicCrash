package io.github.runkang10.atomicCrash.schedulers

import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.TimeUnit

class AsyncScheduler(
    private val plugin: JavaPlugin?
) {
    private val scheduler get() = plugin?.server?.asyncScheduler

    fun runNow(scheduledTask: (ScheduledTask) -> Unit): ScheduledTask? = plugin?.let {
        scheduler?.runNow(it, scheduledTask)
    }

    fun runDelayed(
        scheduledTask: (ScheduledTask) -> Unit,
        delay: Long,
        timeUnit: TimeUnit
    ): ScheduledTask? = plugin?.let {
        scheduler?.runDelayed(it, scheduledTask, delay, timeUnit)
    }

    fun runAtFixedRate(
        scheduledTask: (ScheduledTask) -> Unit,
        initialDelay: Long,
        delay: Long,
        timeUnit: TimeUnit
    ): ScheduledTask? = plugin?.let {
        scheduler?.runAtFixedRate(it, scheduledTask, initialDelay, delay, timeUnit)
    }
}