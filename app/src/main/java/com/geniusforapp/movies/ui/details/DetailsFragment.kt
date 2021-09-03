package com.geniusforapp.movies.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.geniusforapp.movies.BuildConfig
import com.geniusforapp.movies.R
import com.geniusforapp.movies.databinding.DetailsFragmentBinding
import com.geniusforapp.movies.domain.entities.Movie
import com.geniusforapp.movies.ui.ktx.printString

class DetailsFragment : Fragment() {
    private lateinit var binding: DetailsFragmentBinding

    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel by viewModels<DetailsViewModel> { DetailsFactory(args.id) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DetailsFragmentBinding.inflate(inflater, container, false)
        .also { binding = it }
        .root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        viewModel.movieDetails.observe(viewLifecycleOwner, ::showDetails)
        viewModel.errorsLiveData.observe(viewLifecycleOwner, ::showError)
        viewModel.loadingLiveData.observe(viewLifecycleOwner, ::showLoading)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        binding.swipeRefresh.isEnabled = false
        binding.swipeRefresh.isRefreshing = isLoading
    }


    private fun showError(throwable: Throwable?) {
        Toast.makeText(requireContext(), throwable.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun showDetails(movie: Movie) {
        with(binding) {
            imageCover.load(BuildConfig.IMAGE_URL.plus(movie.backdropPath))
            imagePoster.load(BuildConfig.IMAGE_URL.plus(movie.posterPath))
            textViewTitle.text = movie.title
            textViewOverView.text = movie.overview
            textViewGeneres.text = movie.genres.map { it.name }.printString()
            textViewVoating.text = getString(
                R.string.text_voting,
                movie.voteAverage.toString(),
                movie.voteCount.toString()
            )
        }
    }

}