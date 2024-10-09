package com.karan.calcy.model

sealed class CalculatorOperations(val op : String) {
    data object Add : CalculatorOperations("+")
    data object Subtract : CalculatorOperations("-")
    data object Multiply : CalculatorOperations("*")
    data object Divide : CalculatorOperations("/")
    data object Sine : CalculatorOperations("sin")
}