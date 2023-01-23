package com.example.chess.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chess.Cell
import com.example.chess.CellsPair
import com.example.chess.Coordinates
import kotlin.math.abs

private var desk = mutableListOf<Cell>()
private var isFirstClick = true
private var pickedCell = Cell(-1, -1, false, "")
private var walkerName = 'w'

class MainDeskViewModel : ViewModel() {

    private var _cellsPair = MutableLiveData<CellsPair>()
    var cellsPair : LiveData<CellsPair> = _cellsPair

    init {
        deskFill()
    }

    // Нажатие на клетку
    fun onCellPressed(row: Int, column: Int) {
        if (isFirstClick) {
            pickedCell = figureAvailabilityCheck(row, column)
            isFirstClick = pickedCell.hasFigure == false
        } else {
            val newPickedCell = figureAvailabilityCheck(row, column)
            val canMove = currentFigureMove(pickedCell, newPickedCell)
            val opponentColorName = if (pickedCell.figureName.first() == 'w') {
                'b'
            } else {
                'w'
            }

            Log.e("AEFP", "$walkerName : $opponentColorName")
            if((!newPickedCell.hasFigure || newPickedCell.figureName.first() == opponentColorName)
                && walkerName == pickedCell.figureName.first()) {
                if (canMove[Coordinates(newPickedCell.row, newPickedCell.column)] == true) {
                    isFirstClick = true
                    _cellsPair.value = CellsPair(pickedCell, Cell(row, column, false, ""))

                    val newPickedCellIndex = desk.indexOf(newPickedCell)
                    val pickedCellIndex = desk.indexOf(pickedCell)
                    desk[newPickedCellIndex].hasFigure = true
                    desk[newPickedCellIndex].figureName = desk[pickedCellIndex].figureName
                    desk[pickedCellIndex].hasFigure = false
                    desk[pickedCellIndex].figureName = ""

                    walkerName = if (walkerName == 'w') {
                        'b'
                    } else {
                        'w'
                    }
                } else {
                    isFirstClick = true
                }
            } else {
                isFirstClick = true
            }
        }
    }

    // Проверка на наличие фигуры в определенной клетке
    private fun figureAvailabilityCheck(row: Int, column: Int) : Cell{
        var defaultRow = -1
        var defaultColumn = -1
        desk.forEach { cell ->
            if(cell.row == row && cell.column == column) {
                if (cell.hasFigure) {
                    return cell
                } else {
                    defaultRow = cell.row
                    defaultColumn = cell.column
                }
            }
        }
        return Cell(defaultRow, defaultColumn, false, "")
    }

