package com.example.cse224ca3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout

class RegisterationSuccessful : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration_successful)

        val feed = findViewById<Button>(R.id.feeed)
        val personName = findViewById<TextView>(R.id.datar)
        val name = intent.getStringExtra("Extra_Name")
        val namestring = "$name, your data has been recorded"
        personName.text = namestring

        feed.setOnClickListener(){

            val builder = AlertDialog.Builder(this)
            builder.setTitle("FeedBack")
                .setMessage("Please give your valuable feedback here")
            val int = EditText(this)
            val lp = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
            int.layoutParams = lp
            builder.setView(int)

            builder.setPositiveButton("Ok"){a,b ->
                Toast.makeText(applicationContext, "Your Valuable Feedback has been recorded",
                    Toast.LENGTH_LONG).show()
            }
            builder.show()

        }
    }
}