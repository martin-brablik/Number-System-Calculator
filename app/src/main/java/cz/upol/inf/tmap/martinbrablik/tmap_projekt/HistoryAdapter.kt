package cz.upol.inf.tmap.martinbrablik.tmap_projekt

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.StringBuilder

class HistoryAdapter(val context: Context): RecyclerView.Adapter<HistoryAdapter.HistoryItemHolder>() {
    inner class HistoryItemHolder(itemView: View,
        val textView: TextView = itemView.findViewById(R.id.history_itemText),
        val button: ImageButton = itemView.findViewById(R.id.history_itemBtn)): RecyclerView.ViewHolder(itemView) {

        init {
            textView.isClickable = true
            textView.setOnClickListener(showMore())
            button.setOnClickListener(delete())
        }

        private fun showMore(): (View) -> Unit = {
            layoutPosition.also { currentPosition ->
                val item: HistoryItem = HistoryController.getHistoryController().historyItems[currentPosition]
                val intent = Intent(itemView.context, HistoryItemActivity::class.java).apply {
                    putExtra("input", item.input)
                    putExtra("src", item.src)
                    putExtra("dst", item.dst)
                    putExtra("result", item.result)
                    putExtra("position", currentPosition)
                }
                itemView.context.startActivity(intent)
            }
        }

        private fun delete(): (View) -> Unit = {
            layoutPosition.also { currentPosition ->
                HistoryController.getHistoryController().remove(currentPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return HistoryItemHolder(item)
    }

    override fun onBindViewHolder(holder: HistoryItemHolder, position: Int) {
        val sb = StringBuilder()
        val historyItem = HistoryController.getHistoryController().historyItems[position]
        val inputs = historyItem.input.split('\n')
        val results = historyItem.result.split('\n')
        sb.append("[ ").append(historyItem.src).append(" ]  ").append(inputs[0]).append('\n')
        sb.append("[ ").append(historyItem.dst).append(" ]  ").append(results[0])
        if(results.size > 3 || inputs.size > 3) sb.append("\n...")
        holder.textView.setText(sb.toString())
    }

    override fun getItemCount(): Int = HistoryController.getHistoryController().historyItems.size
}