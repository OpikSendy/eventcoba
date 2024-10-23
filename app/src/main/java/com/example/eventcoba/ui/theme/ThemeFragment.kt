package com.example.eventcoba.ui.theme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.eventcoba.data.worker.DailyReminderViewModel
import com.example.eventcoba.databinding.FragmentThemeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ThemeFragment : Fragment() {
    private lateinit var binding: FragmentThemeBinding
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val dailyReminderViewModel: DailyReminderViewModel by viewModels()

    private var isSwitchChanging = false  // Flag untuk menghindari trigger ganda

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThemeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupThemeSwitch()
        setupDailyReminderSwitch()
    }

    private fun setupThemeSwitch() {
        // Observe the theme changes and update the switch accordingly
        viewLifecycleOwner.lifecycleScope.launch {
            settingsViewModel.themeFlow.collect { isDarkMode ->
                if (binding.switchTheme.isChecked != isDarkMode) {
                    isSwitchChanging = true  // Hindari trigger listener ganda
                    binding.switchTheme.isChecked = isDarkMode
                    isSwitchChanging = false  // Reset flag setelah perubahan
                }
            }
        }

        // Handle perubahan switch untuk menyimpan preferensi tema
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (!isSwitchChanging) {
                viewLifecycleOwner.lifecycleScope.launch {
                    settingsViewModel.themeFlow.firstOrNull()?.let { currentTheme ->
                        if (currentTheme != isChecked) {
                            settingsViewModel.saveTheme(isChecked, requireActivity())
                        }
                    }
                }
            }
        }
    }

    private fun setupDailyReminderSwitch() {
        // Observe status reminder dan atur switch sesuai dengan preferensi yang tersimpan
        viewLifecycleOwner.lifecycleScope.launch {
            settingsViewModel.reminderFlow.collect { isReminderEnabled ->
                binding.switchReminder.isChecked = isReminderEnabled
            }
        }

        // Menyimpan status reminder ketika switch reminder diubah
        binding.switchReminder.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.saveReminder(isChecked)

            dailyReminderViewModel.setupDailyReminder(requireContext(), isChecked)
        }
    }
}