package com.example.alquran.model

data class AyahTranslationResponse(
    val data: SurahTranslation
)

data class SurahTranslation(
    val number: Int,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val numberOfAyahs: Int,
    val revelationType: String,
    val ayahs: List<AyahTranslation>
)

data class AyahTranslation(
    val number: Int,
    val text: String,
    val numberInSurah: Int,
    val juz: Int,
    val manzil: Int,
    val page: Int,
    val ruku: Int,
    val hizbQuarter: Int,
    val sajda: Any
)
