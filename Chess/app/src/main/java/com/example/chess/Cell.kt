package com.example.chess

data class Cell(
    var row: Int,
    var column: Int,
    var hasFigure: Boolean,
    var figureName: String,
)