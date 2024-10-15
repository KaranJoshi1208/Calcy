package com.karan.calcy.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.karan.calcy.model.CalculatorActions
import com.karan.calcy.model.CalculatorState
import com.karan.calcy.model.ShuntYard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalViewModel(application: Application) : AndroidViewModel(application) {

    var exp = mutableStateOf(TextFieldValue(""))
    var ans = mutableStateOf("0")

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }

    fun onAction(action: CalculatorActions) {

        /*
        Damn , learned a new thing about "when(){}"
        1. That "is" in "when" block actually casting the "action" object into is subclasses
         */


        when (action) {
            is CalculatorActions.Number -> {
                exp.value = editTextField(exp, action.num.toString())
            }

            is CalculatorActions.Calculate -> {
                try {
                    viewModelScope.launch(Dispatchers.Main) {
                        ans.value = ShuntYard(exp.value.text).evaluate().toString()
                    }
                } catch (e: Exception) {
                    ans.value = "Error \uD83D\uDC80"
                }
            }

            is CalculatorActions.AllClear -> {
                exp.value = TextFieldValue()
                ans.value = "0"
            }

            is CalculatorActions.Decimal -> {
                exp.value = editTextField(exp, ".")
            }

            is CalculatorActions.Clear -> {
                exp.value = TextFieldValue(
                    text = exp.value.text.dropLast(1)
                )
            }

            is CalculatorActions.Operator -> {
                exp.value = editTextField(exp, action.symbol.toString())
            }
        }
    }

    fun editTextField(expState: MutableState<TextFieldValue>, content: String) : TextFieldValue {

        val exp = expState.value.text
        val cursorAt = expState.value.selection.start

        val newExp = exp.substring(0, cursorAt) + content + exp.substring(cursorAt)

        return TextFieldValue(
            text = newExp,
            selection = TextRange(cursorAt + content.length)
        )
    }
}

