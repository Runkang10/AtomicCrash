package io.github.runkang10.atomicCrash.schedulers

import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import org.bukkit.plugin.java.JavaPlugin

class GlobalRegionScheduler(
    private val plugin: JavaPlugin?
) {
    private val scheduler get() = plugin?.server?.globalRegionScheduler

    fun run(scheduledTask: (ScheduledTask) -> Unit): ScheduledTask? = plugin?.let {
        scheduler?.run(it, scheduledTask)
    }

    fun runDelayed(
        scheduledTask: (ScheduledTask) -> Unit,
        delay: Long
    ): ScheduledTask? = plugin?.let {
        scheduler?.runDelayed(it, scheduledTask, delay)
    }

    fun runAtFixedRate(
        scheduledTask: (ScheduledTask) -> Unit,
        initialDelay: Long,
        delay: Long
    ): ScheduledTask? = plugin?.let {
        scheduler?.runAtFixedRate(it, scheduledTask, initialDelay, delay)
    }
}