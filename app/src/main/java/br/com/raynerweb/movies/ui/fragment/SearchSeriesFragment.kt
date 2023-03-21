package br.com.raynerweb.movies.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.raynerweb.movies.R
import br.com.raynerweb.movies.databinding.FragmentSearchSeriesBinding
import br.com.raynerweb.movies.ui.adapter.TVSeriesAdapter
import br.com.raynerweb.movies.ui.bundle.NavigationBundle.TVSHOW
import br.com.raynerweb.movies.ui.viewmodel.SearchSeriesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchSeriesFragment : Fragment() {

    lateinit var binding: FragmentSearchSeriesBinding
    private val viewModel: SearchSeriesViewModel by viewModels()
    private var queryFilter = ""

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
    }

    private fun subscribe() {

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.emptyResult.observe(viewLifecycleOwner) {
            binding.tvEmptyResult.visibility = View.VISIBLE
            binding.rvTvSeries.visibility = View.GONE
        }

        viewModel.tvSeries.observe(viewLifecycleOwner) {
            binding.tvEmptyResult.visibility = View.GONE
            binding.rvTvSeries.visibility = View.VISIBLE
            binding.rvTvSeries.adapter = TVSeriesAdapter(it) { tvShow ->
                val bundle = bundleOf(TVSHOW to tvShow)
                findNavController()
                    .navigate(R.id.action_searchSeriesFragment_to_viewSeriesFragment, bundle)
            }
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

        searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
            .setOnClickListener {
                viewModel.fetchPopular()
                queryFilter = ""
                searchView.setQuery("", false)
            }

        searchView.apply {
            maxWidth = Integer.MAX_VALUE
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.fetchByFilter(queryFilter)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        queryFilter = it
                    }
                    return true
                }
            })

        }
    }

}