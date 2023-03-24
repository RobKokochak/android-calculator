package com.rob.calculator

import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.rob.calculator.databinding.ActivityMainBinding
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), ButtonsFragment.ButtonsListener {

    private lateinit var binding: ActivityMainBinding
    private var operand1 = 0.0
    private var operand2 = 0.0
    private var operation = ""
    var result1 = 0.0



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
        }

        when (btnValue) {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> {
                if (operation == "") {
                    operand1 = operand1 * 10 + btnValue.toInt()
                    displayFragment.changeTextProperties(operand1.toString())
                }
                else {
                    operand2 = operand2 * 10 + btnValue.toInt()
                    displayFragment.changeTextProperties(operand2.toString())
                }
            }
            // implement plusMinus and point buttons

            "+", "-", "*", "/", "%" -> operation = btnValue
            "√" -> {
                result1 = sqrt(operand1)
                displayFragment.changeTextProperties(result1.toString())
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
                displayFragment.changeTextProperties(result1.toString())
                resetCalc()
                operand1 = result1
            }
            "CE" -> {
                // clears the most recent entry (the last digit you just entered)
            }
            "C" -> {
                resetCalc()
                displayFragment.changeTextProperties("0")
            }
        }
    }
}