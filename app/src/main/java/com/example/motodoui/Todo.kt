package com.example.motodoui

import android.os.Parcel
import android.os.Parcelable

data class Todo(
    var id: String = "",  // Use String for Firestore compatibility
    val name: String = "",
    val message: String = "",
    val date: String = "",
    val time: String = "",
    val priority: Int = 1
) : Parcelable {
    override fun describeContents() = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(message)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeInt(priority)
    }

    companion object CREATOR : Parcelable.Creator<Todo> {
        override fun createFromParcel(parcel: Parcel): Todo {
            val id = parcel.readString()!!
            val name = parcel.readString()!!
            val message = parcel.readString()!!
            val date = parcel.readString()!!
            val time = parcel.readString()!!
            val priority = parcel.readInt()
            return Todo(id, name, message, date, time, priority)
        }

        override fun newArray(size: Int): Array<Todo?> = arrayOfNulls(size)
    }
}
