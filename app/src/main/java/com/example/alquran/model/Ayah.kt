package com.example.alquran.model

import android.os.Parcel
import android.os.Parcelable

// Data model untuk Ayah
data class Ayah(
    val number: Int,                // Nomor ayah
    val text: String,               // Teks ayah
    val numberInSurah: Int,         // Nomor ayah dalam surah
    val juz: Int,                   // Juz
    val manzil: Int,                // Manzil
    val page: Int,                  // Nomor halaman
    val ruku: Int,                  // Ruku
    val hizbQuarter: Int,           // Hizb atau seperempat hizb
    val sajda: Boolean              // Apakah ayah ini mengandung sajda
) : Parcelable {

    // Konstruktor untuk membaca data dari Parcel
    constructor(parcel: Parcel) : this(
        parcel.readInt(),            // number
        parcel.readString() ?: "",   // text (null jika tidak ada)
        parcel.readInt(),            // numberInSurah
        parcel.readInt(),            // juz
        parcel.readInt(),            // manzil
        parcel.readInt(),            // page
        parcel.readInt(),            // ruku
        parcel.readInt(),            // hizbQuarter
        parcel.readByte() != 0.toByte()  // sajda (boolean, 0 = false, selain itu true)
    )

    // Menulis data ke Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(number)
        parcel.writeString(text)
        parcel.writeInt(numberInSurah)
        parcel.writeInt(juz)
        parcel.writeInt(manzil)
        parcel.writeInt(page)
        parcel.writeInt(ruku)
        parcel.writeInt(hizbQuarter)
        parcel.writeByte(if (sajda) 1 else 0)  // Sajda disimpan sebagai 1 (true) atau 0 (false)
    }

    // Digunakan untuk mendeskripsikan konten Parcelable (biasanya 0)
    override fun describeContents(): Int = 0

    // Companion object untuk menciptakan objek Parcelable
    companion object CREATOR : Parcelable.Creator<Ayah> {
        override fun createFromParcel(parcel: Parcel): Ayah = Ayah(parcel)
        override fun newArray(size: Int): Array<Ayah?> = arrayOfNulls(size)
    }
}
