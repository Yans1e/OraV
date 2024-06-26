package de.yansie

import io.netty.handler.codec.http.cookie.ServerCookieDecoder
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.main.KSpigot
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import java.awt.Color


lateinit var cfg : FileConfiguration
lateinit var main : Main

class Main : KSpigot(){
    override fun startup() {
        main = this
        cfg = this.config

        jda.updateCommands().queue()

        jda.upsertCommand("spielzeit" +
                "","Sehe wieviele Spielzeiten du hast || Nur in #Spielzeiten verfügbar")
            .addOptions(
                OptionData(OptionType.STRING,"spielername","Spielername",true)
            ).queue()
        jda.upsertCommand("neuertag" +
                "","Nur als Admin oder mit Erlaubnis benutzten || Nur in #Spielzeiten verfügbar").queue()
        jda.upsertCommand("einlösen"+
                "","Löse eine Spielzeit ein || Nur in #Spielzeiten verfügbar")
            .addOptions(
                OptionData(OptionType.STRING,"spielername","Spielername",false)
            ).queue()
        jda.upsertCommand("speichern" +
                "","Speichere deinen Minecraft-Ingame Namen um ihn zu speichern")
            .addOptions(
                OptionData(OptionType.STRING,"spielername","Spielername",true)
            ).queue()
        jda.upsertCommand("config" +
                "","Nur für Admins")
            .addOptions(
                OptionData(OptionType.STRING,"spielername","Spielername",true)
            ).addOptions(
                OptionData(OptionType.STRING,"value","Value",false)
            ).queue()


        StartUp.startup()
    }
    override fun shutdown() {

        Bukkit.getOnlinePlayers().forEach {
            var e = EmbedBuilder()
                .setAuthor(it.player?.name+" left")
                .setColor(Color.RED)
                .build()
            jda.getTextChannelById(ingameChannelID)?.sendMessageEmbeds(e)?.queue()
            if (TimerMap.contains(it.player)) {
                MinuteMap[it.player!!.name] = TimerMap[it.player]?.minute!!
                SecondMap[it.player!!.name] = TimerMap[it.player]?.second!!
                TimerMap.remove(it.player)
            }
        }
        if (nonNormalShutdown) {
            MinuteMap.forEach {
                cfg.set("mm." + it.key, it.value)
                saveConfig()
            }
            SecondMap.forEach {
                cfg.set("sm." + it.key, it.value)
                saveConfig()
            }
        }
        var e = EmbedBuilder()
            .setAuthor("Server ist offline")
            .setColor(Color.RED)
            .build()
        jda.getTextChannelById(ingameChannelID)?.sendMessageEmbeds(e)?.queue()
    }

}