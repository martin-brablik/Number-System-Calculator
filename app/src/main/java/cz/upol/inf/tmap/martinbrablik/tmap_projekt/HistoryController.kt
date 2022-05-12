package cz.upol.inf.tmap.martinbrablik.tmap_projekt

import android.util.Log
import android.view.View

class HistoryController {

    var historyItems = mutableListOf<HistoryItem>()

    companion object {
        private var instance: HistoryController? = null
        fun getHistoryController(): HistoryController {
            if(instance == null)
                instance = HistoryController()
            return instance as HistoryController
        }
    }

    fun loadFromDb() {
        val db = AppDb.getDatabase(MainActivity.getMainActivity().applicationContext)
        val dao = db.historyDao()
        val history = dao.getAll().reversed()
       if(history.isNotEmpty())
            historyItems = history as MutableList<HistoryItem>
    }

    fun makeRecord(input: String, srcBase: String, dstBase: String, result: String) {
        val db = AppDb.getDatabase(MainActivity.getMainActivity().applicationContext)
        val dao = db.historyDao()
        dao.insert(HistoryItem(0, input, srcBase, dstBase, result))
    }

    fun remove(position: Int) {
        val item = historyItems[position]
        historyItems.removeAt(position)
        HistoryActivity.getHistoryActivity().adapter?.notifyItemRemoved(position)
        removeFromDb(item)
    }

    private fun removeFromDb(item: HistoryItem) {
        val db = AppDb.getDatabase(MainActivity.getMainActivity().applicationContext)
        val dao = db.historyDao()
        dao.delete(item)
    }
}