    // Проверка на возможность хода
    private fun currentFigureMove(firstCell: Cell, secondCell: Cell) : Map<Coordinates, Boolean> {
        val canMoveMap = mutableMapOf<Coordinates, Boolean>()
        val name = firstCell.figureName.substring(6)

        val opponentFigureName = if (firstCell.figureName.first() == 'w') {
            'b'
        } else {
            'w'
        }

        if (firstCell.figureName.isNotEmpty()) {
            when (name) {
                "pawn" -> {
                    if (firstCell.figureName.first() == 'w') {
                        if (firstCell.row == 2) {
                            var moveVariationIndex = desk.indexOf(firstCell)

                            for (i in 1..2) {
                                moveVariationIndex += 8
                                canMoveMap[Coordinates(desk[moveVariationIndex].row, desk[moveVariationIndex].column)] =
                                    !desk[moveVariationIndex].hasFigure
                            }
                            moveVariationIndex = desk.indexOf(firstCell)
                            moveVariationIndex += 9
                            if (desk[moveVariationIndex].hasFigure) {
                                canMoveMap[Coordinates(desk[moveVariationIndex].row, desk[moveVariationIndex].column)] =
                                    desk[moveVariationIndex].figureName.first() == 'b'
                            }

                            moveVariationIndex -= 2
                            if (desk[moveVariationIndex].hasFigure) {
                                canMoveMap[Coordinates(desk[moveVariationIndex].row, desk[moveVariationIndex].column)] =
                                    desk[moveVariationIndex].figureName.first() == 'b'
                            }
                        } else {
                            var moveVariationIndex = desk.indexOf(firstCell)

                            moveVariationIndex += 8
                            canMoveMap[Coordinates(desk[moveVariationIndex].row, desk[moveVariationIndex].column)] =
                                !desk[moveVariationIndex].hasFigure

                            moveVariationIndex = desk.indexOf(firstCell)
                            moveVariationIndex += 9
                            if (desk[moveVariationIndex].hasFigure) {
                                canMoveMap[Coordinates(desk[moveVariationIndex].row, desk[moveVariationIndex].column)] =
                                    desk[moveVariationIndex].figureName.first() == 'b'
                            }

                            moveVariationIndex -= 2
                            if (desk[moveVariationIndex].hasFigure) {
                                canMoveMap[Coordinates(desk[moveVariationIndex].row, desk[moveVariationIndex].column)] =
                                    desk[moveVariationIndex].figureName.first() == 'b'
                            }
                        }
                    } else {
                        if (firstCell.row == 7) {
                            var moveVariationIndex = desk.indexOf(firstCell)

                            for (i in 1..2) {
                                moveVariationIndex -= 8
                                canMoveMap[Coordinates(desk[moveVariationIndex].row, desk[moveVariationIndex].column)] =
                                    !desk[moveVariationIndex].hasFigure
                            }
                            moveVariationIndex = desk.indexOf(firstCell)
                            moveVariationIndex -= 9
                            if (desk[moveVariationIndex].hasFigure) {
                                canMoveMap[Coordinates(desk[moveVariationIndex].row, desk[moveVariationIndex].column)] =
                                    desk[moveVariationIndex].figureName.first() == 'w'
                            }

                            moveVariationIndex += 2
                            if (desk[moveVariationIndex].hasFigure) {
                                canMoveMap[Coordinates(desk[moveVariationIndex].row, desk[moveVariationIndex].column)] =
                                    desk[moveVariationIndex].figureName.first() == 'w'
                            }
                        } else {
                            var moveVariationIndex = desk.indexOf(firstCell)

                            moveVariationIndex -= 8
                            canMoveMap[Coordinates(desk[moveVariationIndex].row, desk[moveVariationIndex].column)] =
                                !desk[moveVariationIndex].hasFigure

                            moveVariationIndex = desk.indexOf(firstCell)
                            moveVariationIndex -= 9
                            if (desk[moveVariationIndex].hasFigure) {
                                canMoveMap[Coordinates(desk[moveVariationIndex].row, desk[moveVariationIndex].column)] =
                                    desk[moveVariationIndex].figureName.first() == 'w'
                            }

                            moveVariationIndex += 2
                            if (desk[moveVariationIndex].hasFigure) {
                                canMoveMap[Coordinates(desk[moveVariationIndex].row, desk[moveVariationIndex].column)] =
                                    desk[moveVariationIndex].figureName.first() == 'w'
                            }
                        }
                    }
                }

                "rook" -> {
                    var tempCanMoveMap = rookMoves(firstCell, "vertical", "up", opponentFigureName)
                    canMoveMap += tempCanMoveMap

                    tempCanMoveMap = rookMoves(firstCell, "vertical", "down", opponentFigureName)
                    canMoveMap += tempCanMoveMap

                    tempCanMoveMap = rookMoves(firstCell, "horizontal", "up", opponentFigureName)
                    canMoveMap += tempCanMoveMap

                    tempCanMoveMap = rookMoves(firstCell, "horizontal", "down", opponentFigureName)
                    canMoveMap += tempCanMoveMap
                }

                "bishop" -> {
                    var tempCanMoveMap = bishopMoves(firstCell, "up", 9, opponentFigureName)
                    canMoveMap += tempCanMoveMap

                    tempCanMoveMap = bishopMoves(firstCell, "up", 7, opponentFigureName)
                    canMoveMap += tempCanMoveMap

                    tempCanMoveMap = bishopMoves(firstCell, "down", 9, opponentFigureName)
                    canMoveMap += tempCanMoveMap

                    tempCanMoveMap = bishopMoves(firstCell, "down", 7, opponentFigureName)
                    canMoveMap += tempCanMoveMap

                }

                "knight" -> {
                    if (secondCell.column == firstCell.column + 2 || secondCell.column == firstCell.column - 2) {
                        if (secondCell.row == firstCell.row + 1 || secondCell.row == firstCell.row -1) {
                            canMoveMap[Coordinates(secondCell.row, secondCell.column)] = true
                        }
                    } else if (secondCell.row == firstCell.row + 2 || secondCell.row == firstCell.row - 2) {
                        if (secondCell.column == firstCell.column + 1 || secondCell.column == firstCell.column - 1) {
                            canMoveMap[Coordinates(secondCell.row, secondCell.column)] = true
                        }
                    }
                }

                "queen" -> {
                    var tempCanMoveMap = rookMoves(firstCell, "vertical", "up", opponentFigureName)
                    canMoveMap += tempCanMoveMap
                    tempCanMoveMap = rookMoves(firstCell, "vertical", "down", opponentFigureName)
                    canMoveMap += tempCanMoveMap
                    tempCanMoveMap = rookMoves(firstCell, "horizontal", "up", opponentFigureName)
                    canMoveMap += tempCanMoveMap
                    tempCanMoveMap = rookMoves(firstCell, "horizontal", "down", opponentFigureName)
                    canMoveMap += tempCanMoveMap

                    tempCanMoveMap = bishopMoves(firstCell, "up", 9, opponentFigureName)
                    canMoveMap += tempCanMoveMap
                    tempCanMoveMap = bishopMoves(firstCell, "up", 7, opponentFigureName)
                    canMoveMap += tempCanMoveMap
                    tempCanMoveMap = bishopMoves(firstCell, "down", 9, opponentFigureName)
                    canMoveMap += tempCanMoveMap
                    tempCanMoveMap = bishopMoves(firstCell, "down", 7, opponentFigureName)
                    canMoveMap += tempCanMoveMap
                }

                "king" -> {
                    val tempCanMoveMap = kingMoves(firstCell,opponentFigureName)
                    canMoveMap += tempCanMoveMap
                }
            }
        }

        return canMoveMap
    }

