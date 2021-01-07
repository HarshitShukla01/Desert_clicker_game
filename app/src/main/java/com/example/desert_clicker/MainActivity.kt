package com.example.desert_clicker

import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import com.example.desert_clicker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var revenue = 0
    private var dessertsSold = 0


    private lateinit var binding: ActivityMainBinding

    data class Dessert(val imageId: Int, val price: Int, val startProductionAmount: Int)

    private val allDesserts = listOf(

        Dessert(R.drawable.cupcake, 5, 0),
        Dessert(R.drawable.donut, 10, 5),
        Dessert(R.drawable.eclair, 15, 10),
        Dessert(R.drawable.froyo, 30, 15),
        Dessert(R.drawable.gingerbread, 50, 50),
        Dessert(R.drawable.honeycomb, 100, 75),
        Dessert(R.drawable.icecreamsandwich, 500, 100),
        Dessert(R.drawable.jellybean, 1000, 125),
        Dessert(R.drawable.kitkat, 2000, 150),
        Dessert(R.drawable.lollipop, 3000, 175),
        Dessert(R.drawable.marshmallow, 4000, 200),
        Dessert(R.drawable.nougat, 5000, 225),
        Dessert(R.drawable.oreo, 6000, 250)
    )
    private var currentDessert = allDesserts[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(Companion.TAG, "onCreated Called")
        // Use Data Binding to get reference to the views
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (savedInstanceState != null) {
            revenue = savedInstanceState.getInt(KEY_REVENUE, 0)
            dessertsSold = savedInstanceState.getInt(KEY_DESSERT_SOLD, 0)
            showCurrentDessert()
        }
        binding.dessertButton.setOnClickListener {
            onDessertClicked()
        }
        binding.revenue = revenue
        binding.amountSold = dessertsSold
        binding.dessertButton.setImageResource(currentDessert.imageId)
    }

    private fun onDessertClicked() {

        // Update the score
        revenue += currentDessert.price
        dessertsSold++

        binding.revenue = revenue
        binding.amountSold = dessertsSold

        // Show the next dessert
        showCurrentDessert()
    }

    private fun showCurrentDessert() {
        var newDessert = allDesserts[0]
        for (dessert in allDesserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                newDessert = dessert
            }
            else break
        }

        if (newDessert != currentDessert) {
            currentDessert = newDessert
            binding.dessertButton.setImageResource(newDessert.imageId)
        }
    }

    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText(getString(R.string.share_text, dessertsSold, revenue))
            .setType("text/plain")
            .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareMenuButton -> onShare()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        Log.d(Companion.TAG, "onResume Called")
    }

    override fun onStart() {
        super.onStart()
        Log.d(Companion.TAG, "onStart Called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(Companion.TAG, "onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(Companion.TAG, "onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(Companion.TAG, "onDestroy Called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(Companion.TAG, "onRestart Called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_REVENUE, revenue)
        outState.putInt(KEY_DESSERT_SOLD, dessertsSold)
        Log.d(TAG, "onSaveInstanceState Called")
    }

    companion object {
        const val TAG = "MainActivity"
        const val KEY_REVENUE = "revenue_key"
        const val KEY_DESSERT_SOLD = "dessert_sold_key"
    }
}
