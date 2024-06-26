package de.yansie

import de.yansie.Commands.*
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.extensions.server
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.EntityType
import java.awt.Color
import java.time.LocalDate


object StartUp {
    fun startup() {
        //COMMAND REGISTRATION
        main.getCommand("stats")?.setExecutor(StatsCommand())
        main.getCommand("start")?.setExecutor(StartCommand())
        main.getCommand("config")?.setExecutor(ConfigCommand())
        main.getCommand("dcsend")?.setExecutor(SendDiscordMessageCommand())
        main.getCommand("einlösen")?.setExecutor(EinlösenCommand())
        main.getCommand("nonnormalshutdown")?.setExecutor(NonNormalShutdownCommand())


        //DISCORD
        Discord()

        //LISTENERS
        Listeners



        var i = 3
        server.scheduler.runTaskTimer(main, Runnable {
            main.saveConfig()
            if (i>=0) i--
            if (i==0){
                Discord()
                var e = EmbedBuilder()
                    .setAuthor("Server ist online")
                    .setColor(Color.GREEN)
                    .build()
                jda.getTextChannelById(ingameChannelID)?.sendMessageEmbeds(e)?.queue()
            }




            //REMOVE VILLAGERS
            server.worlds.first().entities.forEach{
                if (it.type== EntityType.VILLAGER) it.remove()
            }
            //UPDATE BOARDS
            boards.values.forEach {
                val board = it
                Bukkit.getOnlinePlayers().forEach {
                    updateBoard(board)
                }
            }
            //JOIN TIMER IST ABGELAUFEN
            joinMap.forEach{
                if (it.value.minute<=0&&it.value.second<=0){
                    if (!it.value.hasJoined){
                        cfg.set(it.key,cfg[it.key] as Int +1)
                        jda.getTextChannelById(spielzeitenChannelID)?.sendMessage(it.key+" hat seine Spielzeit nicht eingelöst")?.queue()
                        MinuteMap.remove(it.key)
                        SecondMap.remove(it.key)
                    }
                    joinMap.remove(it.key)

                }

            }
            //00:00 CHECK
            Bukkit.getOnlinePlayers().forEach {
                if (!TimerMap.contains(it)) return@forEach
                if (TimerMap[it]?.minute!! <=0&&TimerMap[it]?.second!! <=0){
                    val c = Component.text("Spielzeit ausgelaufen!", NamedTextColor.RED)
                    val c1 =Component.text("\nVerbleibene Spielzeiten: "+ cfg[it.name], NamedTextColor.BLUE)
                    it.kick(c.append(c1))
                    val c3 = Component.text("« " + it.name, NamedTextColor.RED)
                    if (it.gameMode != GameMode.SPECTATOR&& joinMap.contains(it.name)) {
                        var e = EmbedBuilder()
                            .setAuthor(it.name + " left")
                            .setColor(Color.RED)
                            .build()
                        jda.getTextChannelById(ingameChannelID)?.sendMessageEmbeds(e)?.queue()
                        broadcast(c3)
                    }
                }
                if (TimerMap[it]?.minute!! <=0&&TimerMap[it]?.second!! <=15){
                    it.sendMessage("§cDeine Spielzeit läuft in 15 Sekunden aus. Nutzte /einlösen (Ingame) um sie zu verlängern")
                }
            }
        }, 0, 20)
        server.scheduler.runTaskTimer(main, Runnable {
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
        },0,1200)


    }
}