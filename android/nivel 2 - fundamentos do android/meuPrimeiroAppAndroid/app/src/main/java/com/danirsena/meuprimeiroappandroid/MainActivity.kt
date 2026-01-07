package com.danirsena.meuprimeiroappandroid

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.danirsena.meuprimeiroappandroid.databinding.ActivityMainBinding
import androidx.core.net.toUri
import com.danirsena.meuprimeiroappandroid.broadcastRecevier.LowBatteryBroadcastReceiver
import com.danirsena.meuprimeiroappandroid.services.SyncDataService

class Functions {
    fun startMusic() = Log.d("MainActivity2", "Start music")

    fun stopMusic() = Log.d("MainActivity2", "Stop music")

    fun deleteTempFiles() = Log.d("MainActivity2", "Delete temp files")

    fun createTempFiles() = Log.d("MainActivity2", "Create temp files")

    fun startlongRunningTask() = Log.d("MainActivity2", "Start long running task")

    fun stoplongRunningTask() = Log.d("MainActivity2", "Stop long running task")
    fun setupLayout() = Log.d("MainActivity2", "Setup layout")

}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val lowBatteryBroadcastReceiver = LowBatteryBroadcastReceiver()
    private val intentFilter = IntentFilter("android.intent.action.BATTERY_LOW")

    private val permissonOfNotification = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        Toast.makeText(this, "Permissão de notificação $it", Toast.LENGTH_SHORT).show()
    }

    private val functions by lazy { Functions() }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge() //expande o conteúdo para o máximo da tela

        //manda uma mensagem para o usuário
        showToast(this)

        // usa context para acessar recursos do sistema
//        val url = "https://www.pichau.com.br"
//        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
//        startActivity(intent)

        //Evite vazamentos de memória
        //val myClass: MyClass = MyClass(this)
        // se for assim, pode acontecer do contexto ficar retido depois que ela excutar transição apra outra. então pode causar vazamento de memória. AO inves disso, pode passar o valor de
        val myNewClass: MyClass = MyClass(applicationContext)
        // assim, não depende do ciclo de vida da activity, mas da aplicação. Pode usar também o weak reference, que mexe lá no


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding.titulo) {
            this?.text = "Meu primeiro App Android!"
            this?.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        }

        binding.button?.setOnClickListener {
            showNotificationGoToActivity2()
            //startActivity(Intent(this, MainActivity2::class.java))
        }

        supportFragmentManager.beginTransaction().add(R.id.mainContainer,BlankFragment.newInstance(
            name = "Yuri Alpha",
            age = 359,
            gender = true
        )).commit()

        //registrar o receiver
        registerReceiver(lowBatteryBroadcastReceiver, intentFilter)

        //inicia o service
        val intent = Intent(this, SyncDataService::class.java)
        startService(intent)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissonOfNotification.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    fun showNotificationGoToActivity2() {

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Toast.makeText(this, "Permissão de notificação negada", Toast.LENGTH_SHORT).show()
            return
        }

        val intentGoToActivity2 = Intent(ACTION_VIEW, "https://danirsena-portfolio.vercel.app".toUri())
            //Intent(this, MainActivity2::class.java)

            startActivity(Intent(this, MainActivity2::class.java))

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intentGoToActivity2,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setContentTitle("Vá para a Activity 2")
            .setContentText("Clique para ir para a Activity 2")
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(NotificationManager::class.java)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = android.app.NotificationChannel(
                "CHANNEL_ID",
                "Go to Activity 2 Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(1, notification.build())
    }
    fun showToast(context: Context) {
        Toast.makeText(context, "Hello, dear", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity2", "onStart")
        functions.startlongRunningTask()
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity2", "onResume")
        functions.createTempFiles()
        functions.startMusic()
    }

    //tá indo pro segundo plano
    override fun onPause() {
        super.onPause()
        Log.d("MainActivity2", "onPause")
        functions.stopMusic()
        //functions.startAnimation()
    }

    // não está mais na tela
    override fun onStop() {
        super.onStop()
        Log.d("MainActivity2", "onStop")
        functions.stoplongRunningTask()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity2", "onDestroy")
        functions.deleteTempFiles()
        unregisterReceiver(lowBatteryBroadcastReceiver)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MainActivity2", "onRestart")
        //functions.restartlongRunningTask()
    }
}