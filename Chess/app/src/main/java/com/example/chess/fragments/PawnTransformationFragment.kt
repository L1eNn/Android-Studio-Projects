package com.example.chess.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.chess.Cell
import com.example.chess.R
import com.example.chess.databinding.FragmentPawnTransformationBinding

class PawnTransformationFragment : Fragment() {

    private lateinit var binding: FragmentPawnTransformationBinding
    private lateinit var requestCell: Cell

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPawnTransformationBinding.inflate(inflater, container, false)

        if (arguments != null) {
            requestCell = requireArguments().getParcelable(CELL, Cell::class.java)!!
        }

        binding.rookImageView.setOnClickListener {onFigurePressed("rook")}
        binding.knightImageView.setOnClickListener {onFigurePressed("knight")}
        binding.bishopImageView.setOnClickListener {onFigurePressed("bishop")}
        binding.queenImageView.setOnClickListener {onFigurePressed("queen")}

        return binding.root
    }

    private fun onFigurePressed(figureName: String) {

        var name = ""

        name = if (requestCell.figureName.first() == 'w') {
            "white_$figureName"
        } else {
            "black_$figureName"
        }

        val cell = Cell(requestCell.row,requestCell.column,true,name)

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, MainDeskFragment.newInstance(cell, false))
            .commit()
    }

    companion object {
        @JvmStatic private val CELL = "CELL"

        fun newInstance(cell: Cell) : PawnTransformationFragment{
            val args = Bundle()
            args.putParcelable(CELL, cell)
            val fragment = PawnTransformationFragment()
            fragment.arguments = args
            return fragment
        }
    }
}