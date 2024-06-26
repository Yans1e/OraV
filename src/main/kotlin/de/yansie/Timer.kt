package de.yansie


import de.yansie.Commands.projectstart
import kotlinx.coroutines.*
import net.axay.kspigot.runnables.sync
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType


class Timer {
    var minute : Int = 30
    var minuteString : String = "30"
    var second : Int = 0
    var secondString : String = "00"
    lateinit var p : Player

    //JoinMap Join Überprüfung
    var hasJoined = false

    var m1 = minute
    var s1 = second
    fun startTimer(){
        CoroutineScope(Dispatchers.Default).launch {
            runTimer()
        }
    }
    fun add(minutes:Int?,seconds:Int?){
        if (minutes != null) {
            m1+= minutes
        }
        if (seconds != null) {
            s1+= seconds
        }
    }
    var pause = false
    fun pause(){
        pause = true
    }
    fun unpause(){
        pause = false
    }
    suspend fun runTimer() = coroutineScope{
        if (MinuteMap.contains(p.name)) m1 = MinuteMap[p.name]!!
        if (SecondMap.contains(p.name)) s1 = SecondMap[p.name]!!
        launch {
            while (projectstart){ delay(100) }
            repeat(((m1*60)+s1+60)*5){
                delay(1000)
                while (pause) delay(1000)
                //TIMER
                if(s1==0){
                    if (m1!=0){
                        m1--
                        s1 = 59
                    }
                }
                if(second>=1&&minute>=0) s1--
                minute = m1
                second = s1

                //0 VOR DIE ZAHLEN
                if (minute<10){
                    minuteString = "0".plus(minute.toString())
                } else minuteString = minute.toString()
                if (second<10){
                    secondString = "0".plus(second.toString())
                } else secondString = second.toString()
                if (second<0){
                    secondString="00"
                }
            }
        }
    }
}