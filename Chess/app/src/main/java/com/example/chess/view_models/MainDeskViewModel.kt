package com.example.chess.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chess.Cell
import com.example.chess.CellsPair
import com.example.chess.Coordinates

private var desk = mutableListOf<Cell>()
private var isFirstClick = true
private var pickedCell = Cell(-1, -1, false, "")
private var walkerName = 'w'
private var canMoveMap = mapOf<Coordinates, Boolean>()
private var possibleMoves = mutableMapOf<Coordinates, Boolean>()

class MainDeskViewModel : ViewModel() {

    private var _activityDesk = MutableLiveData<List<Cell>>()
    var activityDesk : LiveData<List<Cell>> = _activityDesk

    private var _possibleMoveCells = MutableLiveData<Map<Coordinates, Boolean>>()
    var possibleMoveCells : LiveData<Map<Coordinates, Boolean>> = _possibleMoveCells

    private var _gameOver = MutableLiveData<Boolean>()
    var gameOver : LiveData<Boolean> = _gameOver

    init {
        deskFill()
    }

    // Нажатие на клетку
    fun onCellPressed(row: Int, column: Int) {
        if (isFirstClick) {
            pickedCell = figureAvailabilityCheck(row, column)

            if (pickedCell.hasFigure) {
                isFirstClick = false
                canMoveMap = currentFigureMove(pickedCell)
                possibleMoves = checkToMove(canMoveMap)
                _possibleMoveCells.value = possibleMoves
            } else {
                isFirstClick = true
            }

            isFirstClick = pickedCell.hasFigure == false
        } else {
            val newPickedCell = figureAvailabilityCheck(row, column)
            val opponentColorName = if (pickedCell.figureName.first() == 'w') {
                'b'
            } else {
                'w'
            }

            if((!newPickedCell.hasFigure || newPickedCell.figureName.first() == opponentColorName)
                && walkerName == pickedCell.figureName.first()) {
                if (canMoveMap[Coordinates(newPickedCell.row, newPickedCell.column)] == true) {
                    isFirstClick = true

                    possibleMoves.forEach() {
                        possibleMoves[it.key] = false
                    }
                    _possibleMoveCells.value = possibleMoves
                    possibleMoves.clear()

                    if (pickedCell.figureName.substring(6).first() == 'p' && newPickedCell.row == 8) {
                        //TODO Превращение пешки
                    } else if (pickedCell.figureName.substring(6).first() == 'p' && newPickedCell.row == 1) {
                        //TODO Превращение пешки
                    }

                    if (newPickedCell.hasFigure && newPickedCell.figureName.last() == 'g') {
                        _gameOver.value = true
                    }

                    val newPickedCellIndex = desk.indexOf(newPickedCell)
                    val pickedCellIndex = desk.indexOf(pickedCell)
                    desk[newPickedCellIndex].hasFigure = true
                    desk[newPickedCellIndex].figureName = desk[pickedCellIndex].figureName
                    desk[pickedCellIndex].hasFigure = false
                    desk[pickedCellIndex].figureName = ""

                    _activityDesk.value = desk

                    walkerName = if (walkerName == 'w') {
                        'b'
                    } else {
                        'w'
                    }
                } else {
                    isFirstClick = true

                    possibleMoves.forEach() {
                        possibleMoves[it.key] = false
                    }
                    _possibleMoveCells.value = possibleMoves
                    possibleMoves.clear()
                }
            } else {
                isFirstClick = true

                possibleMoves.forEach() {
                    possibleMoves[it.key] = false
                }
                _possibleMoveCells.value = possibleMoves
                possibleMoves.clear()
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

    // Проверка на возможные ходы
    private fun currentFigureMove(firstCell: Cell) : Map<Coordinates, Boolean> {
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
                    val tempCanMoveMap = if (firstCell.row == 2 && firstCell.figureName.first() == 'w') {
                        pawnMoves(firstCell,2,opponentFigureName)
                    } else if (firstCell.row == 7 && firstCell.figureName.first() == 'b') {
                        pawnMoves(firstCell,2,opponentFigureName)
                    } else {
                        pawnMoves(firstCell,1,opponentFigureName)
                    }

                    canMoveMap += tempCanMoveMap
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
                    var tempCanMoveMap = knightMoves(firstCell, "vertical", "up", opponentFigureName)
                    canMoveMap += tempCanMoveMap

                    tempCanMoveMap = knightMoves(firstCell, "vertical", "down", opponentFigureName)
                    canMoveMap += tempCanMoveMap

                    tempCanMoveMap = knightMoves(firstCell, "horizontal", "up", opponentFigureName)
                    canMoveMap += tempCanMoveMap

                    tempCanMoveMap = knightMoves(firstCell, "horizontal", "down", opponentFigureName)
                    canMoveMap += tempCanMoveMap
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

    // Расчет ходов пешки
    private fun pawnMoves(firstCell: Cell, moveAmount: Int, opponentFigureName: Char) : Map<Coordinates, Boolean> {
        val canMoveMap = mutableMapOf<Coordinates, Boolean>()
        var moveVariationIndex = desk.indexOf(firstCell)
        var coefficient = 8

        if (opponentFigureName == 'w') {
            coefficient *= -1
        }

        for (i in 1..moveAmount) {
            moveVariationIndex += coefficient

            if (moveVariationIndex > 64 || moveVariationIndex < 1) {
                break
            }

            if (i == 1) {
                if (desk[moveVariationIndex].column != 8) {
                    if (desk[moveVariationIndex + 1].hasFigure) {
                        canMoveMap[Coordinates(
                            desk[moveVariationIndex + 1].row,
                            desk[moveVariationIndex + 1].column
                        )] = desk[moveVariationIndex + 1].figureName.first() == opponentFigureName
                    } else {
                        canMoveMap[Coordinates(
                            desk[moveVariationIndex + 1].row,
                            desk[moveVariationIndex + 1].column
                        )] = false
                    }
                }
                if (desk[moveVariationIndex].column != 1) {
                    if (desk[moveVariationIndex - 1].hasFigure) {
                        canMoveMap[Coordinates(
                            desk[moveVariationIndex - 1].row,
                            desk[moveVariationIndex - 1].column
                        )] = desk[moveVariationIndex - 1].figureName.first() == opponentFigureName
                    } else {
                        canMoveMap[Coordinates(
                            desk[moveVariationIndex - 1].row,
                            desk[moveVariationIndex - 1].column
                        )] = false
                    }
                }
            }

            if (desk[moveVariationIndex].hasFigure) {
                canMoveMap[Coordinates(desk[moveVariationIndex].row, desk[moveVariationIndex].column)] = false
                break
            } else {
                canMoveMap[Coordinates(desk[moveVariationIndex].row, desk[moveVariationIndex].column)] = true
            }
        }

        return canMoveMap
    }

    // Расчет ходов коня
    private fun knightMoves(firstCell: Cell, moveOrientation: String, moveRoute: String, opponentFigureName: Char) : Map<Coordinates, Boolean> {
        val canMoveMap = mutableMapOf<Coordinates, Boolean>()
        var moveVariationIndex = desk.indexOf(firstCell)
        var coefficient = 0

        if (moveOrientation == "vertical") {
            coefficient = 1
            if (moveRoute == "up") {
                moveVariationIndex += 16
            } else {
                moveVariationIndex -= 16
            }
        } else {
            coefficient = 8
            if (moveRoute == "up") {
                moveVariationIndex += 2
            } else {
                moveVariationIndex -= 2
            }
        }

        for (i in 1..2) {
            if (i == 2) {
                coefficient *= -2
            }

            moveVariationIndex += coefficient

            if (moveVariationIndex > 63 || moveVariationIndex < 1) {
                continue
            } else {
                if (desk[moveVariationIndex].column > firstCell.column + 2 || desk[moveVariationIndex].column < firstCell.column - 2) {
                    continue
                } else {
                    if (desk[moveVariationIndex].hasFigure) {
                        canMoveMap[Coordinates(
                            desk[moveVariationIndex].row,
                            desk[moveVariationIndex].column
                        )] =
                            desk[moveVariationIndex].figureName.first() == opponentFigureName
                    } else {
                        canMoveMap[Coordinates(
                            desk[moveVariationIndex].row,
                            desk[moveVariationIndex].column
                        )] = true
                    }
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

    // Проверка на возможный ход для добавления в список возможных ходов который нужен для отрисовки
    private fun checkToMove(moves: Map<Coordinates, Boolean>) : MutableMap<Coordinates, Boolean> {
        val possibleMoves = mutableMapOf<Coordinates, Boolean>()
        moves.forEach{ move ->
            if (move.value) {
                possibleMoves[move.key] = true
            }
        }

        return possibleMoves
    }

    // Создание шахмотной доски и заполнение фигурами
    private fun deskFill() {
        for (i in 1..8) {
            for (j in 1..8) {
                if (i in 3..6){
                    desk.add(Cell(i,j, false,""))
                } else {
                    when (i) {
                        2 -> {
                            desk.add(Cell(i,j, true,"white_pawn"))
                        }
                        7 -> {
                            desk.add(Cell(i,j, true,"black_pawn"))
                        }
                        else -> {
                            desk.add(Cell(i,j, true,""))
                        }
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

        _activityDesk.value = desk
    }

    // Конец игры
    private fun gameOver() {
        _gameOver.value = true
        desk.clear()
    }

    // Перезапуск игры
    fun gameRestart() {
        desk.clear()
        deskFill()
        walkerName = 'w'
        _gameOver.value = false
    }
}