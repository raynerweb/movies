package br.com.raynerweb.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.raynerweb.movies.databinding.ViewPosterBinding
import br.com.raynerweb.movies.ext.loadRoundedFrom
import br.com.raynerweb.movies.ui.model.Season

class SeasonAdapter(
    var seasons: List<Season>,
    val onClickListener: (Season) -> Unit
) : RecyclerView.Adapter<SeasonAdapter.PosterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PosterViewHolder(
            ViewPosterBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        holder.bind(seasons[position])
    }

    override fun getItemCount() = seasons.size

    inner class PosterViewHolder(private val binding: ViewPosterBinding) :
        ViewHolder(binding.root) {

        fun bind(season: Season) {
            binding.season = season
            binding.ivPoster.loadRoundedFrom(season.poster)
            binding.executePendingBindings()
        }
    }

}