    // Расчет ходов ладьи
    private fun rookMoves(firstCell: Cell, moveOrientation: String, moveRoute: String, opponentFigureName: Char) : Map<Coordinates, Boolean> {
        val canMoveMap = mutableMapOf<Coordinates, Boolean>()
        var moveVariationIndex = desk.indexOf(firstCell)
        var cycleStart = 0
        var cycleStartCoefficient = 1
        var coefficient = 0
        var limit = 8

        if (moveOrientation == "vertical") {
            cycleStart = firstCell.row
            coefficient = 8
        } else {
            cycleStart = firstCell.column
            coefficient = 1
        }
        if (moveRoute == "down") {
            coefficient *= -1
            cycleStartCoefficient = -1
            limit = 1
        }

        while(true) {
            if (cycleStart == limit) {
                break
            }

            moveVariationIndex += coefficient
            cycleStart += cycleStartCoefficient

            if (desk[moveVariationIndex].hasFigure) {
                canMoveMap[Coordinates(
                    desk[moveVariationIndex].row,
                    desk[moveVariationIndex].column
                )] =
                    desk[moveVariationIndex].figureName.first() == opponentFigureName
                break
            } else {
                canMoveMap[Coordinates(
                    desk[moveVariationIndex].row,
                    desk[moveVariationIndex].column
                )] = true
            }
        }

        return canMoveMap
    }

    // Расчет ходов слона
    private fun bishopMoves(firstCell: Cell, moveRoute: String, moveRouteNum: Int, opponentFigureName: Char) : Map<Coordinates, Boolean> {
        val canMoveMap = mutableMapOf<Coordinates, Boolean>()
        var moveVariationIndex = desk.indexOf(firstCell)
        var columnLimit = 0

        if (moveRoute == "up") {
            columnLimit = if (moveRouteNum == 9) {
                8
            } else {
                1
            }

            while (true) {
                if (desk[moveVariationIndex].column == columnLimit) {
                    break
                }

                moveVariationIndex += moveRouteNum
                if (moveVariationIndex > 63 || moveVariationIndex < 1) {
                    break
                }

                if (desk[moveVariationIndex].hasFigure) {
                    canMoveMap[Coordinates(
                        desk[moveVariationIndex].row,
                        desk[moveVariationIndex].column
                    )] =
                        desk[moveVariationIndex].figureName.first() == opponentFigureName
                    break
                } else {
                    canMoveMap[Coordinates(desk[moveVariationIndex].row, desk[moveVariationIndex].column)] = true
                }
            }
        } else {
            columnLimit = if (moveRouteNum == 9) {
                1
            } else {
                8
            }

            while (true) {
                if (desk[moveVariationIndex].column == columnLimit) {
                    break
                }

                moveVariationIndex -= moveRouteNum
                if (moveVariationIndex > 63 || moveVariationIndex < 1) {
                    break
                }

                if (desk[moveVariationIndex].hasFigure) {
                    canMoveMap[Coordinates(
                        desk[moveVariationIndex].row,
                        desk[moveVariationIndex].column
                    )] =
                        desk[moveVariationIndex].figureName.first() == opponentFigureName
                    break
                } else {
                    canMoveMap[Coordinates(desk[moveVariationIndex].row, desk[moveVariationIndex].column)] = true
                }
            }
        }

        return canMoveMap
    }

