package com.danirsena.meuprimeiroappandroid.broadcastRecevier

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

// lembrando que para funcionar, precisa registrar ou no android manifest como receiver ou
class LowBatteryBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "A bateria está acabando!", Toast.LENGTH_LONG).show()
    }
}