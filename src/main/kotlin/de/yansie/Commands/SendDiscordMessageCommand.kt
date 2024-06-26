package de.yansie.Commands

import de.yansie.informationenChannelID
import de.yansie.jda
import net.dv8tion.jda.api.EmbedBuilder
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.awt.Color

class SendDiscordMessageCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (!sender.isOp){
            return false
        }
        var e0 = EmbedBuilder()
            .setColor(Color.BLUE)
            .setAuthor("INFORMATIONEN")
            .addField("Prinzip",
                "Ein Team besteht aus ein bis zwei Spielern\n" +
                        "Jedes Team hat einen Teamnamen und Teamfarbe\n" +
                        "Teams können in <#1178343975875256361> angemeldet werden\n" +
                        "Falls ihr noch kein Team habt könnt ihr in <#1178344804350972055> nach einem Teampartner suchen"
                ,false)
            .addField("Start",
                "Der Start von OraVier ist am 1.12.23 um 20 Uhr\n" +
                        "Die Spieler starten in einem Loch am Spawn\n" +
                        "Beim Start werdet ihr nach einem Countdown in den Survival Modus gesetzt"
                ,false)
            .addField("Spielzeit",
                "Jeder Spieler hat zum Start des Projektes zwei Spielzeiten\n" +
                        "Eine Spielzeit entspricht 30 Minuten\n" +
                        "Jeden Tag bekommt jeder Spieler der schon einmal auf dem Server gespielt hat eine Spielzeit (Maximale Spielzeiten sind **drei**)\n" +
                        "Um eine Spielzeit einzulösen müsst ihr in <#1168207579390935111> den Befehl /einlösen spielername benutzen\n" +
                        "Spielzeiten können nur offline im Discord eingelöst werden. Um eine Spielzeit ingame einzusetzten verwendet den Befehl /einlösen\n" +
                        "Beim Projektstart müsst ihr auch über den Discord eine Spielzeit einlösen, allerdings wird bis das Projekt wirklich losgeht der 30 Minuten Timer nicht laufen"
                ,false)
            .addField("Border",
                "Die Border startet bei 2000/2000 | -2000/-2000" +
                    "\nDie Border wird jeden Tag 50 Blöcke (pro Seite) kleiner\n" +
                    "Außerdem wird für jeden gestorbenen Spieler die Border auch um 50 Blöcke verkleinert",false)
            .addField("Version",
                "Die Version ist 1.20.1",
                false)
            .addField("Chat",
                "Der OraVier Bot wird den Minecraft Chat in den Discord übertragen und andersherum (<#1180181956974092350>)",
                false)
            .addField("Ingame-Commmands",
                "/stats -> Zeigt Statistiken, sowie Spielzeiten und verbleibene Online Zeit an\n" +
                        "/einlösen -> Löst eine Spielzeit ein\n"
                ,false)
            .addField("Discord-Commmands",
                        "/einlösen -> Löst eine Spielzeit ein\n" +
                        "/speichern -> Speichert euren Namen damit ihr nicht immer ihn in den einlösen-Command schreiben müsst\n" +
                        "**Groß und Kleinschreibung bei euren Namen beachten**"
                ,false)
            .addField("Villager",
                "Es wird keine Villager auf der Map geben (Die Dörfer werden trotzdem Loot haben), Zombievillager heilen funktioniert nicht)",
                false)
            .addField("Server",
                "Der Server ist zwischen 12 und 24 Uhr online",
                false)
            .addField("Mobspawning",
                "Mobspawning ist von Beginn eingeschaltet, Phantoms spawnen gar nicht",false)
            .addField("IP",
                "Die IP ist OraVier.instanthost.net",
                false)
            .build()
        jda.getTextChannelById(informationenChannelID)?.sendMessageEmbeds(e0)?.queue()
        var e1= EmbedBuilder()
            .setAuthor("REGELN")
            .setColor(Color.BLUE)
            .addField("Hacking","Hacking, sowie andere Dinge wie XRay oder Freecam sind **ausdrücklich verboten**",false)
            .addField("Verbotene Items","Totems, Potions, TNT Minecarts, End Crystals und Respawn Anchor sind **verboten**; Betten im Nether sind **erlaubt**",false)
            .addField("Teaming","Crossteaming, also teamen mit anderen Teams ist **verboten**",false)
            .addField("Combatlogging","Combatlogging, also ausloggen während des Kampfes ist verboten",false)
            .addField("Spawn","Den Spawn (bzw Das Netherportal am Spawn) zu verändern/kaputt zu machen ist **verboten**",false)
            .addField("Spawntrapping","Spawntrapping, also Spieler die nicht eingeloggt sind trappen, z. B. in Lava setzen, einbauen, oder Wege blockieren, ist verboten",false)
            .addBlankField(false)
            .addField("Aufnahme","Ein Mitglied jedes Teams muss aufnehmen oder streamen\n" +
                    "Das VOD oder Video muss am nächsten Tag vor dem erstmaligen Spielen oder bis 20 Uhr in <#1175823281878728704> gesendet werden" ,false)
            .addField("Blackscreens","Die letzten 60 Sekunden darf geblackscreent werden",false)
            .addField("Koordinaten","Koordinaten dürfen verdeckt werden",false)
            .build()
        jda.getTextChannelById(informationenChannelID)?.sendMessageEmbeds(e1)?.queue()
        var e2= EmbedBuilder()
            .setAuthor("STRIKES")
            .setColor(Color.BLUE)
            .addField("Videos","Wenn ein Video nicht rechtzeitig gepostet wird (Ausnahmen können beantragt werden) bekommt das Team einen Strike",false)
            .addField("Spawn","Wer den Spawn verunstaltet/absichtlich grieft/Cobbelwände in 100 Blöcke Entfernung zum Spawn baut bekommt einen Strike",false)
            .addField("Strikes","**1 Strike = Verwarnung**\n**2 Strikes = Coords Leak**\n**3 Strikes** = Wir werden uns schon was einfallen lassen <:PepeM:915648006244692010>",false)
            .build()
        jda.getTextChannelById(informationenChannelID)?.sendMessageEmbeds(e2)?.queue()

        return true
    }
}