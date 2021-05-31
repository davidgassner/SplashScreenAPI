package com.example.myapplication

import android.animation.ObjectAnimator
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    Thread.sleep(2000)
                    // Check if the initial data is ready.
                    // The content is ready; start drawing.
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    return true
                }
            }
        )

        // Add a callback that's called when the splash screen is animating to
        // the app content.
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            // Create your custom animation.
            val slideOut = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.height.toFloat()
            )
            slideOut.interpolator = LinearInterpolator()
            slideOut.duration = 300L

            // Call SplashScreenView.remove at the end of your custom animation.
            slideOut.doOnEnd {
                splashScreenView.remove()
            }

            // Run your animation.
            slideOut.start()

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}