package com.example.chess.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.chess.Cell
import com.example.chess.CellHasFigure
import com.example.chess.Coordinates
import com.example.chess.R
import com.example.chess.databinding.FragmentDeskBinding
import com.example.chess.view_models.MainDeskViewModel
import kotlin.properties.Delegates

class MainDeskFragment : Fragment() {

    private val drawableMap = mutableMapOf<String, Int>(
        "white_pawn" to R.drawable.ic_chess_white_pawn,
        "white_rook" to R.drawable.ic_chess_white_rook,
        "white_knight" to R.drawable.ic_chess_white_knight,
        "white_bishop" to R.drawable.ic_chess_white_bishop,
        "white_queen" to R.drawable.ic_chess_white_queen,
        "white_king" to R.drawable.ic_chess_white_king,
        "black_pawn" to R.drawable.ic_chess_black_pawn,
        "black_rook" to R.drawable.ic_chess_black_rook,
        "black_knight" to R.drawable.ic_chess_black_knight,
        "black_bishop" to R.drawable.ic_chess_black_bishop,
        "black_queen" to R.drawable.ic_chess_black_queen,
        "black_king" to R.drawable.ic_chess_black_king,
    )

    private lateinit var binding : FragmentDeskBinding
    private lateinit var desk : Map<Coordinates, CellHasFigure>
    private lateinit var bundleCell: Cell
    private var gameRestart by Delegates.notNull<Boolean>()
    private val viewModel: MainDeskViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeskBinding.inflate(inflater, container, false)

        if (arguments != null) {
            gameRestart = requireArguments().getBoolean(GAME_OVER)
            if (requireArguments().getParcelable(CELL, Cell::class.java) != null) {
                bundleCell = requireArguments().getParcelable(CELL, Cell::class.java)!!
                viewModel.pawnTransform(bundleCell)
            }
        }

        if (gameRestart) {
            gameRestart = false
            viewModel.gameRestart()
        }

        createDesk()

        with(binding) {
            cellA1.setOnClickListener{ onCellPressed(1, 1) }
            cellB1.setOnClickListener{ onCellPressed(1, 2) }
            cellC1.setOnClickListener{ onCellPressed(1, 3) }
            cellD1.setOnClickListener{ onCellPressed(1, 4) }
            cellE1.setOnClickListener{ onCellPressed(1, 5) }
            cellF1.setOnClickListener{ onCellPressed(1, 6) }
            cellG1.setOnClickListener{ onCellPressed(1, 7) }
            cellH1.setOnClickListener{ onCellPressed(1, 8) }

            cellA2.setOnClickListener{ onCellPressed(2, 1) }
            cellB2.setOnClickListener{ onCellPressed(2, 2) }
            cellC2.setOnClickListener{ onCellPressed(2, 3) }
            cellD2.setOnClickListener{ onCellPressed(2, 4) }
            cellE2.setOnClickListener{ onCellPressed(2, 5) }
            cellF2.setOnClickListener{ onCellPressed(2, 6) }
            cellG2.setOnClickListener{ onCellPressed(2, 7) }
            cellH2.setOnClickListener{ onCellPressed(2, 8) }

            cellA3.setOnClickListener{ onCellPressed(3, 1) }
            cellB3.setOnClickListener{ onCellPressed(3, 2) }
            cellC3.setOnClickListener{ onCellPressed(3, 3) }
            cellD3.setOnClickListener{ onCellPressed(3, 4) }
            cellE3.setOnClickListener{ onCellPressed(3, 5) }
            cellF3.setOnClickListener{ onCellPressed(3, 6) }
            cellG3.setOnClickListener{ onCellPressed(3, 7) }
            cellH3.setOnClickListener{ onCellPressed(3, 8) }

            cellA4.setOnClickListener{ onCellPressed(4, 1) }
            cellB4.setOnClickListener{ onCellPressed(4, 2) }
            cellC4.setOnClickListener{ onCellPressed(4, 3) }
            cellD4.setOnClickListener{ onCellPressed(4, 4) }
            cellE4.setOnClickListener{ onCellPressed(4, 5) }
            cellF4.setOnClickListener{ onCellPressed(4, 6) }
            cellG4.setOnClickListener{ onCellPressed(4, 7) }
            cellH4.setOnClickListener{ onCellPressed(4, 8) }

            cellA5.setOnClickListener{ onCellPressed(5, 1) }
            cellB5.setOnClickListener{ onCellPressed(5, 2) }
            cellC5.setOnClickListener{ onCellPressed(5, 3) }
            cellD5.setOnClickListener{ onCellPressed(5, 4) }
            cellE5.setOnClickListener{ onCellPressed(5, 5) }
            cellF5.setOnClickListener{ onCellPressed(5, 6) }
            cellG5.setOnClickListener{ onCellPressed(5, 7) }
            cellH5.setOnClickListener{ onCellPressed(5, 8) }

            cellA6.setOnClickListener{ onCellPressed(6, 1) }
            cellB6.setOnClickListener{ onCellPressed(6, 2) }
            cellC6.setOnClickListener{ onCellPressed(6, 3) }
            cellD6.setOnClickListener{ onCellPressed(6, 4) }
            cellE6.setOnClickListener{ onCellPressed(6, 5) }
            cellF6.setOnClickListener{ onCellPressed(6, 6) }
            cellG6.setOnClickListener{ onCellPressed(6, 7) }
            cellH6.setOnClickListener{ onCellPressed(6, 8) }

            cellA7.setOnClickListener{ onCellPressed(7, 1) }
            cellB7.setOnClickListener{ onCellPressed(7, 2) }
            cellC7.setOnClickListener{ onCellPressed(7, 3) }
            cellD7.setOnClickListener{ onCellPressed(7, 4) }
            cellE7.setOnClickListener{ onCellPressed(7, 5) }
            cellF7.setOnClickListener{ onCellPressed(7, 6) }
            cellG7.setOnClickListener{ onCellPressed(7, 7) }
            cellH7.setOnClickListener{ onCellPressed(7, 8) }

            cellA8.setOnClickListener{ onCellPressed(8, 1) }
            cellB8.setOnClickListener{ onCellPressed(8, 2) }
            cellC8.setOnClickListener{ onCellPressed(8, 3) }
            cellD8.setOnClickListener{ onCellPressed(8, 4) }
            cellE8.setOnClickListener{ onCellPressed(8, 5) }
            cellF8.setOnClickListener{ onCellPressed(8, 6) }
            cellG8.setOnClickListener{ onCellPressed(8, 7) }
            cellH8.setOnClickListener{ onCellPressed(8, 8) }
        }

