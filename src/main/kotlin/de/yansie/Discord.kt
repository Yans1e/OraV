package de.yansie

import io.papermc.paper.event.player.AsyncChatEvent
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.extensions.bukkit.toLegacyString
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerChatEvent
import java.time.LocalDate


class Discord : ListenerAdapter(){

    // COMMAND REGISTRATION IN MAIN


    //COMMANDS
    override fun onSlashCommandInteraction(e: SlashCommandInteractionEvent) {
        when(e.name){
            "speichern" -> {
                cfg["d."+e.user.name] = e.getOption("spielername")!!.asString
                e.reply("Spielername auf " + e.getOption("spielername")!!.asString + " gesetzt.").queue()
            }
            "config" -> {
                if (e.channelId!=staffChannelID.toString()){
                    return
                }
                if (e.getOption("value") == null){
                    e.reply(cfg[e.getOption("spielername")!!.asString].toString() +"").queue()
                } else {
                    cfg[e.getOption("spielername")!!.asString] = e.getOption("value")!!.asInt
                    e.reply(cfg[e.getOption("spielername")!!.asString].toString() +"").queue()
                }
            }
            "einlösen" -> {

                if (e.channelId!=spielzeitenChannelID.toString()){
                    return
                }


                var name = e.getOption("spielername")?.asString
                if (name == null){
                    if (cfg.contains("d."+e.user.name)){
                        name = cfg["d."+e.user.name] as String
                    } else {
                        e.reply("Kein Name gespeichert. Nutzte /speichern").queue()
                        return
                    }
                } else name = e.getOption("spielername")!!.asString



                var player : Player? = null
                Bukkit.getOnlinePlayers().forEach {
                    if (it.name==name) player = it
                }
                if (player?.isOnline == true){
                    e.reply("Du kannst Spielzeiten über Discord nur offline einlösen\nUm Spielzeiten ingame einzulösen nutzte den /einlösen Befehl in Minecraft").queue()
                    return
                }



                if (joinMap.keys.contains(name)) return


                if (cfg.contains(name!!)) {
                    if (!cfg.contains("mm.$name")) {
                        if (cfg[name] as Int <= 0) {
                            e.reply("Keine Spielzeiten mehr!").queue()
                            return
                        } else {
                            cfg.set(name, cfg[name] as Int - 1)
                            e.reply("Du kannst in den nächsten 30 Minuten auf den Server joinen und hast noch " + cfg[name] + " weitere Spielzeiten")
                                .queue()
                        }
                    } else e.reply("Du kannst in den nächsten 30 Minuten auf den Server joinen und hast noch " + cfg[name] + " weitere Spielzeiten")
                        .queue()
                } else {
                    cfg.set(name, 1)
                    e.reply("Du kannst in den nächsten 30 Minuten auf den Server joinen und hast noch " + cfg[name] + " weitere Spielzeiten").queue()
                }


                MinuteMap.remove(name)
                SecondMap.remove(name)
                joinMap.put(name, NoPlayerTimer())
                joinMap[name]?.startTimer()


            }
            "spielzeit" -> {
                if (e.channelId!=spielzeitenChannelID.toString()){
                    e.reply("<#1168207579390935111>").queue()
                    return
                }
                if (cfg[e.getOption("spielername")?.asString!!]==null) {
                    e.reply("Spieler nicht eingetragen").queue()
                } else e.reply(e.getOption("spielername")?.asString!!+" hat noch "+cfg[e.getOption("spielername")?.asString!!]+" Spielzeiten").queue()
            }
            "neuertag" -> {
                val currentDate = LocalDate.now()
                // Tag der Woche
                val dayOfMonth = currentDate.dayOfMonth
                if (cfg.contains("day")){
                    if(cfg["day"] != dayOfMonth){
                        cfg.getKeys(true).forEach {
                            if (cfg[it] is Int){
                                if ((cfg[it] as Int) < 3) {
                                    cfg[it] = cfg[it] as Int + 1
                                }
                            }
                        }
                        Bukkit.getWorlds().forEach{
                            it.worldBorder.size -= 100
                        }
                    }
                }
                cfg.set("day",dayOfMonth)
            }
        }
    }

    //DISCORD -> INGAME
    override fun onMessageReceived(e: MessageReceivedEvent){
        if (e.channel.id==ingameChannelID.toString()) {
            if (e.author.id != botID.toString()) {
                broadcast(ChatColor.AQUA.toString() + e.author.name + ChatColor.WHITE + " » " + e.message.contentRaw)
            }
        }
    }

    //INGAME -> DISCORD

    var l = listen<AsyncChatEvent> {
        if (messages==3) {
            broadcast(it.player.name + " » " + it.message().toLegacyString())
            jda.getTextChannelById(ingameChannelID)?.sendMessage(it.player.name + " » " + it.message().toLegacyString())?.queue()
            it.isCancelled = true
            messages = 0
        }
        messages++


    }
}