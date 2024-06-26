package de.yansie.Commands

import de.yansie.Timer
import de.yansie.TimerMap
import de.yansie.cfg
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class EinlösenCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(sender !is Player) return true



        if (cfg[sender.name] as Int <= 0) {
           sender.sendMessage("Keine Spielzeiten übrig")
        } else {
            cfg.set(sender.name, cfg[sender.name] as Int - 1)
            sender.sendMessage("Spielzeit eingelöst")
            sender.sendMessage("Verbleibende Spielzeiten: "+cfg[sender.name])
            var m = TimerMap[sender]?.minute
            var s = TimerMap[sender]?.second
            TimerMap.put(sender, Timer())
            TimerMap[sender]?.add(m,s)
            TimerMap[sender]?.p = sender
            TimerMap[sender]?.startTimer()
        }


        return true
    }
}