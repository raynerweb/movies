package br.com.raynerweb.movies.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.raynerweb.movies.R
import br.com.raynerweb.movies.databinding.FragmentSearchSeriesBinding
import br.com.raynerweb.movies.ui.adapter.TVSeriesAdapter
import br.com.raynerweb.movies.ui.viewmodel.SearchSeriesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchSeriesFragment : Fragment() {

    lateinit var binding: FragmentSearchSeriesBinding
    private val viewModel: SearchSeriesViewModel by viewModels()

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
        setupToolbar()

//        tvSeriesAdapter = TVSeriesAdapter(mutableListOf())
//        binding.rvTvSeries.adapter = tvSeriesAdapter
    }

    private fun subscribe() {

        viewModel.tvSeries.observe(viewLifecycleOwner) {
            binding.rvTvSeries.adapter = TVSeriesAdapter(it)
        }

    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_filter, menu)

        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))

        searchView.apply {
            maxWidth = Integer.MAX_VALUE
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // TODO to be implemented
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
    }

}