package com.rob.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rob.calculator.databinding.FragmentDisplayBinding

class DisplayFragment : Fragment() {

    private lateinit var binding: FragmentDisplayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDisplayBinding.inflate(inflater, container, false)
        return binding.root
    }
}