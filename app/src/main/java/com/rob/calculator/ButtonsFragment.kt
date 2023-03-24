package com.rob.calculator

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rob.calculator.databinding.FragmentButtonsBinding

class ButtonsFragment : Fragment() {

    private lateinit var binding : FragmentButtonsBinding

    private var btnValue : String = ""

    var activityCallback : ButtonsFragment.ButtonsListener? = null

    interface ButtonsListener {
        fun onButtonClick(btnValue : String) // invoking onButtonClick in MainActivity
    }

    override fun onAttach(context : Context) {
        super.onAttach(context)
        try {
            activityCallback = context as ButtonsListener
        }
        catch (e : ClassCastException) {
            throw ClassCastException(context.toString() + " must implement ToolbarListener")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }// onCreate end

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentButtonsBinding.inflate(inflater, container, false)

        binding.btn0.setOnClickListener {
            btnValue = "0"
            buttonClicked()
        }
        binding.btn1.setOnClickListener {
            btnValue = "1"
            buttonClicked()
        }
        binding.btn2.setOnClickListener {
            btnValue = "2"
            buttonClicked()
        }
        binding.btn3.setOnClickListener {
            btnValue = "3"
            buttonClicked()
        }
        binding.btn4.setOnClickListener {
            btnValue = "4"
            buttonClicked()
        }
        binding.btn5.setOnClickListener {
            btnValue = "5"
            buttonClicked()
        }
        binding.btn6.setOnClickListener {
            btnValue = "6"
            buttonClicked()
        }
        binding.btn7.setOnClickListener {
            btnValue = "7"
            buttonClicked()
        }
        binding.btn8.setOnClickListener {
            btnValue = "8"
            buttonClicked()
        }
        binding.btn9.setOnClickListener {
            btnValue = "9"
            buttonClicked()
        }
        binding.btnPoint.setOnClickListener {
            btnValue = "."
            buttonClicked()
        }
        binding.btnAdd.setOnClickListener {
            btnValue = "+"
            buttonClicked()
        }
        binding.btnMinus.setOnClickListener {
            btnValue = "-"
            buttonClicked()
        }
        binding.btnMultiply.setOnClickListener {
            btnValue = "*"
            buttonClicked()
        }
        binding.btnDivide.setOnClickListener {
            btnValue = "/"
            buttonClicked()
        }
        binding.btnModulo.setOnClickListener {
            btnValue = "%"
            buttonClicked()
        }
        binding.btnSqRoot.setOnClickListener {
            btnValue = "√"
            buttonClicked()
        }
        binding.btnEquals.setOnClickListener {
            btnValue = "="
            buttonClicked()
        }
        binding.btnCE.setOnClickListener {
            btnValue = "CE"
            buttonClicked()
        }
        binding.btnClear.setOnClickListener {
            btnValue = "C"
            buttonClicked()
        }

        return binding.root
    }// onCreateView end

    private fun buttonClicked() {
        activityCallback?.onButtonClick(btnValue)
    }
}