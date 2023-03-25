package com.rob.calculator

import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.rob.calculator.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.floor
import kotlin.math.sqrt
import kotlin.math.pow

class MainActivity : AppCompatActivity(), ButtonsFragment.ButtonsListener {

    private lateinit var binding: ActivityMainBinding
    private var operand1 = 0.0
    private var operand2 = 0.0
    private var operation = ""
    var result1 = 0.0
    var sign = 1
    var point = false //false means working on left side of the point
    var pointPlace = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onButtonClick(btnValue: String) {
        Log.i("MainActivity", "in onButtonClick with value " + btnValue)
        val displayFragment = supportFragmentManager.findFragmentById(R.id.displayFragment) as DisplayFragment

        fun resetCalc() {
            operand1 = 0.0
            operand2 = 0.0
            operation = ""
            sign = 1
            pointPlace = 1
            point = false
        }

        when (btnValue) {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "CE" -> {
                if (operation == "") { //working on 1st operand
                    if (sign == 1) { // positive value
                        if (!point) { // left side of point
                            if (btnValue == "CE") operand1 = floor(operand1 / 10)
                            else operand1 = operand1 * 10 + btnValue.toDouble()
                        }
                        else { // right side of point
                            if (btnValue == "CE") {
                                val operandString : String = operand1.toString()
                                val operandStringMinus : String = operandString.substring(0, operandString.length - 1)
                                operand1 = operandStringMinus.toDouble()
                                pointPlace -= 1
                            }
                            else {
                                operand1 += btnValue.toDouble() / (10.0.pow(pointPlace))
                                pointPlace += 1
                            }
                        }
                    }
                    else { // negative value
                        if (!point) { // left side of point
                            if (btnValue == "CE") operand1 = floor(operand1 / 10)
                            else operand1 = operand1 * 10 - btnValue.toDouble()
                        }
                        else { // right side of point
                            if (btnValue == "CE") {
                                val operandString : String = operand1.toString()
                                val operandStringMinus : String = operandString.substring(0, operandString.length - 1)
                                operand1 = operandStringMinus.toDouble()
                                pointPlace -= 1
                            }
                            else {
                                operand1 -= btnValue.toDouble() / (10.0.pow(pointPlace))
                                pointPlace += 1
                            }
                        }
                    }
                    displayFragment.changeTextProperties(BigDecimal(operand1).setScale(9, RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString())
                }
                else { // working on 2nd operand
                    if (sign == 1) { // positive value
                        if (!point) { // left side of point
                            if (btnValue == "CE") operand2 = floor(operand2 / 10)
                            else operand2 = operand2 * 10 + btnValue.toDouble()
                        }
                        else { // right side of point
                            if (btnValue == "CE") {
                                val operandString : String = operand2.toString()
                                val operandStringMinus : String = operandString.substring(0, operandString.length - 1)
                                operand2 = operandStringMinus.toDouble()
                                pointPlace -= 1
                            }
                            else {
                                operand2 += btnValue.toDouble() / (10.0.pow(pointPlace))
                                pointPlace += 1
                            }
                        }
                    }
                    else { // negative value
                        if (!point) { // left side of point
                            if (btnValue == "CE") operand2 = floor(operand2 / 10)
                            else operand2 = operand2 * 10 - btnValue.toDouble()
                        }
                        else { // right side of point
                            if (btnValue == "CE") {
                                val operandString : String = operand2.toString()
                                val operandStringMinus : String = operandString.substring(0, operandString.length - 1)
                                operand2 = operandStringMinus.toDouble()
                                pointPlace -= 1
                            }
                            else {
                                operand2 -= btnValue.toDouble() / (10.0.pow(pointPlace))
                                pointPlace += 1
                            }
                        }
                    }
                    displayFragment.changeTextProperties(BigDecimal(operand2).setScale(9, RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString())
                }
            }
            "." -> {
                point = true
            }
            "negate" -> {
                sign *= -1
                if (operation == "") {
                    operand1 *= -1
                    displayFragment.changeTextProperties(BigDecimal(operand1).setScale(9, RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString())
                }
                else {
                    operand2 *= -1
                    displayFragment.changeTextProperties(BigDecimal(operand2).setScale(9, RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString())
                }
            }
            "+", "-", "*", "/", "%" -> {
                operation = btnValue
                sign = 1
                point = false
                pointPlace = 1
            }
            "√" -> {
                result1 = sqrt(operand1)
                displayFragment.changeTextProperties(BigDecimal(result1).setScale(9, RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString())
                resetCalc()
            }
            "=" -> {
                when (operation) {
                    "+" -> result1 = operand1 + operand2
                    "-" -> result1 = operand1 - operand2
                    "*" -> result1 = operand1 * operand2
                    "/" -> result1 = operand1 / operand2
                    "%" -> result1 = operand1 % operand2
                }
                val formattedResult = BigDecimal(result1).setScale(8, RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString()
                displayFragment.changeTextProperties(formattedResult)
                resetCalc()
            }
            // how to get it so that it saves the answer if I'm immediately operating on it,
            // but not if I try to enter more numbers on top of it? In that case,
            // it must reset. like iphone calculator
            "C" -> {
                resetCalc()
                displayFragment.changeTextProperties("0")
            }
        }
    }
}