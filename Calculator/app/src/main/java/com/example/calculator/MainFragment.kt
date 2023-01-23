package com.example.calculator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.calculator.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding : FragmentMainBinding
    private val vm: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)

        with(binding) {
//            Click listeners for numbers
            numberZeroButton.setOnClickListener { onNumberPressed(numberZeroButton) }
            numberOneButton.setOnClickListener { onNumberPressed(numberOneButton) }
            numberTwoButton.setOnClickListener { onNumberPressed(numberTwoButton) }
            numberThreeButton.setOnClickListener { onNumberPressed(numberThreeButton) }
            numberFourButton.setOnClickListener { onNumberPressed(numberFourButton) }
            numberFiveButton.setOnClickListener { onNumberPressed(numberFiveButton) }
            numberSixButton.setOnClickListener { onNumberPressed(numberSixButton) }
            numberSevenButton.setOnClickListener { onNumberPressed(numberSevenButton) }
            numberEightButton.setOnClickListener { onNumberPressed(numberEightButton) }
            numberNineButton.setOnClickListener { onNumberPressed(numberNineButton) }

//            Click listeners for operators
            multipleButton.setOnClickListener { onOperatorPressed(multipleButton) }
            divButton.setOnClickListener { onOperatorPressed(divButton) }
            plusButton.setOnClickListener { onOperatorPressed(plusButton) }
            minusButton.setOnClickListener { onOperatorPressed(minusButton) }
            percentButton.setOnClickListener { onPercentPressed() }
            sqrButton.setOnClickListener { onSqrPressed() }
            dotButton.setOnClickListener { onDotPressed() }
            negativeButton.setOnClickListener { onNegativePressed() }
            equalButton.setOnClickListener { onEqualPressed() }

            clearButton.setOnClickListener { onClearPressed() }
        }

        vm.symbols.observe(viewLifecycleOwner, Observer {
            binding.resultTextView.text = it
        })

        return binding.root
    }

    private fun onNumberPressed(number: Button) {
        val resultText = binding.resultTextView.text.toString()
        vm.onNumberPressed(resultText, number.text.toString())
    }

    private fun onPercentPressed() {
        val resultText = binding.resultTextView.text.toString()
        vm.onPercentPressed(resultText)
    }

    private fun onSqrPressed() {
        val resultText = binding.resultTextView.text.toString()
        vm.onSqrPressed(resultText)
    }

    private fun onDotPressed() {
        val resultText = binding.resultTextView.text.toString()
        vm.onDotPressed(resultText)
    }

    private fun onNegativePressed() {
        val resultText = binding.resultTextView.text.toString()
        vm.onNegativePressed(resultText)
    }

    private fun onOperatorPressed(operator: Button) {
        val resultText = binding.resultTextView.text.toString()
        vm.onOperatorPressed(resultText, operator.text.toString())
    }

    private fun onEqualPressed() {
        val resultText = binding.resultTextView.text.toString()
        vm.onEqualPressed(resultText)
    }

    private fun onClearPressed() {
        vm.onClearPressed()
    }

}