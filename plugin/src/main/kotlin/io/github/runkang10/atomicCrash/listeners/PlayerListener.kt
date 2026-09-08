package io.github.runkang10.atomicCrash.listeners

import io.github.runkang10.atomicCrash.configurations.DefaultSettings
import io.github.runkang10.atomicCrash.configurations.DefaultTranslations
import io.github.runkang10.atomicCrash.services.CrashRegistry
import io.github.runkang10.atomicCrash.services.PrefixedSender.send
import io.github.runkang10.atomicCrash.utilities.Tags
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.papermc.paper.event.player.AsyncChatEvent
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent
import org.bukkit.OfflinePlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.*

class PlayerListener(
    private val settings: LoggedConfiguration<DefaultSettings>,
    private val translations: LoggedConfiguration<DefaultTranslations>
) : Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    fun onQuit(event: PlayerQuitEvent) {
        CrashRegistry.remove(event.player.uniqueId)
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerChat(event: AsyncChatEvent) = onEvent(event)

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerCommand(event: PlayerCommandPreprocessEvent) = onEvent(event)

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerRotation(event: PlayerMoveEvent) {
        if (event.hasChangedOrientation()) return

        onEvent(event)
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerTeleport(event: PlayerTeleportEvent) = onEvent(event)

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerInventorySlotChange(event: PlayerInventorySlotChangeEvent) = onEvent(event)

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerInteract(event: PlayerInteractEvent) = onEvent(event)


    private fun onEvent(event: PlayerEvent) {
        if (!settings.get().check.enabled) return

        val data = CrashRegistry.consumeIfValid(event.player.uniqueId) ?: return
        val sender = data.sender
        if (sender is OfflinePlayer && !sender.isOnline) return

        sender.send(translations.get().crash.notCrashed, Tags.default(event.player.name))
    }
}