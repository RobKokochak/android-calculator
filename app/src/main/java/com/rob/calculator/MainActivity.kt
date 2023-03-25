package com.rob.calculator

import android.os.Bundle
import android.os.PersistableBundle
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

        when (btnValue) {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "CE" -> {
                if (operand1 == result1 && operation == "") { // stops case of editing a previous equation result
                    resetCalc()
                    result1 = 0.0
                }
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
                    displayValue = operand1
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
                    displayValue = operand2
                }
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
            "√" -> {
                result1 = sqrt(operand1)
                displayValue = result1
                resetCalc()
                operand1 = result1
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
        if (btnValue == ".") displayFragment.changeTextProperties(BigDecimal(displayValue).setScale(8, RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString() + ".")
        else displayFragment.changeTextProperties(BigDecimal(displayValue).setScale(8, RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString())
    }
}