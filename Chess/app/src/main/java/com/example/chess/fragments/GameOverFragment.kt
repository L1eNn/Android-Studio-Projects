package com.example.chess.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.chess.R
import com.example.chess.databinding.FragmentGameOverBinding
import com.example.chess.view_models.MainDeskViewModel

class GameOverFragment : Fragment() {

    private lateinit var binding : FragmentGameOverBinding
    private val viewModel: MainDeskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameOverBinding.inflate(inflater, container, false)

        val winnerColor = arguments?.getChar(WINNER_COLOR)
        if (winnerColor == 'w') {
            binding.winTextView.text = getString(R.string.win_text, "White")
        } else {
            binding.winTextView.text = getString(R.string.win_text, "Black")
        }

        binding.retryButton.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, MainDeskFragment.newInstance(null, true))
                .commit()
        }

        binding.exitButton.setOnClickListener {
            activity?.finish()
        }

        return binding.root
    }

    companion object {
        @JvmStatic val WINNER_COLOR = "WINNER_COLOR"

        fun newInstance(winnerColorSymbol: Char) : GameOverFragment {
            val args = Bundle()
            args.putChar(WINNER_COLOR, winnerColorSymbol)
            val fragment = GameOverFragment()
            fragment.arguments = args

            return fragment
        }
    }

}