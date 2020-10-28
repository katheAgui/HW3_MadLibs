package com.electiva1.hw3_madlibs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ingresarpalabras.*
import java.util.*
import kotlin.collections.ArrayList


class IngresarPalabras : AppCompatActivity() {

    private val REQ_CODE = 123
    private val palabras = ArrayList<String>()
    private val palabrasType = ArrayList<String>()
    private lateinit var myAdapter: ArrayAdapter<String>
    private var counter = 0
    private var storyID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresarpalabras)
        val story = intent.getStringExtra("story")
        extract(story)
    }

    // esta función buscará los tipos de palabras en el texto y devolverá el resultado en palabras
    fun extract(story: String){
        val builder = StringBuilder()
        // recibir el id de la historia
        this.storyID = resources.getIdentifier(story, "raw", packageName)
        val reader = Scanner(resources.openRawResource(storyID))
        while(reader.hasNextLine()){
            val line = reader.nextLine()
            builder.append(line)
        }
        var story = builder.toString()
        val r = Regex("<.*?>")
        val found = r.findAll(story)
        found.forEach{ f ->
            val m = f.value
            palabrasType.add(m)
            counter++
        }
        val first_type = palabrasType.get(0)
        new_words.hint = "place log in $first_type"
        hints.text = "remaining words: $counter"
    }

    fun add_new_words(view: View){

        // recorta el espacio en blanco y comprueba si está vacío
        if(new_words.text.toString().isEmpty() || new_words.text.toString().trim().isEmpty()){
            val Toast = Toast.makeText(this, "Enter the word!", Toast.LENGTH_SHORT)
            Toast.show()
        }
        else{
            val word = new_words.text.toString().trim()
            palabras.add(word)
            counter--
            new_words.setText("") // borra la barra de texto cada vez que ingrese algo
            if(counter >= 1){
                val next_type = palabrasType.get(palabrasType.size - counter)
                new_words.hint = "lace log in $next_type"
                hints.text = "remaining words: $counter"
            }

            if(counter == 1)
                enter.text = "FINISHED!"


            if(counter == 0){
                val myIntent = Intent(this, Historia::class.java)
                myIntent.putExtra("inputs", palabras)
                myIntent.putExtra("storyID", storyID) // pase la ID de la historia a Historia.kt para leer los archivos
                startActivityForResult(myIntent, REQ_CODE) // pasar a la página de la historia
            }
        }
    }
}
