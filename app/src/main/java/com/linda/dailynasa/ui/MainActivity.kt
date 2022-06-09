package com.linda.dailynasa.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.linda.dailynasa.R
import com.linda.dailynasa.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var mDrawerToggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        setDrawer()
        setupNavController()
    }

    private fun setDrawer() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            navController.graph, binding.drawerLayout
        )
        NavigationUI.setupWithNavController(binding.drawerLayoutMenu.navView,navController)

        mDrawerToggle =
            ActionBarDrawerToggle(this, binding.drawerLayout
                , binding.appBarMain.toolbar, R.string.open, R.string.close)

        binding.drawerLayout.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
    }

    private fun setupNavController() {
        findNavController(R.id.nav_host_fragment_content_main).addOnDestinationChangedListener {
                navController: NavController, _: NavDestination, _: Bundle? ->
            when (navController.currentDestination?.id) {
                R.id.nav_home -> {}
                R.id.nav_gallery -> {}
                R.id.nav_slideshow -> {}
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)

//            viewModel.currentFragmentType.value = when (navController.currentDestination?.id) {
//                R.id.homeFragment -> CurrentFragmentType.HOME
//                R.id.diariesFragment -> CurrentFragmentType.DIARY
//                R.id.storesFragment -> CurrentFragmentType.STORES
//                R.id.profileFragment -> CurrentFragmentType.PROFILE
//                R.id.addDiaryFragment -> CurrentFragmentType.ADD
//                R.id.templateFragment -> CurrentFragmentType.TEMPLATE
//                else -> viewModel.currentFragmentType.value
//            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}