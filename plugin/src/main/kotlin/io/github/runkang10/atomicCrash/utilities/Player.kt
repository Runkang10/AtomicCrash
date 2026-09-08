package io.github.runkang10.atomicCrash.utilities

import org.bukkit.entity.Player
import org.geysermc.floodgate.api.FloodgateApi

fun Player.isFloodgatePlayer() = runCatching {
    FloodgateApi.getInstance().isFloodgatePlayer(uniqueId)
}.getOrDefault(false)