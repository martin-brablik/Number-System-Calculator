package cz.upol.inf.tmap.martinbrablik.tmap_projekt

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.StringBuilder

class HistoryItemActivity: AppCompatActivity() {

    lateinit var historyItemController: HistoryItemController

    lateinit var txtInputs: TextView
    lateinit var txtResults: TextView

    companion object {
        private var instance: HistoryItemActivity? = null
        fun getHistoryItemActivity(): HistoryItemActivity {
            if(instance == null)
                instance = HistoryItemActivity()
            return instance as HistoryItemActivity
        }
    }

    init {
        instance = this
        historyItemController = HistoryItemController.getHistoryItemController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_item)
        txtInputs = findViewById(R.id.historyItem_inputs)
        txtResults = findViewById(R.id.historyItem_results)
        build()
        setSupportActionBar(findViewById(R.id.main_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun actionDelete(view: View) {
        HistoryItemController.getHistoryItemController().delete(intent.getIntExtra("position", 0))
        finish()
    }

    fun build() {
        val input = StringBuilder()
        val result = StringBuilder()
        input.append("[ ").append(intent.getStringExtra("src",)).append(" ]\n").append(intent.getStringExtra("input"))
        result.append("[ ").append(intent.getStringExtra("dst")).append(" ]\n").append(intent.getStringExtra("result"))
        txtInputs.text = input.toString()
        txtResults.text = result.toString()
    }
}