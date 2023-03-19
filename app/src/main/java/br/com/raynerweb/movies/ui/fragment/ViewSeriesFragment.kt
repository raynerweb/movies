package br.com.raynerweb.movies.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.raynerweb.movies.databinding.FragmentViewSeriesBinding
import br.com.raynerweb.movies.ext.loadFrom
import br.com.raynerweb.movies.ui.bundle.NavigationBundle.TVSHOW
import br.com.raynerweb.movies.ui.model.TVShow

class ViewSeriesFragment : Fragment() {

    private lateinit var binding: FragmentViewSeriesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewSeriesBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<TVShow>(TVSHOW)?.let { tvShow ->
            binding.tvShow = tvShow
            binding.ivPosterPath.loadFrom(tvShow.poster)
            binding.ivBackdrop.loadFrom(tvShow.backdrop)
            binding.executePendingBindings()
        }

    }

}