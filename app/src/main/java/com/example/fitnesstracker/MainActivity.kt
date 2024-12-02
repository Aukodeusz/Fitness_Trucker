package com.example.fitnesstracker
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesstracker.R
import com.example.fitnesstracker.Training
import com.example.fitnesstracker.TrainingAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private val trainings = mutableListOf<Training>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TrainingAdapter
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TrainingAdapter(trainings)
        recyclerView.adapter = adapter

        val typeGroup = findViewById<RadioGroup>(R.id.typeGroup)
        val distanceInput = findViewById<EditText>(R.id.distanceInput)
        val durationInput = findViewById<EditText>(R.id.durationInput)
        val caloriesInput = findViewById<EditText>(R.id.caloriesInput)
        val intensityBar = findViewById<SeekBar>(R.id.intensityBar)
        val addButton = findViewById<Button>(R.id.addButton)

        addButton.setOnClickListener {
            val type = when (typeGroup.checkedRadioButtonId) {
                R.id.walkRadio -> "Spacer"
                R.id.runRadio -> "Bieg"
                R.id.strengthRadio -> "Trening siłowy"
                else -> "Unknown"
            }
            val distance = distanceInput.text.toString().toDoubleOrNull() ?: 0.0
            val duration = durationInput.text.toString().toDoubleOrNull() ?: 0.0
            val calories = caloriesInput.text.toString().toDoubleOrNull() ?: 0.0
            val intensity = intensityBar.progress

            val training = Training(type, distance, duration, calories, intensity)
            trainings.add(training)
            adapter.notifyDataSetChanged()
            saveTrainings()
        }

        loadTrainings()
    }

    private fun loadTrainings() {
        val savedTrainings = getSharedPreferences("app", MODE_PRIVATE).getString("trainings", null)
        if (savedTrainings != null) {
            val type = object : TypeToken<MutableList<Training>>() {}.type
            trainings.addAll(gson.fromJson(savedTrainings, type))
            adapter.notifyDataSetChanged()
        }
    }

    private fun saveTrainings() {
        val json = gson.toJson(trainings)
        getSharedPreferences("app", MODE_PRIVATE).edit().putString("trainings", json).apply()
    }
}
