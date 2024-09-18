package com.example.practica6

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var spSimple: Spinner
    private lateinit var spCustom: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los Spinners
        spSimple = findViewById(R.id.spSimple)
        spCustom = findViewById(R.id.spCustom)

        // Configuración del Spinner simple
        ArrayAdapter.createFromResource(
            this,
            R.array.paises,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spSimple.adapter = adapter
        }

        // Configuración del Spinner personalizado
        val countries = resources.getStringArray(R.array.paises)
        val icons = intArrayOf(
            R.drawable.australia, // Icono de Australia
            R.drawable.china,     // Icono de China
            R.drawable.india,     // Icono de India
            R.drawable.united_kingdom,       // Icono de UK
            R.drawable.united_states     // Icono de USA
        )

        // Adaptador personalizado para el Spinner
        val customAdapter = object : ArrayAdapter<String>(this, R.layout.custom_spinner_item, countries) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                return createCustomView(position, convertView, parent)
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                return createCustomView(position, convertView, parent)
            }

            @SuppressLint("MissingInflatedId")
            private fun createCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
                val inflater = layoutInflater
                val view = inflater.inflate(R.layout.custom_spinner_item, parent, false)
                val icon = view.findViewById<ImageView>(R.id.imageViewIcon)
                val countryName = view.findViewById<TextView>(R.id.textViewCountry)

                countryName.text = countries[position]

                if (position > 0) { // Para omitir "Seleccionar país"
                    icon.setImageResource(icons[position - 1])
                } else {
                    icon.visibility = View.GONE
                }

                return view
            }
        }

        spCustom.adapter = customAdapter

        // Mostrar un Toast con el país seleccionado
        spCustom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position > 0) { // Para omitir "Seleccionar país"
                    val selectedCountry = countries[position]
                    Toast.makeText(applicationContext, "País seleccionado: $selectedCountry", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hacer nada si no se selecciona nada
            }
        }
    }
}
