package br.com.raynerweb.movies.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.raynerweb.movies.R

class KeywordAdapter(
    var keywords: List<String>
) : RecyclerView.Adapter<KeywordAdapter.KeywordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.view_keywords, parent, false
        )
        return KeywordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        holder.bind(keywords[position])
    }

    override fun getItemCount() = keywords.size

    inner class KeywordViewHolder(itemView: View) : ViewHolder(itemView) {
        fun bind(item: String) {
            itemView.findViewById<TextView>(R.id.tv_keyword).text = item
        }
    }
}