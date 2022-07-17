package com.geekymusketeers.gitcheats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        textlabel.text = intent.getStringExtra("label")!!.capitalize()
        textDisplayCommand.text = intent.getStringExtra("usage")
        textDisplayNote.text = intent.getStringExtra("nb")

        if (textDisplayNote.text.isNullOrEmpty()){
            cardNote.visibility = View.GONE
            textNote.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}