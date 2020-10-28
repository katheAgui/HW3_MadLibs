package com.electiva1.hw3_madlibs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val REQ_CODE = 124
    val StoryArray = arrayOf("hints", "madlib0_simple", "madlib1_tarzan",
        "madlib2_university", "madlib3_clothes", "madlib4_dance")
    var Story_title = "hints"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun gameStartClick(view: View){

        val index = story_menu.selectedItemPosition;
        val Story_title = StoryArray[index];

        if (Story_title == "hints"){
            val Toast = Toast.makeText(this, "Choose a story!", Toast.LENGTH_SHORT)
            Toast.show()
        }
        else{
            val myIntent = Intent(this, IngresarPalabras::class.java)
            myIntent.putExtra("story", Story_title)
            startActivityForResult(myIntent, REQ_CODE)
        }
    }
}
