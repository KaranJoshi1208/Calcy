package com.karan.calcy.model

import com.karan.calcy.model.CalculatorActions.Operator

data class CalculatorState(
    var num1 : String = "0",                           //  DEFAULT VALUE COULD BE "0"
    var num2 : String = "",
    var op : Operator? = null
)
