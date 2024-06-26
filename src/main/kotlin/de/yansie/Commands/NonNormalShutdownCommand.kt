package de.yansie.Commands

import de.yansie.nonNormalShutdown
import net.axay.kspigot.extensions.broadcast
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

class NonNormalShutdownCommand : CommandExecutor {
    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>?): Boolean {
        if(p0 !is Player) {
            if (p0.isOp) {
                nonNormalShutdown = !nonNormalShutdown
                p0.sendMessage("NonNormalShutdown:$nonNormalShutdown")
            }
        }
        if (p0 is ConsoleCommandSender){
            nonNormalShutdown = !nonNormalShutdown
            broadcast("NNS = $nonNormalShutdown")
        }
        return true
    }
}