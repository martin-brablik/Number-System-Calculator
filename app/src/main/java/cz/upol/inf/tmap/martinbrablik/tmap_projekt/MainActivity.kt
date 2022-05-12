package cz.upol.inf.tmap.martinbrablik.tmap_projekt

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity: AppCompatActivity() {

    companion object {
        private var instance: MainActivity? = null
        fun getMainActivity(): MainActivity {
            if(instance == null)
                instance = MainActivity()
            return instance as MainActivity
        }

        lateinit var mainController: MainController
    }

    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.main_toolbar))
        mainController = MainController.getMainController()
        mainController.populateSpinner(findViewById<Spinner>(R.id.main_tv_in_src))
        mainController.populateSpinner(findViewById<Spinner>(R.id.main_tv_in_dst))
        mainController.spinner1.onItemSelectedListener = MainController.getMainController()
        mainController.spinner2.onItemSelectedListener = MainController.getMainController()
        mainController.loadSelection()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater. inflate (R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_history -> {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    fun actionConvert(view: View) {
        mainController.convert()
    }

    fun actionSwap(view: View) {
        mainController.swap()
    }

    fun actionReset(view: View) {
        mainController.reset()
    }
}