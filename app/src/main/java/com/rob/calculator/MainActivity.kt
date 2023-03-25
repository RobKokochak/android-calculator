package com.rob.calculator

import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.rob.calculator.databinding.ActivityMainBinding
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
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> {
                Log.d("MainActivity", "The value of point is: " + point);
                if (operation == "") { //working on 1st operand
                    if (sign == 1) { // positive value
                        if (!point) operand1 = operand1 * 10 + btnValue.toDouble()
                        else {
                            operand1 += btnValue.toDouble() / (10.0.pow(pointPlace))
                            pointPlace += 1
                        }
                    }
                    else { // negative value
                        if (!point) operand1 = operand1 * 10 - btnValue.toDouble()
                        else {
                            operand1 -= btnValue.toDouble() / (10.0.pow(pointPlace))
                            pointPlace += 1
                        }
                    }
                    displayFragment.changeTextProperties(operand1.toString())
                }
                else { // working on 2nd operand
                    if (sign == 1) { // positive value
                        if (!point) operand2 = operand2 * 10 + btnValue.toDouble()
                        else {
                            operand2 += btnValue.toDouble() / (10.0.pow(pointPlace))
                            pointPlace += 1
                        }
                    }
                    else { // negative value
                        if (!point) operand2 = operand2 * 10 - btnValue.toDouble()
                        else {
                            operand2 -= btnValue.toDouble() / (10.0.pow(pointPlace))
                            pointPlace += 1
                        }
                    }
                    displayFragment.changeTextProperties(operand2.toString())
                }
            }
            "." -> {
                point = true
            }
            "negate" -> {
                sign *= -1
                if (operation == "") {
                    operand1 *= -1
                    displayFragment.changeTextProperties(operand1.toString())
                }
                else {
                    operand2 *= -1
                    displayFragment.changeTextProperties(operand2.toString())
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
                displayFragment.changeTextProperties(result1.toString())
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
                displayFragment.changeTextProperties(result1.toString())
                resetCalc()
            }
            // how to get it so that it saves the answer if I'm immediately operating on it,
            // but not if I try to enter more numbers on top of it? In that case,
            // it must reset. like iphone calculator
            "CE" -> {
                // clears the most recent entry (the last digit you just entered)
            }
            "C" -> {
                resetCalc()
                displayFragment.changeTextProperties("0.0")
            }
        }
    }
}