    // Расчет ходов короля
    private fun kingMoves(firstCell: Cell, opponentFigureName: Char) : Map<Coordinates, Boolean> {
        val canMoveMap = mutableMapOf<Coordinates, Boolean>()
        val moveVariationIndex = desk.indexOf(firstCell)
        val coefficientsList = mutableListOf<Int>()

        when (firstCell.row) {
            1 -> {
                when (firstCell.column) {
                    1 -> {
                        coefficientsList.add(8)
                        coefficientsList.add(9)
                        coefficientsList.add(1)
                    }
                    8 -> {
                        coefficientsList.add(-1)
                        coefficientsList.add(7)
                        coefficientsList.add(8)
                    }
                    else -> {
                        coefficientsList.add(-1)
                        coefficientsList.add(7)
                        coefficientsList.add(8)
                        coefficientsList.add(9)
                        coefficientsList.add(1)
                    }
                }
            }

            8 -> {
                when (firstCell.column) {
                    1 -> {
                        coefficientsList.add(1)
                        coefficientsList.add(-7)
                        coefficientsList.add(-8)
                    }
                    8 -> {
                        coefficientsList.add(-8)
                        coefficientsList.add(-9)
                        coefficientsList.add(-1)
                    }
                    else -> {
                        coefficientsList.add(1)
                        coefficientsList.add(-7)
                        coefficientsList.add(-8)
                        coefficientsList.add(-9)
                        coefficientsList.add(-1)
                    }
                }
            }

            else -> {
                when (firstCell.column) {
                    1 -> {
                        coefficientsList.add(8)
                        coefficientsList.add(9)
                        coefficientsList.add(1)
                        coefficientsList.add(-7)
                        coefficientsList.add(-8)
                    }
                    8 -> {
                        coefficientsList.add(-8)
                        coefficientsList.add(-9)
                        coefficientsList.add(-1)
                        coefficientsList.add(7)
                        coefficientsList.add(8)
                    }
                    else -> {
                        coefficientsList.add(1)
                        coefficientsList.add(-1)
                        for (i in 7..9) {
                            coefficientsList.add(i)
                            coefficientsList.add(-i)
                        }
                    }
                }
            }
        }

        for (i in coefficientsList) {
            if (desk[moveVariationIndex + i].hasFigure) {
                canMoveMap[Coordinates(
                    desk[moveVariationIndex + i].row,
                    desk[moveVariationIndex + i].column
                )] =
                    desk[moveVariationIndex + i].figureName.first() == opponentFigureName
            } else {
                canMoveMap[Coordinates(desk[moveVariationIndex + i].row, desk[moveVariationIndex + i].column)] = true
            }
        }

        return canMoveMap
    }

    // Создание шахмотной доски и заполнение фигурами
    private fun deskFill() {
        for (i in 1..8) {
            for (j in 1..8) {
                if (i in 3..6){
                    desk.add(Cell(i,j, false,""))
                } else {
                    if (i == 2) {
                        desk.add(Cell(i,j, true,"white_pawn"))
                    } else if (i == 7) {
                        desk.add(Cell(i,j, true,"black_pawn"))
                    }
                    else {
                        desk.add(Cell(i,j, true,""))
                    }
                }
            }
        }

        desk[0].figureName = "white_rook"
        desk[1].figureName = "white_knight"
        desk[2].figureName = "white_bishop"
        desk[3].figureName = "white_queen"
        desk[4].figureName = "white_king"
        desk[5].figureName = "white_bishop"
        desk[6].figureName = "white_knight"
        desk[7].figureName = "white_rook"

        desk[56].figureName = "black_rook"
        desk[57].figureName = "black_knight"
        desk[58].figureName = "black_bishop"
        desk[59].figureName = "black_queen"
        desk[60].figureName = "black_king"
        desk[61].figureName = "black_bishop"
        desk[62].figureName = "black_knight"
        desk[63].figureName = "black_rook"
    }
}