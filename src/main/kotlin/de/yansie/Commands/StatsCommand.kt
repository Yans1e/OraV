package de.yansie.Commands

import de.yansie.Builder
import de.yansie.TimerMap
import kotlinx.coroutines.*
import net.axay.kspigot.event.listen
import net.axay.kspigot.items.*
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Statistic
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.meta.SkullMeta
import de.yansie.cfg

var gui = Bukkit.createInventory(null,9*6,"§cPlayers")
class StatsCommand : CommandExecutor {
    var i = 0
    var a = true
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) return true
        if (a) {
            a=false
            repeat(54) {
                gui.setItem(i, Builder(Material.GRAY_STAINED_GLASS_PANE, 1).setName(" ").build())
                i++
            }
            val list = listOf<Int>(
                10,
                11,
                12,
                13,
                14,
                15,
                16,
                19,
                20,
                21,
                22,
                23,
                24,
                25,
                29,
                30,
                31,
                32,
                33,
                34,
                35,
                38,
                39,
                40,
                41,
                42,
                43,
                44
            )
            i = 0
            Bukkit.getOnlinePlayers().forEach {
                gui.setItem(list[i], itemStack(Material.PLAYER_HEAD) {
                    meta<SkullMeta> {
                        owningPlayer = it
                        name = it.name()
                        val l = mutableListOf<String>(
                            "Spielzeiten: " + cfg[it.name],
                            "Time: " + TimerMap[it]?.minuteString + ":" + TimerMap[it]?.secondString,
                            "Kills: " + it.getStatistic(Statistic.PLAYER_KILLS).toString(),
                            "Playtime: " + it.getStatistic(Statistic.PLAY_ONE_MINUTE)/20/60
                        )
                        lore = l
                    }
                })
                i++
            }
            i=0

            val click = listen<InventoryClickEvent> {
                if (it.inventory.contains(Material.PLAYER_HEAD)) it.isCancelled = true
            }
            CoroutineScope(Dispatchers.Default).launch {
                refresh(gui)
            }
        }
        sender.openInventory(gui)

        return true
    }

    suspend fun refresh(it:Inventory) = coroutineScope{
        launch{
            while(true) {
                i=0
                repeat(54) {
                    gui.setItem(i, Builder(Material.GRAY_STAINED_GLASS_PANE, 1).setName(" ").build())
                    i++
                }
                val list = listOf<Int>(
                    10,
                    11,
                    12,
                    13,
                    14,
                    15,
                    16,
                    19,
                    20,
                    21,
                    22,
                    23,
                    24,
                    25,
                    29,
                    30,
                    31,
                    32,
                    33,
                    34,
                    35,
                    38,
                    39,
                    40,
                    41,
                    42,
                    43,
                    44
                )
                i = 0
                Bukkit.getOnlinePlayers().forEach {
                    gui.setItem(list[i], itemStack(Material.PLAYER_HEAD) {
                        meta<SkullMeta> {
                            owningPlayer = it
                            name = it.name()
                            val l = mutableListOf<String>(
                                "Spielzeiten: " + cfg[it.name],
                                "Time: " + TimerMap[it]?.minuteString + ":" + TimerMap[it]?.secondString,
                                "Kills: " + it.getStatistic(Statistic.PLAYER_KILLS).toString(),
                                "Playtime: " + it.getStatistic(Statistic.PLAY_ONE_MINUTE)/20/60
                            )
                            lore = l
                        }
                    })
                    i++
                }
                delay(100)
                i=0
            }
        }
    }

}