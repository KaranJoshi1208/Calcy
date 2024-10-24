package com.karan.calcy.model

import java.util.EmptyStackException
import java.util.Stack
import kotlin.math.pow
import kotlin.math.sqrt

class ShuntYard(private val exp: String) {
    private var size = exp.length

    private fun value(op: String): Int {
        return when (op) {
            in "^√" -> 3
            in "/*%" -> 2
            in "+-" -> 1
            else -> 0                     // Unknown Operator
        }
    }

    private fun operate(
        a: Float,
        b: Float,
        op: Char,
        emptyA: Boolean = false,
        emptyB: Boolean = false
    ): Float {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> if(!emptyB) (a/b) else  throw IllegalArgumentException("Error")
            '^' -> a.pow(b)
            '√' -> sqrt(b)
            '%' -> {
                if(emptyA) (b / 100)
                else if(emptyB) (a / 100)
                else (b * a / 100)
            }
            else -> throw IllegalArgumentException("Operator '$op' not Recognised")
        }
    }

    private fun toPostfix(): List<String> {
        val rpn = mutableListOf<String>()
        val opStk = Stack<Char>()
        var i = 0
        while (i < size) {
            val c = exp[i]
            when {
                c.isDigit() -> {
                    val num = StringBuilder()
                    while (i < size && (exp[i].isDigit() || exp[i] == '.')) {
                        num.append(exp[i])
                        i++
                    }
                    rpn.add(num.toString())
                    i--
                }

                c == '(' -> opStk.push(c)
                c == ')' -> {
                    while (opStk.isNotEmpty() && opStk.peek() != '(') {
                        rpn.add(opStk.pop().toString())
                    }
                    opStk.pop().toString()  // Pop the '('
                }

                c in "+-/*^√%" -> {
                    while (opStk.isNotEmpty() && value(
                            opStk.peek().toString()
                        ) >= value(c.toString())
                    ) {
                        rpn.add(opStk.pop().toString())
                    }
                    opStk.push(c)
                }

            }
            i++
        }
        while (opStk.isNotEmpty()) {
            rpn.add(opStk.pop().toString())
        }

        return rpn
    }

    private fun evaluateRpn(rpn: List<String>): Float {
        val ans = Stack<Float>()
        for (token in rpn) {
            val digit = token.toFloatOrNull()
            when {
                digit != null -> ans.push(digit)
                token in "+-/*^√%" -> {
                    var emptyB = false
                    val b: Float = try {
                        ans.pop()
                    } catch (e: EmptyStackException) {
                        emptyB = true
                        0f
                    }
                    var emptyA = false
                    var a: Float = try {
                        ans.pop()
                    } catch (e: EmptyStackException) {
                        emptyA = true
                        0f
                    }
                    ans.push(operate(a, b, token[0], emptyA, emptyB))
                }
            }
        }
        return ans.pop()
    }

    fun evaluate(): Float {
        return evaluateRpn(toPostfix())
    }

}