package com.example.chess

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import androidx.annotation.RequiresApi
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cell(
    var row: Int,
    var column: Int,
    var hasFigure: Boolean,
    var figureName: String,
) : Parcelable {

    companion object : Parceler<Cell> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun Cell.write(parcel: Parcel, flags: Int) {
            parcel.writeInt(row)
            parcel.writeInt(column)
            parcel.writeBoolean(hasFigure)
            parcel.writeString(figureName)
        }

        @RequiresApi(Build.VERSION_CODES.Q)
        override fun create(parcel: Parcel): Cell {
            return Cell(
                parcel.readInt(),
                parcel.readInt(),
                parcel.readBoolean(),
                parcel.readString()!!
            )
        }
    }
}
