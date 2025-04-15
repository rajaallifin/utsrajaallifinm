package com.example.alquran

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView // Pastikan ini ada
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.alquran.api.RetrofitInstance
import com.example.alquran.model.AyahResponse
import com.example.alquran.model.Surah
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import kotlinx.coroutines.async

class DetailSurahActivity : AppCompatActivity() {

    private lateinit var tvAyahList: TextView // Pastikan kamu mendeklarasikan TextView ini
    private lateinit var tvSurahName: TextView
    private lateinit var tvSurahTranslation: TextView
    private lateinit var tvRevelationType: TextView
    private lateinit var tvNumberOfAyahs: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_surah)

        // Inisialisasi TextViews
        tvSurahName = findViewById(R.id.tvSurahName)
        tvAyahList = findViewById(R.id.tvAyahList) // Pastikan ada di layout XML
        tvSurahTranslation = findViewById(R.id.tvSurahTranslation)
        tvRevelationType = findViewById(R.id.tvRevelationType)
        tvNumberOfAyahs = findViewById(R.id.tvNumberOfAyahs)

        // Ambil Surah yang dikirim lewat Intent
        val surah = intent.getParcelableExtra<Surah>("surah_key")

        if (surah != null) {
            tvSurahName.text = "${surah.number}. ${surah.englishName}"
            tvSurahTranslation.text = "Translation: ${surah.englishNameTranslation}"
            tvRevelationType.text =  "Revelation Type: ${surah.revelationType}"
            tvNumberOfAyahs.text = "Number of Ayahs: ${surah.numberOfAyahs}"

            // Ambil ayah untuk surah ini
            fetchAyahsForSurah(surah.number)
        }
    }

    private fun fetchAyahsForSurah(surahNumber: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Launch concurrent requests using async
                val ayahResponseDeferred = async {
                    RetrofitInstance.api.getAyahsForSurah(surahNumber).execute()
                }
                val translationResponseDeferred = async {
                    RetrofitInstance.api.getTranslationAyahs(surahNumber).execute()
                }

                // Await both responses
                val ayahResponse = ayahResponseDeferred.await()
                val translationResponse = translationResponseDeferred.await()

                if (ayahResponse.isSuccessful && translationResponse.isSuccessful &&
                    ayahResponse.body() != null && translationResponse.body() != null
                ) {

                    // Extract lists from each response (adjust based on your response model)
                    val ayahs = ayahResponse.body()!!.data.ayahs
                    val translations = translationResponse.body()!!.data.ayahs

                    // Check if both lists are not empty
                    if (ayahs.isNotEmpty() && translations.isNotEmpty()) {
                        // Combine each ayah with its translation by zipping the lists
                        val combinedText = ayahs.zip(translations) { ayah, translation ->
                            "${ayah.numberInSurah}. ${ayah.text}\n(${translation.text})"
                        }.joinToString(separator = "\n\n")

                        // Update the TextView on the main thread
                        withContext(Dispatchers.Main) {
                            tvAyahList.text = combinedText
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            tvAyahList.text = "No Ayahs available for this Surah."
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        tvAyahList.text = "Failed to load Ayahs."
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    tvAyahList.text = "Error: ${e.message}"
                }
            }
        }
    }
}
