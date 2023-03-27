package br.com.raynerweb.movies.ui.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.raynerweb.movies.R
import br.com.raynerweb.movies.databinding.ViewSeriesBinding
import br.com.raynerweb.movies.ui.model.TVShow
import coil.load
import coil.transform.RoundedCornersTransformation

class SeriesAdapter(
    val series: List<TVShow>,
    val onClickListener: (TVShow) -> Unit
) : RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SeriesViewHolder(
            ViewSeriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.bind(series[position])
    }

    override fun getItemCount() = series.size

    inner class SeriesViewHolder(private val binding: ViewSeriesBinding) :
        ViewHolder(binding.root) {

        fun bind(tvShow: TVShow) {
            binding.tvShow = tvShow
            binding.ivPoster.load(
                tvShow.poster.ifEmpty { R.drawable.poster }
            ) {
                placeholder(R.drawable.poster)
                crossfade(true)
                transformations(RoundedCornersTransformation(20f))
            }
            binding.root.setOnClickListener {
                onClickListener.invoke(tvShow)
            }
            binding.executePendingBindings()
        }
    }
}