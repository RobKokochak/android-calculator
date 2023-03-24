package com.rob.calculator

import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.rob.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ButtonsFragment.ButtonsListener {

    private lateinit var binding: ActivityMainBinding
    private var operand1 = 0
    private var operand2 = 0
    var operation = ""
    var currentValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onButtonClick(btnValue: String) {
        Log.i("MainActivity", "in onButtonClick with value " + btnValue)
        val displayFragment = supportFragmentManager.findFragmentById(R.id.displayFragment) as DisplayFragment

        when (btnValue) {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> currentValue = currentValue * 10 + btnValue.toInt()
            "+", "-", "*", "/", "%", "√" -> operation = btnValue

        }
        if (operation == "") operand1 = currentValue
        else operand2 = currentValue

        displayFragment.changeTextProperties(currentValue.toString())
    }
}