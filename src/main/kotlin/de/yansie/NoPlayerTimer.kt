package de.yansie


import kotlinx.coroutines.*


class NoPlayerTimer {
    var minute : Int = 30
    var second : Int = 0

    var hasJoined = false


    var m1 = minute
    var s1 = second
    fun startTimer(){
        CoroutineScope(Dispatchers.Default).launch {
            runTimer()
        }
    }
    var delay = false
    fun delayOn(){
        delay = true
    }
    fun delayOff(){
        delay = false
    }
    suspend fun runTimer() = coroutineScope{
        launch {
            repeat(((m1*60)+s1+60)*5){
                delay(1000)
                while (delay) delay(1000)
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
            }
        }
    }
}