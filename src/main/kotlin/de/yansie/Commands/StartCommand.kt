package de.yansie.Commands

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.axay.kspigot.extensions.bukkit.title
import net.axay.kspigot.runnables.sync
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.Firework
import org.bukkit.entity.Player
import java.time.Duration

var projectstart = false
class StartCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (!sender.isOp) return true
        projectstart = !projectstart
        Bukkit.getOnlinePlayers().forEach {
            CoroutineScope(Dispatchers.Default).launch {
                run(it)
            }
        }
        return true
    }
    var c = Component.text("")
    var c1 = Component.text("Ora",NamedTextColor.GOLD)
    var c2 = Component.text("Vier",NamedTextColor.GOLD)
    var c3 = Component.text("startet",NamedTextColor.DARK_GRAY)
    var c4 = Component.text("in",NamedTextColor.DARK_GRAY)
    var c5 = Component.text("15",NamedTextColor.GOLD)
    var c6 = Component.text("10",NamedTextColor.GOLD)
    var c7 = Component.text("5",NamedTextColor.GOLD)
    var c8 = Component.text("4",NamedTextColor.GOLD)
    var c9 = Component.text("3",NamedTextColor.GOLD)
    var c10 = Component.text("2",NamedTextColor.GOLD)
    var c11 = Component.text("1",NamedTextColor.GOLD)
    suspend fun run(it:Player){
        it.title(c1, c, Duration.ofMillis(100), Duration.ofMillis(1800), Duration.ofMillis(100))
        delay(2000)
        it.title(c2, c, Duration.ofMillis(100), Duration.ofMillis(1800), Duration.ofMillis(100))
        delay(2000)
        it.title(c3, c, Duration.ofMillis(100), Duration.ofMillis(1800), Duration.ofMillis(100))
        delay(2000)
        it.title(c4, c, Duration.ofMillis(100), Duration.ofMillis(1800), Duration.ofMillis(100))
        delay(2000)
        it.title(c5, c, Duration.ofMillis(100), Duration.ofMillis(800), Duration.ofMillis(100))
        delay(5000)
        it.title(c6, c, Duration.ofMillis(100), Duration.ofMillis(800), Duration.ofMillis(100))
        delay(5000)
        it.title(c7, c, Duration.ofMillis(100), Duration.ofMillis(800), Duration.ofMillis(100))
        delay(1000)
        it.title(c8, c, Duration.ofMillis(100), Duration.ofMillis(800), Duration.ofMillis(100))
        delay(1000)
        it.title(c9, c, Duration.ofMillis(100), Duration.ofMillis(800), Duration.ofMillis(100))
        delay(1000)
        it.title(c10, c, Duration.ofMillis(100), Duration.ofMillis(800), Duration.ofMillis(100))
        delay(1000)
        it.title(c11, c, Duration.ofMillis(100), Duration.ofMillis(800), Duration.ofMillis(100))
        delay(1000)
        sync {
            it.gameMode = GameMode.SURVIVAL
        }
    }
}