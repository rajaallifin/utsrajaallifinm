package com.example.alquran
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alquran.adapter.SurahAdapter
import com.example.alquran.api.RetrofitInstance
import com.example.alquran.model.Surah
import com.example.alquran.model.SurahResponse

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var surahAdapter: SurahAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewSurah)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchSurahList()
    }

    private fun fetchSurahList() {
        // Memanggil API secara asynchronous
        RetrofitInstance.api.getSurahList().enqueue(object : Callback<SurahResponse> {
            override fun onResponse(call: Call<SurahResponse>, response: Response<SurahResponse>) {
                // Mengecek apakah response sukses
                if (response.isSuccessful && response.body() != null) {
                    val surahList = response.body()!!.data

                    // Update UI di main thread
                    runOnUiThread {
                        surahAdapter = SurahAdapter(surahList) { surah ->
                            Toast.makeText(this@MainActivity, "Klik: ${surah.englishName}", Toast.LENGTH_SHORT).show()
                            // Navigasi ke DetailSurahActivity atau sesuatu yang lain
                        }
                        recyclerView.adapter = surahAdapter
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Failed to load surah list", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<SurahResponse>, t: Throwable) {
                // Menangani error jika API gagal dipanggil
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }


}
