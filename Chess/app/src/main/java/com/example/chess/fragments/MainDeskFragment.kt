package com.example.chess.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.chess.Coordinates
import com.example.chess.R
import com.example.chess.databinding.FragmentDeskBinding
import com.example.chess.view_models.MainDeskViewModel

class MainDeskFragment : Fragment() {

    private lateinit var binding : FragmentDeskBinding
    private lateinit var desk : Map<Coordinates, ImageView>
    private val viewModel: MainDeskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeskBinding.inflate(inflater, container, false)

        desk = mapOf<Coordinates, ImageView>(
            Coordinates(1,1) to binding.cellA1, Coordinates(1,2) to binding.cellB1,
            Coordinates(1,3) to binding.cellC1, Coordinates(1,4) to binding.cellD1,
            Coordinates(1,5) to binding.cellE1, Coordinates(1,6) to binding.cellF1,
            Coordinates(1,7) to binding.cellG1, Coordinates(1,8) to binding.cellH1,

            Coordinates(2,1) to binding.cellA2, Coordinates(2,2) to binding.cellB2,
            Coordinates(2,3) to binding.cellC2, Coordinates(2,4) to binding.cellD2,
            Coordinates(2,5) to binding.cellE2, Coordinates(2,6) to binding.cellF2,
            Coordinates(2,7) to binding.cellG2, Coordinates(2,8) to binding.cellH2,

            Coordinates(3,1) to binding.cellA3, Coordinates(3,2) to binding.cellB3,
            Coordinates(3,3) to binding.cellC3, Coordinates(3,4) to binding.cellD3,
            Coordinates(3,5) to binding.cellE3, Coordinates(3,6) to binding.cellF3,
            Coordinates(3,7) to binding.cellG3, Coordinates(3,8) to binding.cellH3,

            Coordinates(4,1) to binding.cellA4, Coordinates(4,2) to binding.cellB4,
            Coordinates(4,3) to binding.cellC4, Coordinates(4,4) to binding.cellD4,
            Coordinates(4,5) to binding.cellE4, Coordinates(4,6) to binding.cellF4,
            Coordinates(4,7) to binding.cellG4, Coordinates(4,8) to binding.cellH4,

            Coordinates(5,1) to binding.cellA5, Coordinates(5,2) to binding.cellB5,
            Coordinates(5,3) to binding.cellC5, Coordinates(5,4) to binding.cellD5,
            Coordinates(5,5) to binding.cellE5, Coordinates(5,6) to binding.cellF5,
            Coordinates(5,7) to binding.cellG5, Coordinates(5,8) to binding.cellH5,

            Coordinates(6,1) to binding.cellA6, Coordinates(6,2) to binding.cellB6,
            Coordinates(6,3) to binding.cellC6, Coordinates(6,4) to binding.cellD6,
            Coordinates(6,5) to binding.cellE6, Coordinates(6,6) to binding.cellF6,
            Coordinates(6,7) to binding.cellG6, Coordinates(6,8) to binding.cellH6,

            Coordinates(7,1) to binding.cellA7, Coordinates(7,2) to binding.cellB7,
            Coordinates(7,3) to binding.cellC7, Coordinates(7,4) to binding.cellD7,
            Coordinates(7,5) to binding.cellE7, Coordinates(7,6) to binding.cellF7,
            Coordinates(7,7) to binding.cellG7, Coordinates(7,8) to binding.cellH7,

            Coordinates(8,1) to binding.cellA8, Coordinates(8,2) to binding.cellB8,
            Coordinates(8,3) to binding.cellC8, Coordinates(8,4) to binding.cellD8,
            Coordinates(8,5) to binding.cellE8, Coordinates(8,6) to binding.cellF8,
            Coordinates(8,7) to binding.cellG8, Coordinates(8,8) to binding.cellH8,
        )

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

        viewModel.cellsPair.observe(viewLifecycleOwner, Observer{
            val firstCellCords = Coordinates(it.firstCell.row, it.firstCell.column)
            val secondCellCords = Coordinates(it.secondCell.row, it.secondCell.column)

            val firstCell = desk.getValue(firstCellCords)
            val secondCell = desk.getValue(secondCellCords)

            secondCell.setImageDrawable(firstCell.drawable)

            if (firstCellCords.row % 2 == 0) {
                if (firstCellCords.column % 2 == 0) {
                    firstCell.setImageResource(R.drawable.ic_black_cell)
                } else {
                    firstCell.setImageResource(R.drawable.ic_white_cell)
                }
            } else {
                if (firstCellCords.column % 2 == 0) {
                    firstCell.setImageResource(R.drawable.ic_white_cell)
                } else {
                    firstCell.setImageResource(R.drawable.ic_black_cell)
                }
            }
        })

        return binding.root
    }

    private fun onCellPressed(row: Int, column: Int) {
        viewModel.onCellPressed(row, column)
    }

    private fun createDesk() {

    }
}