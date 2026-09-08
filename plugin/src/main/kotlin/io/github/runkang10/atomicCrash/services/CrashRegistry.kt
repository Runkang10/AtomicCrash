package io.github.runkang10.atomicCrash.services

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant

object CrashRegistry {
    data class UserData(
        val sender: CommandSender,
        val targetName: String,
        val expiresAt: Instant
    )

    private val users = ConcurrentHashMap<UUID, UserData>()


    fun add(
        sender: CommandSender,
        player: Player
    ) = users.set(player.uniqueId, UserData(sender, player.name, now() + 5.seconds))

    fun remove(uuid: UUID) = users.remove(uuid)

    fun consumeIfValid(uuid: UUID): UserData? {
        val data = users[uuid] ?: return null

        if (data.expiresAt <= now()) {
            users.remove(uuid, data)
            return null
        }

        return if (users.remove(uuid, data)) data else null
    }

    fun clear() = users.clear()


    private fun now() = Clock.System.now()
}