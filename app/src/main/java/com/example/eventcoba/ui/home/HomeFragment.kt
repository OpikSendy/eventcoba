package com.example.eventcoba.ui.home

//import android.os.Bundle
//import android.util.Log
import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.eventcoba.data.repository.EventRepository
//import com.example.eventcoba.databinding.FragmentHomeBinding
//import com.example.eventcoba.ui.adapter.EventAdapter
//import com.example.eventcoba.ui.utils.Resource

class HomeFragment : Fragment() {
//
//    private var _binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var viewModel: HomeViewModel
//    private lateinit var activeEventAdapter: EventAdapter
//    private lateinit var pastEventAdapter: EventAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Inisialisasi ViewModel
//        val repository = EventRepository()
//        val factory = HomeViewModelFactory(repository)
//        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
//
//        // Setup RecyclerView untuk active events
//        activeEventAdapter = EventAdapter()
//        binding.rvActiveEvents.apply {
//            adapter = activeEventAdapter
//            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        }
//
//        // Setup RecyclerView untuk past events
//        pastEventAdapter = EventAdapter()
//        binding.rvPastEvents.apply {
//            adapter = pastEventAdapter
//            layoutManager = LinearLayoutManager(requireContext())
//        }
//
//        // Cek jika data sudah ada di ViewModel untuk Active Events
//        viewModel.activeEvents.observe(viewLifecycleOwner) { resource ->
//            when (resource) {
//                is Resource.Loading -> {
//                    binding.progressBarActive.visibility = View.VISIBLE
//                }
//                is Resource.Success -> {
//                    binding.progressBarActive.visibility = View.GONE
//                    Log.d("HomeFragment", "Active Events: ${resource.data?.size}")
//                    activeEventAdapter.submitList(resource.data ?: emptyList())
//                }
//                is Resource.Error -> {
//                    binding.progressBarActive.visibility = View.GONE
//                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//
//        // Cek jika data sudah ada di ViewModel untuk Past Events
//        viewModel.pastEvents.observe(viewLifecycleOwner) { resource ->
//            when (resource) {
//                is Resource.Loading -> {
//                    binding.progressBarPast.visibility = View.VISIBLE
//                }
//                is Resource.Success -> {
//                    binding.progressBarPast.visibility = View.GONE
//                    Log.d("HomeFragment", "Past Events: ${resource.data?.size}")
//                    pastEventAdapter.submitList(resource.data ?: emptyList())
//                }
//                is Resource.Error -> {
//                    binding.progressBarPast.visibility = View.GONE
//                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//
//        activeEventAdapter.setOnItemClickListener { event ->
//            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(event)
//            findNavController().navigate(action)
//        }
//
//        pastEventAdapter.setOnItemClickListener { event ->
//            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(event)
//            findNavController().navigate(action)
//        }
//
//        // Pastikan ViewModel hanya mengambil data jika belum ada sebelumnya
//        if (viewModel.activeEvents.value == null) {
//            viewModel.fetchActiveEvents() // Panggil ViewModel untuk ambil data active events
//        }
//
//        if (viewModel.pastEvents.value == null) {
//            viewModel.fetchPastEvents() // Panggil ViewModel untuk ambil data past events
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}

