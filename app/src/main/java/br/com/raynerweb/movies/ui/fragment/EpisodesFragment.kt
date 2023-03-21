package br.com.raynerweb.movies.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import br.com.raynerweb.movies.databinding.FragmentEpisodesBinding
import br.com.raynerweb.movies.ui.adapter.EpisodeAdapter
import br.com.raynerweb.movies.ui.bundle.NavigationBundle.SEASON
import br.com.raynerweb.movies.ui.model.Season
import br.com.raynerweb.movies.ui.viewmodel.EpisodesViewModel
import com.rubensousa.decorator.LinearMarginDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodesFragment : Fragment() {

    private lateinit var binding: FragmentEpisodesBinding
    private val viewModel: EpisodesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Season>(SEASON)?.let {
            subscribe()
            setupViews()
            viewModel.fetchEpisodes(it.tvShowId, it.seasonNumber)
        }
    }

    private fun setupViews() {
//        binding.rvEpisodes.addItemDecoration(
//            LinearMarginDecoration.create(24, RecyclerView.HORIZONTAL)
//        )
    }

    private fun subscribe() {
        viewModel.episodes.observe(viewLifecycleOwner) {
            binding.rvEpisodes.adapter = EpisodeAdapter(it)
        }
    }

}