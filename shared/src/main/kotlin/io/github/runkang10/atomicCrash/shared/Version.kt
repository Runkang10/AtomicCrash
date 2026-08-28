package io.github.runkang10.atomicCrash.shared

import org.bukkit.entity.Player

interface Version {
    fun crash(player: Player)
}