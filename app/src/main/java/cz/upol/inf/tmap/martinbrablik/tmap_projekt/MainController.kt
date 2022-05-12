package cz.upol.inf.tmap.martinbrablik.tmap_projekt

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly


class MainController(): AdapterView.OnItemSelectedListener {

    var numberIn: EditText = MainActivity.getMainActivity().findViewById<EditText>(R.id.main_tv_in_number)
    var srcBaseIn: Spinner = MainActivity.getMainActivity().findViewById<Spinner>(R.id.main_tv_in_src)
    var dstBaseIn: Spinner = MainActivity.getMainActivity().findViewById<Spinner>(R.id.main_tv_in_dst)
    var resultOut: EditText = MainActivity.getMainActivity().findViewById<EditText>(R.id.main_ta_in)
    val spinner1: Spinner = MainActivity.getMainActivity().findViewById(R.id.main_tv_in_src)
    val spinner2: Spinner = MainActivity.getMainActivity().findViewById(R.id.main_tv_in_dst)

    companion object {
        private var instance: MainController? = null
        fun getMainController(): MainController {
            if(instance == null)
                instance = MainController()
            return instance as MainController
        }
    }

    fun populateSpinner(spinner: Spinner) {
        ArrayAdapter.createFromResource(
            MainActivity.getMainActivity().applicationContext,
            R.array.dropdown_choices,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    fun convert(): Pair<String, Boolean> {
        val values: List<String> = numberIn.text.split(' ')
        val srcBase = srcBaseIn.selectedItemPosition + 1
        val dstBase = dstBaseIn.selectedItemPosition + 1

        val sb = StringBuilder()
        for(value in values) {
            if(srcBase > 1) {
                if(srcBase <= 10 && !value.isDigitsOnly()) {
                    displayError(MainActivity.getMainActivity().resources.getString(R.string.err_invalidInput))
                    sb.append(MainActivity.getMainActivity().resources.getString(R.string.err_invalidInput))
                    break;
                }
            }
            sb.append(
                when {
                    srcBase == dstBase -> value
                    srcBase > 1 && dstBase > 1 -> Utils.anyToAny(value, srcBase, dstBase)
                    srcBase > 1 && dstBase == 1 -> Utils.anyToAscii(value, srcBase)
                    srcBase == 1 && dstBase > 1 -> Utils.asciiToAny(value, dstBase)
                    else -> displayError(R.string.err_invalidInput.toString())
                }
            )
        }
        val result = Pair(sb.toString(),
            sb.toString() != MainActivity.getMainActivity().resources.getString(R.string.err_invalidInput)
        )
        resultOut.setText(sb.toString())
        if(result.second) makeHistoryRecord()
        return result
    }

    private fun displayError(message: String): String {
        resultOut.setText(message)
        return message
    }

    fun makeHistoryRecord() {
        HistoryController.getHistoryController().makeRecord(
            numberIn.text.toString().replace(' ', '\n'),
            srcBaseIn.selectedItem.toString(),
            dstBaseIn.selectedItem.toString(),
            resultOut.text.toString())
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        saveSelection()
        val nightModeFlag: Boolean = isDarkTheme()
        (parent.getChildAt(0) as TextView?)?.setTextColor(
            when {
                nightModeFlag -> ContextCompat.getColor(MainActivity.getMainActivity().applicationContext, R.color.white)
                else -> ContextCompat.getColor(MainActivity.getMainActivity().applicationContext, R.color.black)
            }
        )
    }


    private fun saveSelection() {
        val sharedPref = MainActivity.getMainActivity().getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt("src", spinner1.selectedItemPosition)
            putInt("dst", spinner2.selectedItemPosition)
            apply()
        }
    }

    fun loadSelection() {
        val sharedPref = MainActivity.getMainActivity().getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = 0
        val srcPos = sharedPref.getInt("src", defaultValue)
        val dstPos = sharedPref.getInt("dst", defaultValue)
        spinner1.setSelection(srcPos)
        spinner2.setSelection(dstPos)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {}

    private fun isDarkTheme(): Boolean {
        return MainActivity.getMainActivity().resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    fun swap() {
        val tmpItemPos: Int = srcBaseIn.selectedItemPosition
        srcBaseIn.setSelection(dstBaseIn.selectedItemPosition)
        dstBaseIn.setSelection(tmpItemPos)

        val tmpNumberIn = numberIn.text
        numberIn.text = resultOut.text
        resultOut.text = tmpNumberIn

        if(numberIn.text.toString().isNotEmpty() && resultOut.text.toString().isNotEmpty())
            makeHistoryRecord()
    }

    fun reset() {
        numberIn.setText("")
        resultOut.setText("")
    }
}