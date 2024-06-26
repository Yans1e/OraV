package de.yansie

import fr.mrmicky.fastboard.FastBoard
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.bukkit.Bukkit
import org.bukkit.Statistic
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.ArrayList


//ANDERE PLUGINS:
// CHUNKY (Chunks prerendern)
//

//VERSION ÄNDERN:
//plugin.yml:
//          api-version
//          "net.axay:kspigot:1.20.1"
//build.gradle.kts:
//          paperDevBundle("1.20.1-R0.1-SNAPSHOT")
//          implementation("net.axay:kspigot:1.20.1")
//          runPaper{ version = "1.20.1" }


var nonNormalShutdown = false
var messages = 3

//DISCORD
var jdaToken = "MTA1MzQxNjEyNTk4MDk5OTY4MA.GfUtRD.131chU6ARDf5wX01sOLAvfmSzOWRgHWtdYLFvU"
var jda = JDABuilder.createDefault(jdaToken).enableIntents(GatewayIntent.MESSAGE_CONTENT).addEventListeners(Discord()).build()
var botID = 1053416125980999680

//DISCORD ROLES
val adminRoleID = 0
val memberRoleID = 0

//DISCORD CHANNELS
val ingameChannelID = 1180181956974092350
val spielzeitenChannelID = 1168207579390935111
val informationenChannelID = 1180181185092137092
val staffChannelID = 915634966912700436





















//CODE


//HASHMAPS
val TimerMap = HashMap<Player,Timer>()
var MinuteMap = HashMap<String,Int>()
var SecondMap = HashMap<String,Int>()
var joinMap = HashMap<String,NoPlayerTimer>()
val boards = HashMap<UUID, FastBoard>()

//SCOREBOARD
fun updateBoard(board: FastBoard) {
    board.updateLines(
        "",
        "Players: ",
        Bukkit.getServer().getOnlinePlayers().size.toString(),
        "",
        "Kills: ",
        board.getPlayer().getStatistic(Statistic.PLAYER_KILLS).toString(),
        "",
        "Time: ",
        TimerMap[board.player]?.minuteString +":"+ TimerMap[board.player]?.secondString,
        "",
        "Spielzeiten:",
        cfg[board.player.name].toString(),
        "",
        "Border: ",
        (Bukkit.getWorlds().get(0).worldBorder.size / 2).toString(),
        "",
    )
}



