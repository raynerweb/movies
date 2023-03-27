package br.com.raynerweb.movies.ui.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.raynerweb.movies.R
import br.com.raynerweb.movies.databinding.ViewEpisodeBinding
import br.com.raynerweb.movies.ui.model.Episode
import coil.load
import coil.transform.RoundedCornersTransformation

class EpisodeAdapter(
    var episodes: List<Episode>
) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EpisodeViewHolder(
            ViewEpisodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(episodes[position])
    }

    override fun getItemCount() = episodes.size

    inner class EpisodeViewHolder(private val binding: ViewEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(episode: Episode) {
            binding.episode = episode
            binding.ivPicture.load(
                episode.picture.ifEmpty { R.drawable.episode }
            ) {
                placeholder(R.drawable.episode)
                crossfade(true)
                transformations(RoundedCornersTransformation(10f))
            }
            binding.executePendingBindings()
        }
    }
}