package com.geekymusketeers.gitcheats

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.geekymusketeers.gitcheats.Adapter.RecyclerViewAdapter
import com.geekymusketeers.gitcheats.Model.PrimaryModel
import com.geekymusketeers.gitcheats.Model.SecondaryModel
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), Click {

    private lateinit var jsonObject: JSONObject
    private var list: ArrayList<PrimaryModel> = ArrayList()
    private var listRecyclerView : ArrayList<SecondaryModel> = ArrayList()
    private val FILENAME: String = "git_command_explorer.json"
    private lateinit var primaryValue: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        jsonObject = JSONObject(loadJSONFromAsset())

        PrimaryOptions()
        inputField.setOnTouchListener { _, _ ->
            inputField.showDropDown()
            false
        }

        inputField.setOnItemClickListener { adapterView, view, pos, l ->
            primaryValue = list.find {
                it.label == inputField.text.toString()
            }?.value!!
            dismissKeyboard(inputField)
            getItems()
        }
    }

    private fun getItems(){
        listRecyclerView.clear()
        val jsonSecondaryOptionsObject = jsonObject.getJSONObject("secondary_options")
        val jsonArray = jsonSecondaryOptionsObject.getJSONArray(primaryValue)
        for (i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            val seconday = SecondaryModel()
            seconday.value = jsonObject.getString("value")
            seconday.label = jsonObject.getString("label")
            if (jsonObject.has("usage")){
                seconday.usage = jsonObject.getString("usage")
            }
            if (jsonObject.has("nb")){
                seconday.nb = jsonObject.getString("nb")
            }
            listRecyclerView.add(seconday)
        }
        recyclerView.adapter =
            RecyclerViewAdapter(
                this,
                listRecyclerView,
                this)
    }

    private fun Context.dismissKeyboard(view: View) {
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun PrimaryOptions() {
        val jsonPrimaryOptionsArray = jsonObject.getJSONArray("primary_options")
        for (i in 0 until jsonPrimaryOptionsArray.length()) {
            val jsonObject = jsonPrimaryOptionsArray.getJSONObject(i)
            val primary = PrimaryModel()
            primary.value = jsonObject.getString("value")
            primary.label = jsonObject.getString("label")
            list.add(primary)
        }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, list.map {
                it.label
            }
        )
        inputField.setAdapter(adapter)
    }

    override fun onBackPressed() {
        dismissKeyboard(inputField)
        super.onBackPressed()
    }

    private fun loadJSONFromAsset(): String {
        return assets.open(FILENAME).bufferedReader().use {
            it.readText()
        }
    }

    override fun clickListener(position: Int) {
        val intent = Intent(this, InfoActivity::class.java)
        intent.putExtra("label", listRecyclerView[position].label)
        intent.putExtra("value", listRecyclerView[position].value)
        intent.putExtra("usage", listRecyclerView[position].usage)
        intent.putExtra("nb", listRecyclerView[position].nb)
        startActivity(intent)
        overridePendingTransition( android.R.anim.fade_in, android.R.anim.fade_out );
    }
}