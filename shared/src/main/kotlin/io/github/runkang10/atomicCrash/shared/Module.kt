package io.github.runkang10.atomicCrash.shared

import org.bukkit.entity.Player

interface Module {
    fun crash(player: Player): Result<Unit>
}