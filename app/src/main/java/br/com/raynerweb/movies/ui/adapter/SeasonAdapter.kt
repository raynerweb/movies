package br.com.raynerweb.movies.ui.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.raynerweb.movies.R
import br.com.raynerweb.movies.databinding.ViewPosterBinding
import br.com.raynerweb.movies.ui.model.Season
import coil.load
import coil.transform.RoundedCornersTransformation

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
            binding.ivPoster.load(
                if (TextUtils.isEmpty(season.poster)) R.drawable.poster else season.poster
            ) {
                placeholder(R.drawable.poster)
                crossfade(true)
                transformations(RoundedCornersTransformation(10f))
            }
            binding.root.setOnClickListener {
                onClickListener.invoke(season)
            }
            binding.executePendingBindings()
        }
    }

}