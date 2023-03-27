package br.com.raynerweb.movies.ui.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.raynerweb.movies.R
import br.com.raynerweb.movies.databinding.ViewTvseriesDefaultBinding
import br.com.raynerweb.movies.databinding.ViewTvseriesMainBinding
import br.com.raynerweb.movies.ui.model.TVShow
import coil.load
import coil.transform.RoundedCornersTransformation

class TVSeriesAdapter(
    var series: List<TVShow>,
    val onClickListener: (TVShow) -> Unit
) : RecyclerView.Adapter<TVSeriesAdapter.TVSeriesViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVSeriesViewHolder {
        if (viewType > 0) {
            return DefaultTVSeriesViewHolder(
                ViewTvseriesDefaultBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    )
                )
            )
        }
        return MainTVSeriesViewHolder(
            ViewTvseriesMainBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount() = series.size

    override fun onBindViewHolder(holder: TVSeriesViewHolder, position: Int) {
        holder.bind(series[position])
    }

    abstract class TVSeriesViewHolder(view: View) : ViewHolder(view) {
        abstract fun bind(tvShow: TVShow)
    }

    inner class MainTVSeriesViewHolder(private val binding: ViewTvseriesMainBinding) :
        TVSeriesViewHolder(binding.root) {
        override fun bind(tvShow: TVShow) {
            binding.tvShow = tvShow
            binding.ivPosterPath.load(
                tvShow.poster.ifEmpty { R.drawable.poster }
            ) {
                placeholder(R.drawable.poster)
                crossfade(true)
                transformations(RoundedCornersTransformation(10f))
            }
            binding.ivBackdrop.load(
                tvShow.backdrop.ifEmpty { R.drawable.backdrop }
            ) {
                placeholder(R.drawable.backdrop)
                crossfade(true)
            }
            binding.btMore.setOnClickListener {
                onClickListener.invoke(tvShow)
            }
            binding.executePendingBindings()
        }
    }

    inner class DefaultTVSeriesViewHolder(private val binding: ViewTvseriesDefaultBinding) :
        TVSeriesViewHolder(binding.root) {

        override fun bind(tvShow: TVShow) {
            binding.tvShow = tvShow
            binding.ivPosterPath.load(
                tvShow.poster.ifEmpty { R.drawable.poster }
            ) {
                placeholder(R.drawable.poster)
                crossfade(true)
                transformations(RoundedCornersTransformation(10f))
            }
            binding.root.setOnClickListener {
                onClickListener.invoke(tvShow)
            }
            binding.executePendingBindings()
        }
    }
}