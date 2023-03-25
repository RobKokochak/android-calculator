package com.rob.calculator

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.rob.calculator.databinding.ActivityMainBinding
import java.lang.Double.isNaN
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), ButtonsFragment.ButtonsListener {

    private lateinit var binding: ActivityMainBinding

    private var operand1 = 0.0
    private var operand2 = 0.0
    private var operation = ""
    private var result1 = 0.0
    private var sign = 1 // 1 signifies positive, -1 negative
    private var point = false //false means working on left side of the point
    private var pointPlace = 1
    private var displayValue = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            operand1 = savedInstanceState.getDouble("operand1")
            operand2 = savedInstanceState.getDouble("operand2")
            operation = savedInstanceState.getString("operation", "")
            result1 = savedInstanceState.getDouble("result1")
            sign = savedInstanceState.getInt("sign")
            point = savedInstanceState.getBoolean("point")
            pointPlace = savedInstanceState.getInt("pointPlace")
            displayValue = savedInstanceState.getDouble("displayValue")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("operand1", operand1)
        outState.putDouble("operand2", operand2)
        outState.putString("operation", operation)
        outState.putDouble("result1", result1)
        outState.putInt("sign", sign)
        outState.putBoolean("point", point)
        outState.putInt("pointPlace", pointPlace)
        outState.putDouble("displayValue", displayValue)
    }

    override fun onButtonClick(btnValue: String) {
        val displayFragment = supportFragmentManager.findFragmentById(R.id.displayFragment) as DisplayFragment

        fun resetCalc() {
            operand1 = 0.0
            operand2 = 0.0
            operation = ""
            sign = 1
            pointPlace = 1
            point = false
        }

        fun updateOperand(operand : Double, btnValue: String): Double {
            var returnOperand : Double = operand
            if (sign == 1) { // positive value
                if (!point) { // left side of point
                    if (btnValue == "CE") returnOperand = floor(returnOperand / 10)
                    else returnOperand = returnOperand * 10 + btnValue.toDouble()
                }
                else { // right side of point
                    if (btnValue == "CE") {
                        val operandString : String = returnOperand.toString()
                        val operandStringMinus : String = operandString.substring(0, operandString.length - 1)
                        returnOperand = operandStringMinus.toDouble()
                        pointPlace -= 1
                    }
                    else {
                        returnOperand += btnValue.toDouble() / (10.0.pow(pointPlace))
                        pointPlace += 1
                    }
                }
            }
            else { // negative value
                if (!point) { // left side of point
                    if (btnValue == "CE") returnOperand = floor(returnOperand / 10)
                    else returnOperand = returnOperand * 10 - btnValue.toDouble()
                }
                else { // right side of point
                    if (btnValue == "CE") {
                        val operandString : String = returnOperand.toString()
                        val operandStringMinus : String = operandString.substring(0, operandString.length - 1)
                        returnOperand = operandStringMinus.toDouble()
                        pointPlace -= 1
                    }
                    else {
                        returnOperand -= btnValue.toDouble() / (10.0.pow(pointPlace))
                        pointPlace += 1
                    }
                }
            }
            displayValue = returnOperand
            return returnOperand
        }

        when (btnValue) {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "CE" -> {
                if (operand1 == result1 && operation == "") { // stops case of editing a previous equation result
                    resetCalc()
                    result1 = 0.0
                }
                // updating 1st operand
                if (operation == "") operand1 = updateOperand(operand1, btnValue)
                // updating 2nd operand
                else operand2 = updateOperand(operand2, btnValue)
            }
            "." -> {
                point = true
            }
            "negate" -> {
                sign *= -1
                if (operation == "") {
                    operand1 *= -1
                    displayValue = operand1
                }
                else {
                    operand2 *= -1
                    displayValue = operand2
                }
            }
            "+", "-", "*", "/", "%" -> {
                operation = btnValue
                sign = 1
                point = false
                pointPlace = 1
            }
            "√" -> { // crashes if a negative number
                result1 = sqrt(operand1)
                displayValue = result1
                resetCalc()
                if (isNaN(displayValue)) operand1 = 0.0
                else operand1 = result1
            }
            "=" -> {
                when (operation) {
                    "+" -> result1 = operand1 + operand2
                    "-" -> result1 = operand1 - operand2
                    "*" -> result1 = operand1 * operand2
                    "/" -> result1 = operand1 / operand2
                    "%" -> result1 = operand1 % operand2
                }
                displayValue = result1
                resetCalc()
                operand1 = result1
            }
            "C" -> {
                resetCalc()
                result1 = 0.0
                displayValue = 0.0
            }
        }
        if (isNaN(displayValue)) displayFragment.changeTextProperties(displayValue.toString())
        else {
            if (btnValue == ".") displayFragment.changeTextProperties(
                BigDecimal(displayValue).setScale(8, RoundingMode.HALF_EVEN)
                    .stripTrailingZeros().toPlainString() + "."
            )
            else displayFragment.changeTextProperties(
                BigDecimal(displayValue).setScale(8, RoundingMode.HALF_EVEN)
                    .stripTrailingZeros().toPlainString()
            )
        }
    }
}