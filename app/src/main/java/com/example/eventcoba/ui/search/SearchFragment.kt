package com.example.eventcoba.ui.search

//import android.os.Bundle
import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.appcompat.widget.SearchView
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.eventcoba.data.repository.EventRepository
//import com.example.eventcoba.databinding.FragmentSearchBinding
//import com.example.eventcoba.ui.adapter.EventAdapter
//import com.example.eventcoba.ui.utils.Resource

class SearchFragment : Fragment() {
//
//    private lateinit var binding: FragmentSearchBinding
//    private lateinit var searchViewModel: SearchViewModel
//    private lateinit var eventAdapter: EventAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentSearchBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val repository = EventRepository() // Assuming the repository is correctly set up
//        searchViewModel = ViewModelProvider(this, SearchViewModelFactory(repository))[SearchViewModel::class.java]
//
//        eventAdapter = EventAdapter()
//        binding.recyclerView.apply {
//            adapter = eventAdapter
//            layoutManager = LinearLayoutManager(context)
//        }
//
//        // Menampilkan data yang sudah ada di ViewModel saat orientasi berubah
//        searchViewModel.searchResults.observe(viewLifecycleOwner) { resource ->
//            when (resource) {
//                is Resource.Success -> {
//                    eventAdapter.submitList(resource.data)
//                    binding.progressBar.visibility = View.GONE
//                }
//                is Resource.Error -> {
//                    showError(resource.message)
//                    binding.progressBar.visibility = View.GONE
//                }
//                is Resource.Loading -> {
//                    binding.progressBar.visibility = View.VISIBLE
//                }
//            }
//        }
//
//        // Jika ada data sebelumnya, tampilkan tanpa load ulang
//        if (searchViewModel.searchResults.value is Resource.Success) {
//            val data = (searchViewModel.searchResults.value as Resource.Success).data
//            eventAdapter.submitList(data)
//        }
//
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let {
//                    performSearch(it)
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                if (newText.isNullOrEmpty()) {
//                    eventAdapter.submitList(emptyList())
//                } else {
//                    performSearch(newText)
//                }
//                return true
//            }
//        })
//    }
//
//    private fun performSearch(query: String) {
//        searchViewModel.searchEvents(query)
//    }
//
//    private fun showError(message: String?) {
//        Toast.makeText(context, message ?: "An error occurred", Toast.LENGTH_SHORT).show()
//    }
}


