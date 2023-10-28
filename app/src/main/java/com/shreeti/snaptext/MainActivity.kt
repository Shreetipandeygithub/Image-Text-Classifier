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
        val imgRecognition:Button=findViewById(R.id.imgRecognition)

        txtRecognition.setOnClickListener{
            val intent=Intent(this,TextRecognition::class.java)
            startActivity(intent)
            finish()

        }

        imgRecognition.setOnClickListener{
            val intent=Intent(this,ImageRecognitionActivity::class.java)
            startActivity(intent)

        }
    }
}