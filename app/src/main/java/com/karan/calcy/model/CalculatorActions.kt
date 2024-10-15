package com.karan.calcy.model

sealed class CalculatorActions {
    data class Number(val num : Int) : CalculatorActions()
    object AllClear : CalculatorActions()
    object Clear : CalculatorActions()
    object Decimal : CalculatorActions()
    object Calculate : CalculatorActions()

//    data class Operation(val op : Operator) : CalculatorActions()

    sealed class Operator(val symbol : String) : CalculatorActions() {
        object Add : Operator("+")
        object Subtract : Operator("-")
        object Multiply : Operator("*")
        object Divide : Operator("/")
        object Opening : Operator("(")
        object Closing : Operator(")")
        object Sine : Operator("sin")
    }
}