package io.github.runkang10.atomicCrash.utilities

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionDefault

object PermissionUtility {
    fun register(
        permission: String,
        default: PermissionDefault
    ): String {
        val pluginManager = Bukkit.getServer().pluginManager
        if (pluginManager.getPermission(permission) != null) return permission

        val permissionNode = Permission(permission, default)
        pluginManager.addPermission(permissionNode)
        return permission
    }

    fun from(vararg nodes: String) = "atomiccrash." + nodes.joinToString(".")


    private fun CommandSender.exemptWeight() = if (this !is Player)
        Int.MAX_VALUE
    else effectivePermissions
        .filter { it.value && it.permission.startsWith(from("exempt") + ".") }
        .mapNotNull { it.permission.split(".").lastOrNull()?.toIntOrNull() }
        .maxOrNull() ?: 0

    fun CommandSender.canCrash(target: Player): Boolean {
        val targetWeight = target.exemptWeight()
        if (this !is Player) return true

        val senderWeight = exemptWeight()
        return senderWeight > targetWeight
    }
}
