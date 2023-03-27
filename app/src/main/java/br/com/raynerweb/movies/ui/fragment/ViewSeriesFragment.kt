package br.com.raynerweb.movies.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.raynerweb.movies.R
import br.com.raynerweb.movies.databinding.FragmentViewSeriesBinding
import br.com.raynerweb.movies.ui.adapter.KeywordAdapter
import br.com.raynerweb.movies.ui.adapter.SeasonAdapter
import br.com.raynerweb.movies.ui.bundle.NavigationBundle.SEASON
import br.com.raynerweb.movies.ui.bundle.NavigationBundle.TVSHOW
import br.com.raynerweb.movies.ui.model.TVShow
import br.com.raynerweb.movies.ui.viewmodel.ViewSeriesViewModel
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rubensousa.decorator.LinearMarginDecoration
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ViewSeriesFragment : Fragment() {

    private var tvShowId: Int = 0
    private lateinit var binding: FragmentViewSeriesBinding
    private val viewModel: ViewSeriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewSeriesBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<TVShow>(TVSHOW)?.let { tvShow ->
            subscribe()
            setupViews()

            tvShowId = tvShow.id

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
            binding.executePendingBindings()

            viewModel.fetchSeasons(tvShow.id)
        }
    }

    private fun setupViews() {

        setupPosterRecycleView()

        binding.btMore.setOnClickListener {
            viewModel.fetchKeywords(tvShowId)
        }
    }

    private fun setupPosterRecycleView() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvPoster.layoutManager = layoutManager
        binding.rvPoster.addItemDecoration(
            LinearMarginDecoration.create(24, RecyclerView.HORIZONTAL)
        )
    }

    private fun subscribe() {

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.emptyResult.observe(viewLifecycleOwner) {
            binding.tvEmptyResult.visibility = View.VISIBLE
        }

        viewModel.seasons.observe(viewLifecycleOwner) {
            val adapter = SeasonAdapter(it) { season ->
                val bundle = bundleOf(SEASON to season)
                findNavController()
                    .navigate(R.id.action_viewSeriesFragment_to_episodesFragment, bundle)
            }
            binding.rvPoster.adapter = adapter
        }

        viewModel.keywords.observe(viewLifecycleOwner) { keywords ->
            val inflater = LayoutInflater.from(requireContext())
            val view = inflater.inflate(R.layout.view_dialog_keywords, null)
            view.findViewById<RecyclerView>(R.id.rv_keywords).let { recyclerView ->
                recyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                recyclerView.adapter = KeywordAdapter(keywords)
            }
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(view)
            dialog.show()
        }

        viewModel.emptyKeywords.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(),
                getString(R.string.no_keywords_found),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

}