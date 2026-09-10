package io.github.runkang10.atomicCrash.shared

import net.minecraft.server.level.ServerPlayer
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player

fun Player.asServerPlayer(): ServerPlayer = (this as CraftPlayer).handle