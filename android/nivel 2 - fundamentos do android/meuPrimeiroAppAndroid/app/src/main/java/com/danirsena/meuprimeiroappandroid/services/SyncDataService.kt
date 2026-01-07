package com.danirsena.meuprimeiroappandroid.services

import android.app.Service
import android.content.Intent
import android.os.IBinder

class SyncDataService: Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            for (i in 1..100) {
                println("O progresso está em $i%")
                Thread.sleep(500)
            }
            stopSelf()
        }.start()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}