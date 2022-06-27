package com.linda.dailynasa.ui

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
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
import com.linda.dailynasa.common.CurrentFragmentType
import com.linda.dailynasa.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    private lateinit var mDrawerToggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        setDrawer()
        setupNavController()

        viewModel.currentFragmentType.observe(this) {
            it?.let {
                binding.appBarMain.toolbar.title = it.value
            }
        }
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
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupNavController() {
        findNavController(R.id.nav_host_fragment_content_main).addOnDestinationChangedListener {
                navController: NavController, _: NavDestination, _: Bundle? ->
            when (navController.currentDestination?.id) {
                R.id.nav_home -> {
                    showToolbar(true)
                    viewModel.currentFragmentType.value = CurrentFragmentType.HOME
                }
                R.id.nav_gallery -> {
                    showToolbar(true)
                    viewModel.currentFragmentType.value = CurrentFragmentType.GALLERY
                }
                R.id.roverDetailFragment,R.id.galleryDetailFragment -> {
                    showToolbar(false)
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun showToolbar(isShow:Boolean) {
        binding.appBarMain.toolbar.visibility = if (isShow) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun hideKeyboard(context: Context) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            hideKeyboard(this)
            if (ev?.action == MotionEvent.ACTION_DOWN) {
                val v = currentFocus
                if (v is EditText) {
                    val outRect = Rect()
                    v.getGlobalVisibleRect(outRect)
                    if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                        v.clearFocus()
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev)
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