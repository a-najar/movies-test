package com.geniusforapp.movies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.geniusforapp.movies.R
import com.geniusforapp.movies.databinding.FragmentHomeBinding
import com.geniusforapp.movies.domain.entities.Movies.Movie
import com.geniusforapp.movies.domain.usecases.MoviesItems
import com.geniusforapp.movies.ui.home.adapter.HomeListAdapter
import com.geniusforapp.movies.ui.home.adapter.HomeListItem

class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding

    private val adapter = HomeListAdapter(::onMovieClicked)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentHomeBinding.inflate(inflater, container, false)
            .also { binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObservers()
    }

    private fun initAdapter() {
        binding.listMovies.adapter = adapter
    }

    private fun initObservers() {
        homeViewModel.errorsLiveData.observe(viewLifecycleOwner, ::showError)
        homeViewModel.moviesItems.observe(viewLifecycleOwner, ::showMovies)
        homeViewModel.loadingLiveData.observe(viewLifecycleOwner, ::showLoading)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        binding.swipeRefresh.isEnabled = false
        binding.swipeRefresh.isRefreshing = isLoading
    }

    private fun showMovies(movies: List<MoviesItems>) {
        if (movies.isEmpty()) return
        adapter.submitList(movies.map { HomeListItem.MoviesListItem(it.id, it.movies) })
    }

    private fun showError(throwable: Throwable) {
        Toast.makeText(requireContext(), throwable.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun onMovieClicked(id: Int) {
        findNavController().navigate(HomeFragmentDirections.goToDetails(id))
    }
}