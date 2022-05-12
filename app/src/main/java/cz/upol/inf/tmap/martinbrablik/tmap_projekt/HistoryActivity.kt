package cz.upol.inf.tmap.martinbrablik.tmap_projekt

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryItem(@PrimaryKey(autoGenerate = true) val id: Int, @ColumnInfo(name = "input") val input: String, @ColumnInfo(name = "src") val src: String, @ColumnInfo(name= "dst") val dst: String, @ColumnInfo(name = "result") val result: String)

class HistoryActivity: AppCompatActivity() {

    var adapter: HistoryAdapter? = null
    lateinit var historyController: HistoryController

    companion object {
        private var instance: HistoryActivity? = null
        fun getHistoryActivity(): HistoryActivity {
            if(instance == null)
                instance = HistoryActivity()
            return instance as HistoryActivity
        }
    }

    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(findViewById(R.id.main_toolbar))
        historyController = HistoryController.getHistoryController()

        historyController.loadFromDb()
        adapter = HistoryAdapter(applicationContext)
        val layoutManager = LinearLayoutManager(applicationContext)
        val recyclerView = findViewById<RecyclerView>(R.id.history_recycler)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView?.adapter = adapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}