package com.karan.calcy.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.karan.calcy.model.CalculatorActions
import com.karan.calcy.model.CalculatorState

class CalViewModel(application: Application) : AndroidViewModel(application) {

    var state by mutableStateOf(CalculatorState())
        private set

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }

    fun onAction(action: CalculatorActions) {

        /*
        Damn , learned a new thing about "when(){}"
        1. That "is" in "when" block actually casting the "action" object into is subclasses
         */

//        val cal : CalculatorActions.Operator = action as CalculatorActions.Operator
//        Log.d("onAction")

        when (action) {
            is CalculatorActions.Number -> enterNumber(action.num)
            is CalculatorActions.Calculate -> calculate()
            is CalculatorActions.AllClear -> state = CalculatorState()
            is CalculatorActions.Decimal -> enterDecimal()
            is CalculatorActions.Clear -> delete()
            is CalculatorActions.Operator -> enterOperation(action)
//            is CalculatorActions.Operation -> TODO()
        }
    }


    // CAN IMPROVISE HERE >>> {REMOVE THIS REDUNDANT FUNCTION, WHY??}
    private fun enterOperation(op: CalculatorActions.Operator) {
        if (state.op != null && state.num2.isNotBlank()) {
            calculate()
        }
        state.op = op
    }

    private fun delete() {
        when {
            state.num2.isNotBlank() -> state.num2.dropLast(1)

            (state.op != null) -> state.op = null

            state.num1.isNotBlank() -> state.num1.dropLast(1)
        }
    }

    private fun enterDecimal() {
        if (state.op != null && (!state.num1.contains("."))) {
            state.num1 += "."
            return
        }

        // removing this condition "state.num2.isNotBlank())",
        // I think it's redundant , but it could also become a potential bug
        if (!state.num2.contains(".")) {
            state.num2 += "."
            return
        }
    }


    private fun enterNumber(num: Int) {
        if (state.op == null) {
            if (state.num1.length < MAX_NUM_LENGTH) {
                state.num1 += num
            }
        } else {
            if (state.num2.length < MAX_NUM_LENGTH) {
                state.num2 += num
            }
        }
    }

    private fun calculate() {
        val n1 = state.num1.toDoubleOrNull()
        val n2 = state.num2.toDoubleOrNull()
        if (n1 != null && n2 != null) {
            val result = when (state.op) {
                is CalculatorActions.Operator.Add -> n1 + n2
                is CalculatorActions.Operator.Subtract  -> n1 - n2
                is CalculatorActions.Operator.Multiply  -> n1 * n2
                is CalculatorActions.Operator.Divide  -> n1 / n2
                else -> {
                    return
                }
            }
            state.apply {
                num1 = result.toString().take(15)
                num2 = ""
                op = null
            }
        }
    }
}