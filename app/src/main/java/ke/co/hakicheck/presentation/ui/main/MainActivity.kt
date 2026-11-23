package ke.co.hakicheck.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ke.co.hakicheck.HakiCheckApplication
import ke.co.hakicheck.R
import ke.co.hakicheck.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply saved Dark Mode preference on startup
        val settingsRepo = (application as HakiCheckApplication).settingsRepository
        val profile = settingsRepo.loadProfile()
        if (profile.isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        // Dynamic header title update based on current destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.tvHeaderTitle.text = when (destination.id) {
                R.id.navigation_verify -> getString(R.string.app_name)
                R.id.navigation_knowledge -> getString(R.string.knowledge_header)
                R.id.navigation_trending -> getString(R.string.trending_header)
                R.id.navigation_saved -> getString(R.string.saved_header)
                R.id.navigation_settings -> getString(R.string.settings_header)
                else -> getString(R.string.app_name)
            }
        }
    }
}
