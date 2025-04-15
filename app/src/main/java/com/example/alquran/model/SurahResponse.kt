package com.example.alquran.model

import android.os.Parcel
import android.os.Parcelable

// Model untuk response dari API
data class SurahResponse(
    val code: Int,
    val status: String,
    val data: List<Surah>
)

// Model untuk satu Surah
data class Surah(
    val number: Int,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val numberOfAyahs: Int,
    val revelationType: String,
    val ayahs: List<Ayah>? = null // Tambahan: daftar ayah
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.createTypedArrayList(Ayah.CREATOR) // Baca list Ayah
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(number)
        parcel.writeString(name)
        parcel.writeString(englishName)
        parcel.writeString(englishNameTranslation)
        parcel.writeInt(numberOfAyahs)
        parcel.writeString(revelationType)
        parcel.writeTypedList(ayahs) // Tulis list Ayah
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Surah> {
        override fun createFromParcel(parcel: Parcel): Surah = Surah(parcel)
        override fun newArray(size: Int): Array<Surah?> = arrayOfNulls(size)
    }
}
