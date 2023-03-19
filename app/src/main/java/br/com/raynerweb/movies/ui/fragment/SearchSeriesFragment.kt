package br.com.raynerweb.movies.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.raynerweb.movies.databinding.FragmentSearchSeriesBinding
import br.com.raynerweb.movies.ui.adapter.TVSeriesAdapter
import br.com.raynerweb.movies.ui.viewmodel.SearchSeriesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchSeriesFragment : Fragment() {

    lateinit var binding: FragmentSearchSeriesBinding
    private val viewModel: SearchSeriesViewModel by viewModels()

    private lateinit var tvSeriesAdapter: TVSeriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchSeriesBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        subscribe()

        viewModel.fetchPopular()
    }

    private fun setupViews() {
        tvSeriesAdapter = TVSeriesAdapter(mutableListOf())
        binding.rvTvSeries.adapter = tvSeriesAdapter
    }

    private fun subscribe() {

        viewModel.tvSeries.observe(viewLifecycleOwner) {
            tvSeriesAdapter.update(it.toMutableList())
        }

    }

}