        viewModel.activityDesk.observe(viewLifecycleOwner, Observer { list ->
            desk.forEach { map ->
                list.forEach { cell ->
                    if (cell.row == map.key.row && cell.column == map.key.column) {
                        if (!cell.hasFigure) {
                            emptyCellFill(Coordinates(map.key.row, map.key.column), map.value.imageView, "drawable")
                            map.value.hasFigure = false
                        } else {
                            var src = 0
                            drawableMap.forEach {
                                if (cell.figureName == it.key) {
                                    src = it.value
                                }
                            }

                            map.value.hasFigure = true
                            map.value.imageView.setImageResource(src)
                        }
                    }
                }
            }
        })

        viewModel.possibleMoveCells.observe(viewLifecycleOwner, Observer{ possibleMoveMap ->
            var cell: CellHasFigure

            possibleMoveMap.forEach() {
                cell = desk.getValue(it.key)
                if (!cell.hasFigure) {
                    if (it.value) {
                        cell.imageView.setImageResource(R.drawable.ic_possible_move_cell)
                    } else {
                        emptyCellFill(it.key, cell.imageView, "drawable")
                    }
                } else {
                    if (it.value) {
                        cell.imageView.setBackgroundColor(Color.rgb(76, 104, 43))
                    } else {
                        emptyCellFill(it.key, cell.imageView, "color")
                    }
                }
            }
        })

