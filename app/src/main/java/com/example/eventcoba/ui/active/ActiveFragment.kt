package com.example.eventcoba.ui.active

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventcoba.databinding.FragmentActiveBinding
import com.example.eventcoba.ui.adapter.EventAdapter
import com.example.eventcoba.ui.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActiveFragment : Fragment() {

    private var _binding: FragmentActiveBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ActiveViewModel by viewModels()
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        eventAdapter = EventAdapter()
        binding.recyclerViewActive.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Observing the data from ViewModel
        viewModel.events.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    eventAdapter.submitList(resource.data)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Pastikan ViewModel hanya mengambil data jika belum ada sebelumnya
        if (viewModel.events.value == null) {
            viewModel.fetchEvents() // Panggil ViewModel untuk ambil data active events
        }

        // Handle item click
        eventAdapter.setOnItemClickListener { event ->
            val action = ActiveFragmentDirections.actionActiveFragmentToDetailFragment(event)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}