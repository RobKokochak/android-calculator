package com.rob.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rob.calculator.databinding.FragmentDisplayBinding
import java.math.BigDecimal
import java.math.RoundingMode

class DisplayFragment : Fragment() {

    private lateinit var binding: FragmentDisplayBinding
    private var displayValue = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        // Inflate the layout for this fragment
        binding = FragmentDisplayBinding.inflate(inflater, container, false)

        if (savedInstanceState != null) {
            displayValue = savedInstanceState.getDouble("displayValue")
            binding.tvDisplay.text = BigDecimal(displayValue).setScale(8, RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString()
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        displayValue = binding.tvDisplay.text.toString().toDouble()
        outState.putDouble("displayValue", displayValue)
    }

    fun changeTextProperties(btnVal : String) {
        binding.tvDisplay.text = btnVal
    }
}