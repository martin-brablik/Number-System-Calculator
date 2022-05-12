package cz.upol.inf.tmap.martinbrablik.tmap_projekt

import android.util.Log
import android.widget.TextView
import java.lang.StringBuilder

class HistoryItemController {

    companion object {
        private var instance: HistoryItemController? = null
        fun getHistoryItemController(): HistoryItemController {
            if(instance == null)
                instance = HistoryItemController()
            return instance as HistoryItemController
        }
    }

    fun delete(position: Int) {
        HistoryController.getHistoryController().remove(position)
    }
}