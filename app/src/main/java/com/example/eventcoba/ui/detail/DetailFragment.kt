package com.example.eventcoba.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.eventcoba.R
import com.example.eventcoba.data.model.ListEventsItem
import com.example.eventcoba.data.model.toFavoriteEvent
import com.example.eventcoba.databinding.FragmentDetailBinding
import com.example.eventcoba.ui.favorite.FavoriteViewModel
import com.example.eventcoba.ui.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil argument yang dikirim dari ActiveFragment
        val event = arguments?.getParcelable<ListEventsItem>("event")

        // Set detail event di ViewModel
        event?.let {
            viewModel.setEventDetails(it)
        }

        // Observe data dari DetailViewModel
        viewModel.eventDetails.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { event ->
                        binding.textEventName.text = event.name
                        binding.textOrganizer.text = event.ownerName
                        binding.textEventTime.text = event.beginTime
                        binding.textQuota.text = (event.quota - event.registrants).toString()
                        binding.textDescription.text = Html.fromHtml(event.description, Html.FROM_HTML_MODE_LEGACY)

                        Glide.with(this)
                            .load(event.mediaCover)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.error)
                            .into(binding.imageEvent)

                        // Handle opening event link
                        binding.buttonOpenLink.setOnClickListener {
                            event.link.let { url ->
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(requireContext(), "Invalid URL", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        event?.let { currentEvent ->
            val favoriteEvent = currentEvent.toFavoriteEvent()

            // Observe isFavorite status
            favoriteViewModel.isFavorite(favoriteEvent.id).observe(viewLifecycleOwner) { isFavorite ->
                binding.fabFavorite.setImageResource(
                    if (isFavorite) R.drawable.baseline_favorite_24
                    else R.drawable.baseline_favorite_border_24
                )

                // Set FAB listener hanya sekali, untuk menghindari multiple clicks
                binding.fabFavorite.setOnClickListener {
                    if (isFavorite) {
                        favoriteViewModel.removeFavorite(favoriteEvent)
                        Toast.makeText(requireContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show()
                    } else {
                        favoriteViewModel.addFavorite(favoriteEvent)
                        Toast.makeText(requireContext(), "Added to Favorites", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
