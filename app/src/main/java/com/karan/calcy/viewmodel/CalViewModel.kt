package com.karan.calcy.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.karan.calcy.model.CalculatorActions
import com.karan.calcy.model.CalculatorState

class CalViewModel(application: Application) : AndroidViewModel(application) {

    // nip -> as in "Snippet"
    var nip = CalculatorState()
        private set
    
    var exp by mutableStateOf("")

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

        when(action) {
            is CalculatorActions.Number -> enterNumber(action.num)
            is CalculatorActions.Calculate -> calculate()
            is CalculatorActions.AllClear -> nip = CalculatorState()
            is CalculatorActions.Decimal -> enterDecimal()
            is CalculatorActions.Clear -> delete()
            is CalculatorActions.Operator -> enterOperation(action)
//            is CalculatorActions.Operation -> TODO()
        }

        // here , do it here , fetch the nip and print exp
        exp = (action as CalculatorActions.Operator).symbol

    }


    // CAN IMPROVISE HERE >>> {REMOVE THIS REDUNDANT FUNCTION, WHY??}
    private fun enterOperation(op: CalculatorActions.Operator) {
        if (nip.op != null && nip.num2.isNotBlank()) {
            calculate()
        }
        nip.op = op
    }

    private fun delete() {
        when {
            nip.num2.isNotBlank() -> nip.num2.dropLast(1)

            (nip.op != null) -> nip.op = null

            nip.num1.isNotBlank() -> nip.num1.dropLast(1)
        }
    }

    private fun enterDecimal() {
        if (nip.op != null && (!nip.num1.contains("."))) {
            nip.num1 += "."
            return
        }

        // removing this condition "state.num2.isNotBlank())",
        // I think it's redundant , but it could also become a potential bug
        if (!nip.num2.contains(".")) {
            nip.num2 += "."
            return
        }
    }


    private fun enterNumber(num: Int) {
        if (nip.op == null) {
            if (nip.num1.length < MAX_NUM_LENGTH) {
                nip.num1 += num
            }
        } else {
            if (nip.num2.length < MAX_NUM_LENGTH) {
                nip.num2 += num
            }
        }
    }

    private fun calculate() {
        val n1 = nip.num1.toDoubleOrNull()
        val n2 = nip.num2.toDoubleOrNull()
        if (n1 != null && n2 != null) {
            val result = when (nip.op) {
                is CalculatorActions.Operator.Add -> n1 + n2
                is CalculatorActions.Operator.Subtract  -> n1 - n2
                is CalculatorActions.Operator.Multiply  -> n1 * n2
                is CalculatorActions.Operator.Divide  -> n1 / n2
                else -> {
                    return
                }
            }
            nip.apply {
                num1 = result.toString().take(15)
                num2 = ""
                op = null
            }
        }
    }
}