        viewModel.gameOver.observe(viewLifecycleOwner, Observer{
            if (it == true) {
                parentFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, GameOverFragment())
                    .commit()
            }
        })

        viewModel.transformablePawnCell.observe(viewLifecycleOwner, Observer {
            parentFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, PawnTransformationFragment.newInstance(it))
                .commit()
        })

        return binding.root
    }

    private fun emptyCellFill(firstCellCords: Coordinates, firstCell: ImageView, type: String) {
        if (firstCellCords.row % 2 == 0) {
            if (firstCellCords.column % 2 == 0) {
                if (type == "drawable") firstCell.setImageResource(R.drawable.ic_black_cell)
                else firstCell.setBackgroundColor(Color.rgb(92, 86, 79))
            } else {
                if (type == "drawable") firstCell.setImageResource(R.drawable.ic_white_cell)
                else firstCell.setBackgroundColor(Color.rgb(223, 204, 181))
            }
        } else {
            if (firstCellCords.column % 2 == 0) {
                if (type == "drawable") firstCell.setImageResource(R.drawable.ic_white_cell)
                else firstCell.setBackgroundColor(Color.rgb(223, 204, 181))
            } else {
                if (type == "drawable") firstCell.setImageResource(R.drawable.ic_black_cell)
                else firstCell.setBackgroundColor(Color.rgb(92, 86, 79))
            }
        }
    }

    private fun onCellPressed(row: Int, column: Int) {
        viewModel.onCellPressed(row, column)
    }

    private fun createDesk() {
        desk = mapOf<Coordinates, CellHasFigure>(
            Coordinates(1,1) to CellHasFigure(binding.cellA1, true), Coordinates(1,2) to CellHasFigure(binding.cellB1, true),
            Coordinates(1,3) to CellHasFigure(binding.cellC1, true), Coordinates(1,4) to CellHasFigure(binding.cellD1, true),
            Coordinates(1,5) to CellHasFigure(binding.cellE1, true), Coordinates(1,6) to CellHasFigure(binding.cellF1, true),
            Coordinates(1,7) to CellHasFigure(binding.cellG1, true), Coordinates(1,8) to CellHasFigure(binding.cellH1, true),

            Coordinates(2,1) to CellHasFigure(binding.cellA2, true), Coordinates(2,2) to CellHasFigure(binding.cellB2, true),
            Coordinates(2,3) to CellHasFigure(binding.cellC2, true), Coordinates(2,4) to CellHasFigure(binding.cellD2, true),
            Coordinates(2,5) to CellHasFigure(binding.cellE2, true), Coordinates(2,6) to CellHasFigure(binding.cellF2, true),
            Coordinates(2,7) to CellHasFigure(binding.cellG2, true), Coordinates(2,8) to CellHasFigure(binding.cellH2, true),

            Coordinates(3,1) to CellHasFigure(binding.cellA3, false), Coordinates(3,2) to CellHasFigure(binding.cellB3, false),
            Coordinates(3,3) to CellHasFigure(binding.cellC3, false), Coordinates(3,4) to CellHasFigure(binding.cellD3, false),
            Coordinates(3,5) to CellHasFigure(binding.cellE3, false), Coordinates(3,6) to CellHasFigure(binding.cellF3, false),
            Coordinates(3,7) to CellHasFigure(binding.cellG3, false), Coordinates(3,8) to CellHasFigure(binding.cellH3, false),

            Coordinates(4,1) to CellHasFigure(binding.cellA4, false), Coordinates(4,2) to CellHasFigure(binding.cellB4, false),
            Coordinates(4,3) to CellHasFigure(binding.cellC4, false), Coordinates(4,4) to CellHasFigure(binding.cellD4, false),
            Coordinates(4,5) to CellHasFigure(binding.cellE4, false), Coordinates(4,6) to CellHasFigure(binding.cellF4, false),
            Coordinates(4,7) to CellHasFigure(binding.cellG4, false), Coordinates(4,8) to CellHasFigure(binding.cellH4, false),

            Coordinates(5,1) to CellHasFigure(binding.cellA5, false), Coordinates(5,2) to CellHasFigure(binding.cellB5, false),
            Coordinates(5,3) to CellHasFigure(binding.cellC5, false), Coordinates(5,4) to CellHasFigure(binding.cellD5, false),
            Coordinates(5,5) to CellHasFigure(binding.cellE5, false), Coordinates(5,6) to CellHasFigure(binding.cellF5, false),
            Coordinates(5,7) to CellHasFigure(binding.cellG5, false), Coordinates(5,8) to CellHasFigure(binding.cellH5, false),

            Coordinates(6,1) to CellHasFigure(binding.cellA6, false), Coordinates(6,2) to CellHasFigure(binding.cellB6, false),
            Coordinates(6,3) to CellHasFigure(binding.cellC6, false), Coordinates(6,4) to CellHasFigure(binding.cellD6, false),
            Coordinates(6,5) to CellHasFigure(binding.cellE6, false), Coordinates(6,6) to CellHasFigure(binding.cellF6, false),
            Coordinates(6,7) to CellHasFigure(binding.cellG6, false), Coordinates(6,8) to CellHasFigure(binding.cellH6, false),

            Coordinates(7,1) to CellHasFigure(binding.cellA7, true), Coordinates(7,2) to CellHasFigure(binding.cellB7, true),
            Coordinates(7,3) to CellHasFigure(binding.cellC7, true), Coordinates(7,4) to CellHasFigure(binding.cellD7, true),
            Coordinates(7,5) to CellHasFigure(binding.cellE7, true), Coordinates(7,6) to CellHasFigure(binding.cellF7, true),
            Coordinates(7,7) to CellHasFigure(binding.cellG7, true), Coordinates(7,8) to CellHasFigure(binding.cellH7, true),

            Coordinates(8,1) to CellHasFigure(binding.cellA8, true), Coordinates(8,2) to CellHasFigure(binding.cellB8, true),
            Coordinates(8,3) to CellHasFigure(binding.cellC8, true), Coordinates(8,4) to CellHasFigure(binding.cellD8, true),
            Coordinates(8,5) to CellHasFigure(binding.cellE8, true), Coordinates(8,6) to CellHasFigure(binding.cellF8, true),
            Coordinates(8,7) to CellHasFigure(binding.cellG8, true), Coordinates(8,8) to CellHasFigure(binding.cellH8, true),
        )
    }

    companion object {
        @JvmStatic private val CELL = "CELL"
        @JvmStatic private val GAME_OVER = "GAME_OVER"

        fun newInstance(cell: Cell?, gameOver: Boolean) : MainDeskFragment {
            val args = Bundle()
            args.putParcelable(CELL, cell)
            args.putBoolean(GAME_OVER, gameOver)
            val fragment = MainDeskFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
