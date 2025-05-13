package com.example.alquran

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

class DetailSurahActivity : AppCompatActivity() {

    private lateinit var tvAyahList: TextView // Pastikan kamu mendeklarasikan TextView ini
    private lateinit var tvSurahName: TextView
    private lateinit var tvSurahDetails: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_surah)

        // Inisialisasi TextViews
        tvSurahName = findViewById(R.id.tvSurahName)
        tvSurahDetails = findViewById(R.id.tvSurahDetails)
        tvAyahList = findViewById(R.id.tvAyahList) // Pastikan ada di layout XML

        // Ambil Surah yang dikirim lewat Intent
        val surah = intent.getParcelableExtra<Surah>("surah_key")

        if (surah != null) {
            tvSurahName.text = "${surah.number}. ${surah.englishName}"
            tvSurahDetails.text = "Translation: ${surah.englishNameTranslation}\n\n" +
                    "Revelation Type: ${surah.revelationType}\n" +
                    "Number of Ayahs: ${surah.numberOfAyahs}"

            // Ambil ayah untuk surah ini
            fetchAyahsForSurah(surah.number)
        }
    }

    private fun fetchAyahsForSurah(surahNumber: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Panggil API untuk mengambil ayah untuk surah
                val response = RetrofitInstance.api.getAyahsForSurah(surahNumber)

                // Menangani respons
                response.enqueue(object : Callback<AyahResponse> {
                    override fun onResponse(call: Call<AyahResponse>, response: Response<AyahResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            val surahWithAyahs = response.body()!!.data
                            val ayahs = surahWithAyahs.ayahs
                            Log.d("DetailSurahActivity", "Response body: ${response.body()}")

                            if (ayahs.isNotEmpty()) {
                                val ayahText = ayahs.joinToString("\n\n") { "${it.numberInSurah}. ${it.text}" }
                                tvAyahList.text = ayahText
                            } else {
                                tvAyahList.text = "No Ayahs available for this Surah."
                            }
                        } else {
                            tvAyahList.text = "Failed to load ayahs."
                        }
                    }

                    override fun onFailure(call: Call<AyahResponse>, t: Throwable) {
                        Log.e("DetailSurahActivity", "API call failed", t)  // Log error
                        tvAyahList.text = "Error: ${t.message}"
                    }
                })

            } catch (e: Exception) {
                // Tangani exception jika ada
                withContext(Dispatchers.Main) {
                    tvAyahList.text = "Error: ${e.message}"
                }
            }
        }
    }


}
