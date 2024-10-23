package com.example.eventcoba.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventcoba.data.model.toListEventsItem
import com.example.eventcoba.databinding.FragmentFavoriteBinding
import com.example.eventcoba.ui.adapter.EventAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        eventAdapter = EventAdapter()
        binding.recyclerViewFavorites.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Mengamati data favorit
        viewModel.allFavorites.observe(viewLifecycleOwner) { favoriteEvents ->
            eventAdapter.submitList(favoriteEvents.map { it.toListEventsItem() })
            binding.textNoFavorites.visibility = if (favoriteEvents.isEmpty()) View.VISIBLE else View.GONE
        }

        // Menangani klik pada item event
        eventAdapter.setOnItemClickListener { event ->
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(event)
            findNavController().navigate(action)
        }
    }
}

