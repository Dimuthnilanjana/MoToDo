import android.os.Parcel
import android.os.Parcelable

data class Todo(val id: Int, val name: String, val message: String, val date: String) : Parcelable {
    override fun describeContents() = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(message)
        parcel.writeString(date)
    }

    companion object CREATOR : Parcelable.Creator<Todo> {
        override fun createFromParcel(parcel: Parcel): Todo {
            val id = parcel.readInt()
            val name = parcel.readString()!!
            val message = parcel.readString()!!
            val date = parcel.readString()!!
            return Todo(id, name, message, date)
        }

        override fun newArray(size: Int): Array<Todo?> = arrayOfNulls(size)
    }
}
