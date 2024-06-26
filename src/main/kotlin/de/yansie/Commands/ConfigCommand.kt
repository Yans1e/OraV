package de.yansie.Commands

import de.yansie.*
import net.axay.kspigot.extensions.broadcast


import net.axay.kspigot.extensions.server
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.command.ConsoleCommandSender

class ConfigCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender.isOp&&sender !is ConsoleCommandSender) {

            var p = sender as Player
            Bukkit.getOnlinePlayers().forEach {
                if (it.name == args!![1]) {
                    p = it
                }
            }
        when(args?.get(0)) {
            "debug" -> {
                broadcast("MM: $MinuteMap")
                broadcast("SM: $SecondMap")
                broadcast("TM: $TimerMap")
                broadcast("JM: $joinMap")
                broadcast("NNS: $nonNormalShutdown")
                broadcast("CFG:")
                cfg.getKeys(true).forEach{
                    broadcast("$it: " + cfg[it])
                }
            }
            "map" -> {
                sender.sendMessage(MinuteMap.toString())
                sender.sendMessage(SecondMap.toString())
            }
            "timer" -> {
                // config timer Yansie_ 12 5
                if (args[2].toInt() < 1) {
                    sender.sendMessage("Timer von " + p.name + " von " + TimerMap[p]?.minute + ":" + TimerMap[p]?.second + " auf 0" + args[2] + ":" + args[3] + " gesetzt")
                }
                if (args[3].toInt() < 10) {
                    sender.sendMessage("Timer von " + p.name + " von " + TimerMap[p]?.minute + ":" + TimerMap[p]?.second + " auf " + args[2] + ":0" + args[3] + " gesetzt")
                }
                if (args[2].toInt() < 10 && args[3].toInt() < 10) {
                    sender.sendMessage("Timer von " + p.name + " von " + TimerMap[p]?.minute + ":" + TimerMap[p]?.second + " auf 0" + args[2] + ":0" + args[3] + " gesetzt")
                }
                TimerMap.remove(p)
                TimerMap.put(p, Timer())
                TimerMap[p]?.p = p
                TimerMap[p]?.minute = args[2].toInt()
                TimerMap[p]?.second = args[3].toInt()
                TimerMap[p]?.startTimer()
            }

            "config" -> {
                // config config Yansie_ s. <187>
                if (args.size == 3) {
                    sender.sendMessage(cfg[args[2] + args[1]].toString())
                }
                if (args.size == 4) {
                    cfg.set(args[2] + args[1], args[3].toInt())
                }
            }

            "configaddall" -> {
                server.worlds.first().worldBorder.size -= 100
                cfg.getKeys(true).forEach {
                    sender.sendMessage(it)
                    if (!it.startsWith("mm.")||!it.startsWith("sm.")) {
                        sender.sendMessage(it)
                        sender.sendMessage(cfg[it] as String)
                        if (cfg[it] as Int <= 2) {
                            cfg.set(it, cfg[it] as Int + 1)
                        }
                        sender.sendMessage(cfg[it] as String)
                    }
                }
            }
        }
        }
        if (sender.isOp){
            when(args?.get(0)) {
                "debug" -> {
                    broadcast("MM: $MinuteMap")
                    broadcast("SM: $SecondMap")
                    broadcast("TM: $TimerMap")
                    TimerMap.forEach{
                        broadcast(it.key.name + " - " + it.key.uniqueId +" - "+ it.value.minute +":" + it.value.second)
                    }
                    broadcast("JM: $joinMap")
                    joinMap.forEach{
                        broadcast(it.key+" - "+ it.value.minute +":" + it.value.second)
                    }
                    broadcast("NNS: $nonNormalShutdown")
                    broadcast("CFG:")
                    cfg.getKeys(true).forEach{
                        broadcast("$it: " + cfg[it])
                    }

                }
            }
        }
        /*
        if (sender !is Player){
            sender.sendMessage(cfg.currentPath)
            when(args?.get(0)){
                "cconfig" -> {
                    // config config Yansie_ s. <187>
                    if (args.size==3){
                        sender.sendMessage(cfg[args[2]+args[1]].toString())
                    }
                    if (args.size==4){
                        cfg.set(args[2]+args[1],args[3].toInt())
                    }
                }
                "cconfigaddall" -> {
                    server.worlds.first().worldBorder.size -= 100
                    cfg.getKeys(true).forEach{
                        if(it.startsWith("s.")){
                            if (cfg[it] as Int ==2) return true
                            cfg.set(it, cfg[it] as Int + 1)
                        }
                    }
                }
                "cspawn1" -> {
                    cfg.set("l."+args[1],Location(server.worlds.first(),args[2].toDouble(),args[3].toDouble(),args[4].toDouble()))
                }
                "cspawn2" -> {
                    cfg.set("l."+args[1],Location(server.worlds[2],args[2].toDouble(),args[3].toDouble(),args[4].toDouble()))
                }
            }

         */
        return true

    }
}