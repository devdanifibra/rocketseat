package com.danirsena.appnavigation

import android.os.Bundle
import android.view.inputmethod.InputBinding
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import com.danirsena.appnavigation.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //construindo o menu
    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcvMainContainer) as? NavHostFragment
        navHostFragment?.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val diceModel: DiceViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                diceModel.uiState.collect {
                    //id do drawable de dado
                    //binding.diceImage.setImageResource()
                }
            }
        }

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //botão para rolar dado
        binding.btnRollDice.setOnClickListener {
            diceModel.rollDice()
        }

        // criando gráfico de navegação, controlado pelo navController
        binding.btnNextFragment.setOnClickListener {
            navController?.currentDestination?.id.let {
                when (it) {
                    R.id.firstFragment -> {
                        navController?.navigate(
                            R.id.ACFirstToSecond,
                            bundleOf("first_argument" to arrayOf("1", "2", "3"))
                        )
                        binding.btnNextFragment.text = getString(R.string.voltar)
                    }

                    R.id.secondFragment -> {
                        navController?.popBackStack()
                        binding.btnNextFragment.text = getString(R.string.ir_para_proxima_tela)
                    }
                }
            }

        }
    }
}