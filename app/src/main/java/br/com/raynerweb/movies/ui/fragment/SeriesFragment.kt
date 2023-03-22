package br.com.raynerweb.movies.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import br.com.raynerweb.movies.R
import br.com.raynerweb.movies.databinding.FragmentSeriesBinding
import br.com.raynerweb.movies.databinding.ViewSearchBinding
import br.com.raynerweb.movies.ui.adapter.SeriesAdapter
import br.com.raynerweb.movies.ui.bundle.NavigationBundle
import br.com.raynerweb.movies.ui.model.TVShow
import br.com.raynerweb.movies.ui.viewmodel.SeriesViewModel
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rubensousa.decorator.ColumnProvider
import com.rubensousa.decorator.GridMarginDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesFragment : Fragment() {

    private lateinit var binding: FragmentSeriesBinding
    private lateinit var highlighted: TVShow
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private val viewModel: SeriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeriesBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()
        setupViews()

        viewModel.search()
    }

    private fun setupRecycleView() {
        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvSeries.layoutManager = layoutManager
        val decoration =
            GridMarginDecoration.create(16, columnProvider = object : ColumnProvider {
                override fun getNumberOfColumns(): Int = 3
            })
        binding.rvSeries.addItemDecoration(decoration)
    }

    private fun setupBottomSheetDialog() {
        val inflater = LayoutInflater.from(requireContext())
        val dialogBinding = ViewSearchBinding.inflate(inflater)
        dialogBinding.fragment = this
        dialogBinding.viewModel = viewModel
        dialogBinding.lifecycleOwner = this

        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(dialogBinding.root)
    }

    fun clearFilter(view: View) {
        viewModel.clearFilter()
    }

    fun saerch(view: View) {
        viewModel.search()
        bottomSheetDialog.dismiss()
    }

    private fun setupViews() {

        setupRecycleView()
        setupBottomSheetDialog()

        binding.btMore.setOnClickListener {
            val bundle = bundleOf(NavigationBundle.TVSHOW to highlighted)
            findNavController()
                .navigate(R.id.action_seriesFragment_to_viewSeriesFragment, bundle)
        }

        binding.fabFilter.setOnClickListener {
            bottomSheetDialog.show()
        }
    }

    private fun subscribe() {

        viewModel.tvSeries.observe(viewLifecycleOwner) {
            binding.rvSeries.adapter = SeriesAdapter(it) { tvShow ->
                val bundle = bundleOf(NavigationBundle.TVSHOW to tvShow)
                findNavController()
                    .navigate(R.id.action_seriesFragment_to_viewSeriesFragment, bundle)
            }
        }

        viewModel.highlight.observe(viewLifecycleOwner) {
            highlighted = it

            binding.tvShow = it
            binding.ivBackdrop.load(
                if (TextUtils.isEmpty(it.backdrop)) R.drawable.backdrop else it.backdrop
            ) {
                placeholder(R.drawable.backdrop)
                crossfade(true)
            }

            binding.executePendingBindings()
        }

    }

}