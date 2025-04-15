package com.example.alquran.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alquran.R
import com.example.alquran.model.Surah

class SurahAdapter(
    private val surahList: List<Surah>,
    private val onItemClick: (Surah) -> Unit
) : RecyclerView.Adapter<SurahAdapter.SurahViewHolder>() {

    inner class SurahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSurahName: TextView = itemView.findViewById(R.id.tvSurahName)
        val tvSurahTranslation: TextView = itemView.findViewById(R.id.tvSurahTranslation)

        fun bind(surah: Surah) {
            tvSurahName.text = "${surah.number}. ${surah.englishName}"
            tvSurahTranslation.text = surah.englishNameTranslation

            itemView.setOnClickListener { onItemClick(surah) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_surah, parent, false)
        return SurahViewHolder(view)
    }

    override fun onBindViewHolder(holder: SurahViewHolder, position: Int) {
        holder.bind(surahList[position])
    }

    override fun getItemCount(): Int = surahList.size
}
