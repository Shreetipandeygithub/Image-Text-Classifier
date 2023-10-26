package com.shreeti.snaptext

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtRecognition:Button=findViewById(R.id.txtRecognition)

        txtRecognition.setOnClickListener{
            val intent=Intent(this,TextRecognition::class.java)
            startActivity(intent)
            finish()
        }
    }
}