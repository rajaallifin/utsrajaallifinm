package com.example.alquran.model

data class AyahResponse(
    val code: Int,
    val status: String,
    val data: SurahWithAyahs
)

data class SurahWithAyahs(
    val number: Int,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val numberOfAyahs: Int,
    val revelationType: String,
    val ayahs: List<Ayah>
)
