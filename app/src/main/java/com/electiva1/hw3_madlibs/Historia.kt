package com.electiva1.hw3_madlibs

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_historia.*
import java.util.*
import kotlin.collections.ArrayList


class Historia : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historia)
        val inputs = intent.getStringArrayListExtra("inputs")  // recupera las palabras ingresadas
        val storyID = intent.getIntExtra("storyID", 0) // recupera el id
        writer(inputs, storyID)
    }

    fun writer(inputs: ArrayList<String>, storyID: Int){
        val builder = StringBuilder()
        val reader = Scanner(resources.openRawResource(storyID))

        val first_line = reader.nextLine() // llama la primera línea sin agregar espacio
        builder.append(first_line)
        while(reader.hasNextLine()){
            val line = reader.nextLine()
            builder.append(" ") // agrega el espacio faltante entre líneas
            builder.append(line)
        }

        var story = builder.toString()
        // identifica espacios en blanco
        val r = Regex("<.*?>")
        val blanks = r.findAll(story).map { it.value }
        var i: Int = 0
        for(blank in blanks){
            story = story.replaceFirst(blank, inputs.get(i))
            i++
        }
        story_text.text = "$story"
    }


    fun makeNew(view: View){
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent) // ir a la página de inicio
    }
}
