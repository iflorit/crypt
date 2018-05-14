package es.florit.crypt.datasource.repository.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * UI data model
 *
 * Implements Parcelable for passing the object between activities (because in this
 * example we don't use local storage)
 *
 * Created by ismael on 17/3/18.
 */
data class Crypto(
        // tv show list nested fields
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("price") val price: Double,
        @SerializedName("percentage") val percentage: Double,

        @SerializedName("logo") var logo: String = "",
        @SerializedName("rank") var rank: Int = -1

) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeDouble(price)
        parcel.writeDouble(percentage)
        parcel.writeString(logo)
        parcel.writeInt(rank)
    }

    override fun describeContents(): Int {
        return id
    }

    companion object CREATOR : Parcelable.Creator<Crypto> {
        override fun createFromParcel(parcel: Parcel): Crypto {
            return Crypto(parcel)
        }

        override fun newArray(size: Int): Array<Crypto?> {
            return arrayOfNulls(size)
        }
    }
}