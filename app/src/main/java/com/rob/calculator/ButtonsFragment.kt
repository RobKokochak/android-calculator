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

    private var btnValue : String = "0"

    var activityCallback : ButtonsFragment.ButtonsListener? = null

    interface ButtonsListener {
        fun onButtonClick(btnValue : String) // what I'll send to MainActivity
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

        return binding.root
    }// onCreateView end

    private fun buttonClicked() {
        activityCallback?.onButtonClick(btnValue)
    }
}