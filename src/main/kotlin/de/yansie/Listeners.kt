package de.yansie

import de.yansie.Commands.projectstart
import fr.mrmicky.fastboard.FastBoard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.extensions.server
import net.axay.kspigot.runnables.sync
import net.dv8tion.jda.api.EmbedBuilder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.awt.Color



object Listeners {
    val JEvent = listen<PlayerJoinEvent> {
        val c1 = Component.text("Keine Spielzeit eingelöst", NamedTextColor.RED)
        if (joinMap.keys.contains(it.player.name)) {
            joinMap[it.player.name]?.hasJoined = true
            joinMap[it.player.name]?.delayOff()
            //MESSAGE
            val c = Component.text("» " + it.player.name, NamedTextColor.DARK_GREEN)
                .clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("stats"))
            if (it.player.gameMode != GameMode.SPECTATOR) {
                var e = EmbedBuilder()
                    .setAuthor(it.player.name + " joined")
                    .setColor(Color.GREEN).build()
                jda.getTextChannelById(ingameChannelID)?.sendMessageEmbeds(e)?.queue()
            }
            it.joinMessage = ""
            Bukkit.broadcast(c)
            if (projectstart) it.player.gameMode = GameMode.ADVENTURE

            //SCOREBOARD
            val board = FastBoard(it.player)
            board.updateTitle("§cOraVier")
            boards.put(it.player.getUniqueId(), board)



            if (cfg.contains("mm."+it.player.name)) {
                MinuteMap[it.player.name] = cfg["mm." + it.player.name] as Int
                SecondMap[it.player.name] = cfg["sm." + it.player.name] as Int
                cfg.set("mm." + it.player.name, null)
                cfg.set("sm." + it.player.name, null)
            }

            //TIMER
            TimerMap.put(it.player, Timer())



            //TIMER SETZTEN BEIM REJOINEN
            if (MinuteMap.contains(it.player.name)) {
                TimerMap[it.player]?.minute = MinuteMap[it.player.name]!!
            }
            if (SecondMap.contains(it.player.name)) {
                TimerMap[it.player]?.second = SecondMap[it.player.name]!!
            }


            TimerMap[it.player]?.p = it.player
            TimerMap[it.player]?.startTimer()




        } else {
            if (!it.player.isOp) {
                it.joinMessage = ""
                val board = FastBoard(it.player)
                board.updateTitle("§cOraVier")
                boards.put(it.player.getUniqueId(), board)
                CoroutineScope(Dispatchers.Default).launch {
                    kickAfterSeconds(it.player, c1)
                }
            } else {
                val c = Component.text("» " + it.player.name, NamedTextColor.DARK_GREEN)
                    .clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("stats"))
                val board = FastBoard(it.player)
                board.updateTitle("§cOraVier")
                boards.put(it.player.getUniqueId(), board)
                it.joinMessage = ""
                Bukkit.broadcast(c)
                it.player.sendMessage("§cDu hast keine Spielzeit eingelöst, kannst aber tortzdem joinen da du OP besitzt")
            }
        }
    }

    val QEvent = listen<PlayerQuitEvent> {
        //MESSAGES
        val c = Component.text("« " + it.player.name, NamedTextColor.RED)
        if (it.player.gameMode != GameMode.SPECTATOR&& joinMap.contains(it.player.name)) {
            var e = EmbedBuilder()
                .setAuthor(it.player.name + " left")
                .setColor(Color.RED)
                .build()
            jda.getTextChannelById(ingameChannelID)?.sendMessageEmbeds(e)?.queue()
            Bukkit.broadcast(c)
        }

        it.quitMessage = ""

        boards.remove(it.player.uniqueId)?.delete()

        if (TimerMap.contains(it.player)) {
            MinuteMap[it.player.name] = TimerMap[it.player]?.minute!!
            SecondMap[it.player.name] = TimerMap[it.player]?.second!!
            TimerMap.remove(it.player)
        }
    }
    val killListener = listen<PlayerDeathEvent> {
        if (it.player.gameMode == GameMode.SPECTATOR) return@listen
        var e = EmbedBuilder()
            .setAuthor(it.deathMessage)
            .setColor(Color.BLACK).build()
        jda.getTextChannelById(ingameChannelID)?.sendMessageEmbeds(e)?.queue()
        broadcast(server.worlds[1].worldBorder.size.toString())
        broadcast(Bukkit.getWorld("world")?.worldBorder?.size.toString())
        if (server.worlds[1].worldBorder.size >= 301) {
            server.worlds.forEach {
                it.worldBorder.size -= 100
            }
            var e2 = EmbedBuilder()
                .setAuthor("Worldborder auf "+Bukkit.getWorlds().get(0).worldBorder.size / 2  +" gesetzt")
                .setColor(Color.BLACK).build()
            jda.getTextChannelById(ingameChannelID)?.sendMessageEmbeds(e2)?.queue()
        }
        it.player.gameMode = GameMode.SPECTATOR

    }
    val verboteneItemsListener = listen<InventoryClickEvent> {
        it.inventory.remove(Material.TNT_MINECART)
        it.inventory.remove(Material.TOTEM_OF_UNDYING)
        it.inventory.remove(Material.RESPAWN_ANCHOR)
        it.inventory.remove(Material.END_CRYSTAL)
        it.inventory.remove(Material.POTION)
    }
    suspend fun kickAfterSeconds(player:Player,c:Component){
        delay(100)
        sync {
            player.kick(c)
        }
    }
}