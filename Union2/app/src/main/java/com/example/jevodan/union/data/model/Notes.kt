package com.example.jevodan.union.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Notes(val id: String = UUID.randomUUID().toString(),
                 val color: Color = Color.WHITE,
                 val title: String = "Запись",
                 val desc: String = "Шифрованная записка",
                 val lastChanged: Date = Date()) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass !=other?.javaClass) return false

        other as Notes

        if(id != other.id) return false
        return true
    }
}