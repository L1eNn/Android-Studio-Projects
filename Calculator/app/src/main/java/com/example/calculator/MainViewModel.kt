package com.example.calculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var _symbols = MutableLiveData<String>()
    var symbols : LiveData<String> = _symbols

    fun onNumberPressed(resultText: String, number: String) { // Нажатие на число
        var result = resultText
        var isOperatorLast = false

        if (result == "0" ) {
            result = number
        } else if (result.isNotEmpty()) {
            when(result.last()) {
                '+' -> isOperatorLast = true
                '-' -> isOperatorLast = true
                'x' -> isOperatorLast = true
                '/' -> isOperatorLast = true
            }

            if (isOperatorLast) {
                result += " ${number}"
            } else {
                result += number
            }
        } else {
            result += number
        }

        _symbols.value = result
    }

    fun onNegativePressed(resultText: String) {
        var text = resultText
        val definedExpression = defineExpression(resultText)
        var checkedNum = ""

        if (resultText.isNotEmpty()) {
            if (definedExpression.secondNumberString.isNotEmpty() && definedExpression.secondNumberString.toDouble() != 0.0) {
                var num = definedExpression.secondNumberString.toDouble()
                num = num * -num / num
                checkedNum = checkDoubleToInt(num)
                text = "${definedExpression.firstNumberString} ${definedExpression.operator} ${checkedNum}"
            } else if (definedExpression.operator.isNotEmpty()) {
                Unit
            } else if (definedExpression.firstNumberString.isNotEmpty() && definedExpression.firstNumberString.toDouble() != 0.0) {
                var num = definedExpression.firstNumberString.toDouble()
                num = num * -num / num
                checkedNum = checkDoubleToInt(num)
                text = checkedNum
            }
        }

        _symbols.value = text
    }

    fun onDotPressed(resultText: String) {
        var text = resultText
        val definedExpression = defineExpression(text)

        if (text.isNotEmpty()) {
            if (definedExpression.secondNumberString.isNotEmpty()) {
                text += '.'
            } else if (definedExpression.operator.isNotEmpty()) {
                Unit
            } else if (definedExpression.firstNumberString.isNotEmpty()) {
                text += '.'
            }
        }

        _symbols.value = text
    }

    fun onSqrPressed(resultText: String) {
        val definedExpression = defineExpression(resultText)
        var result = resultText
        var totalResult = ""

        if (definedExpression.secondNumberString.isNotEmpty()) {
            val secondNum = definedExpression.secondNumberString.toDouble()
            val secondSqrNum = secondNum * secondNum
            totalResult = checkDoubleToInt(secondSqrNum)
            result = "${definedExpression.firstNumberString} ${definedExpression.operator} ${totalResult}"
        } else if (definedExpression.operator.isNotEmpty()) {
            Unit
        } else if (definedExpression.firstNumberString.isNotEmpty()) {
            val firstNum = definedExpression.firstNumberString.toDouble()
            val firstSqrNum = firstNum * firstNum
            totalResult = checkDoubleToInt(firstSqrNum)
            result = totalResult
        }

        _symbols.value = result
    }

    fun onPercentPressed(resultText: String) { // Нажатие на процент
        var text = resultText
        var isHasOperator = false
        var isLastOperator = false

        if (text.isNotEmpty()) {
            text.forEach { symbol ->
                when(symbol) {
                    '+' -> {
                        isHasOperator = true
                        if (text.last() == symbol) {
                            isLastOperator = true
                        }
                    }
                    '-' -> {
                        isHasOperator = true
                        if (text.last() == symbol) {
                            isLastOperator = true
                        }
                    }
                    'x' -> {
                        isHasOperator = true
                        if (text.last() == symbol) {
                            isLastOperator = true
                        }
                    }
                    '/' -> {
                        isHasOperator = true
                        if (text.last() == symbol) {
                            isLastOperator = true
                        }
                    }
                }
            }

            if (!isLastOperator) {
                if (isHasOperator) {
                    val definedExpression = defineExpression(text)
                    var newText = ""
                    var percentNumber = 0f
                    var totalNumber = 0f

                    when(definedExpression.operator) {
                        "+" -> {
                            percentNumber = definedExpression.secondNumberString.toFloat() / 100
                            totalNumber = definedExpression.firstNumberString.toFloat() * percentNumber
                            newText =
                                "${definedExpression.firstNumberString} + ${totalNumber}"
                        }
                        "-" -> {
                            percentNumber = definedExpression.secondNumberString.toFloat() / 100
                            totalNumber = definedExpression.firstNumberString.toFloat() * percentNumber
                            newText =
                                "${definedExpression.firstNumberString} - ${totalNumber}"
                        }
                        "x" -> {
                            percentNumber = definedExpression.secondNumberString.toFloat() / 100
                            newText =
                                "${definedExpression.firstNumberString} x ${percentNumber}"
                        }
                        "/" -> {
                            percentNumber = definedExpression.secondNumberString.toFloat() / 100
                            newText =
                                "${definedExpression.firstNumberString} / ${percentNumber}"
                        }
                    }
                    text = newText
                } else {
                    text = "0"
                }
            }
        }

        _symbols.value = text
    }

    fun onOperatorPressed(resultText: String, resultOperator: String) { // Нажатие на оператор
        var text = ""
        val definedExpression = defineExpression(resultText)

        if (resultText.isNotEmpty()) {
            if (definedExpression.secondNumberString.isNotEmpty()) {
                onEqualPressed(resultText)
                text = _symbols.value.toString()
            } else {
                text = definedExpression.firstNumberString
            }

            text += " $resultOperator"
        }

        _symbols.value = text
    }

    fun onEqualPressed(resultText : String) { // Нажатие на равно

        var totalResult = ""
        var doubleResult = 0.0

        val definedExpression = defineExpression(resultText)
        val firstNumberString = definedExpression.firstNumberString
        val secondNumberString = definedExpression.secondNumberString
        val operator = definedExpression.operator

        if (resultText.isNotEmpty() && secondNumberString.isNotEmpty()) {
            val firstNumberDouble = firstNumberString.toDouble()
            val secondNumberDouble = secondNumberString.toDouble()

            when (operator) {
                "+" -> {
                    doubleResult = firstNumberDouble + secondNumberDouble
                    totalResult = checkDoubleToInt(doubleResult)
                }
                "-" -> {
                    doubleResult = firstNumberDouble - secondNumberDouble
                    totalResult = checkDoubleToInt(doubleResult)
                }
                "x" -> {
                    doubleResult = firstNumberDouble * secondNumberDouble
                    totalResult = checkDoubleToInt(doubleResult)
                }
                "/" -> {
                    if (secondNumberString == "0") {
                        totalResult = ""
                    } else {
                        doubleResult = firstNumberDouble / secondNumberDouble
                        totalResult = checkDoubleToInt(doubleResult)
                    }
                }
            }
        }
        _symbols.value = totalResult
    }

    fun onClearPressed() { // Нажатие на кнопку очистить " C "
        _symbols.value = ""
    }

    private fun defineExpression(resultText: String) : DefinedExpression { // Функция определяющия первое число, оператор и второе число
        var whichNumber = 1
        var firstNumberString = ""
        var secondNumberString = ""
        var operator = ""

        resultText.forEach { symbol ->
            if (symbol == ' ') {
                whichNumber += 1
            } else {
                if (whichNumber == 1) {
                    firstNumberString += symbol
                } else if (whichNumber == 2) {
                    operator += symbol
                } else {
                    secondNumberString += symbol
                }
            }
        }

        val definedExpression = DefinedExpression(
            firstNumberString = firstNumberString,
            secondNumberString = secondNumberString,
            operator = operator
        )

        return definedExpression
    }

    private fun checkDoubleToInt(number: Double) : String {
        val stringNumber = number.toString()
        var beforeDotStringNumber = ""
        var hasDot = false
        var afterDotSum = 0

        stringNumber.forEach { symbol ->
            if (hasDot) {
                afterDotSum += symbol.digitToInt()
            }
            if (symbol == '.') {
                hasDot = true
            }
            if (!hasDot){
                beforeDotStringNumber += symbol
            }
        }

         if (afterDotSum == 0) {
             return beforeDotStringNumber
         } else {
             return stringNumber
         }
    }
}