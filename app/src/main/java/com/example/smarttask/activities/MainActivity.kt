package com.example.smarttask.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.smarttask.R
import com.example.smarttask.databinding.ActivityMainBinding
import com.example.smarttask.viewModels.TitleViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var calendar: Calendar = Calendar.getInstance()
    private var currentDate: Calendar = Calendar.getInstance()
    private val titleViewModel: TitleViewModel by viewModels()

    private val mainDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            if (destination.id != R.id.homeFragment)
                binding.toolbarTitle.text = destination.label.toString()
            else
                updateTitle()
            binding.ivBack.visibility =
                if (destination.id == R.id.homeFragment) View.VISIBLE else View.GONE
            binding.ivFwd.visibility =
                if (destination.id == R.id.homeFragment) View.VISIBLE else View.GONE
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener(mainDestinationChangedListener)

        binding.ivBack.setOnClickListener {
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            updateTitle()
        }

        binding.ivFwd.setOnClickListener {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            updateTitle()
        }

        titleViewModel.title.observe(this) { title ->
            // Here you can also update the toolbar title if needed
            binding.toolbarTitle.text = title
        }
    }

    private fun updateTitle() {
        val dateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault())
        titleViewModel.setDate(calendar.time)
        if (calendar.time == currentDate.time)
            titleViewModel.setTitle("Today")
        else
            titleViewModel.setTitle(dateFormat.format(calendar.time))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment)
            ?.findNavController()
        return navController?.navigateUp() ?: super.onSupportNavigateUp()
    }
}