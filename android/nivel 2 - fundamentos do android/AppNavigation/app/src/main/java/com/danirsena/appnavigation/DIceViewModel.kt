package com.danirsena.appnavigation

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

data class DiceUiState(
    @DrawableRes val rolledDiceImage1: Int? = null,
    @DrawableRes val rolledDiceImage2: Int? = null,
    @DrawableRes val rolledDiceImage3: Int? = null,
    val numberOfRolls: Int = 0,
)

class DiceViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DiceUiState())
    val uiState: StateFlow<DiceUiState> = _uiState.asStateFlow()

    fun rollDice() {
        _uiState.update { currentState ->
            currentState.copy(
                rolledDiceImage1 = getDiceImageResource(Random.nextInt(1, 7)),
                rolledDiceImage2 = getDiceImageResource(Random.nextInt(1, 7)),
                rolledDiceImage3 = getDiceImageResource(Random.nextInt(1, 7)),
                numberOfRolls = currentState.numberOfRolls + 1
            )
        }
    }

    private fun getDiceImageResource(diceValue: Int): Int {
        return when (diceValue) {
            1 -> R.drawable.dado_face_1
            2 -> R.drawable.dado_face_2
            3 -> R.drawable.dado_face_3
            4 -> R.drawable.dado_face_4
            5 -> R.drawable.dado_face_5
            6 -> R.drawable.dado_face_6
            else -> R.drawable.dado_face_1
        }
    }
}