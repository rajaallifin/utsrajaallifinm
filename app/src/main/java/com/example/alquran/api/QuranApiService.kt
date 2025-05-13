package com.example.alquran.api

import com.example.alquran.model.SurahResponse
import com.example.alquran.model.AyahResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface QuranApiService {
    @GET("surah")  // Sesuaikan endpoint API untuk mendapatkan daftar surah
    fun getSurahList(): Call<SurahResponse>  // Mengembalikan SurahResponse

    @GET("surah/{number}")  // Sudah ada untuk mendapatkan ayat dari surah
    fun getAyahsForSurah(@Path("number") number: Int): Call<AyahResponse>
}
