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
import kotlin.properties.Delegates

class PawnTransformationFragment : Fragment() {

    private lateinit var binding: FragmentPawnTransformationBinding
    private lateinit var requestCell: Cell
    private var figureColorSymbol by Delegates.notNull<Char>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPawnTransformationBinding.inflate(inflater, container, false)

        if (arguments != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (requireArguments().getParcelable(CELL, Cell::class.java) != null) {
                    requestCell = requireArguments().getParcelable(CELL, Cell::class.java)!!
                }
            } else {
                if (requireArguments().getParcelable<Cell>(CELL) != null) {
                    requestCell = requireArguments().getParcelable(CELL)!!
                }
            }
        }

        figureColorSymbol = requestCell.figureName.first()

        if (figureColorSymbol == 'b') {
            binding.rookImageView.setImageResource(R.drawable.ic_chess_black_rook)
            binding.knightImageView.setImageResource(R.drawable.ic_chess_black_knight)
            binding.bishopImageView.setImageResource(R.drawable.ic_chess_black_bishop)
            binding.queenImageView.setImageResource(R.drawable.ic_chess_black_queen)
        }

        binding.rookImageView.setOnClickListener {onFigurePressed("rook", figureColorSymbol)}
        binding.knightImageView.setOnClickListener {onFigurePressed("knight", figureColorSymbol)}
        binding.bishopImageView.setOnClickListener {onFigurePressed("bishop", figureColorSymbol)}
        binding.queenImageView.setOnClickListener {onFigurePressed("queen", figureColorSymbol)}

        return binding.root
    }

    private fun onFigurePressed(figureName: String, figureColor: Char) {

        var name = ""

        name = if (figureColor == 'w') {
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