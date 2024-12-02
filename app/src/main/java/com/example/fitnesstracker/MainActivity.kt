import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        findViewById<Button>(R.id.addButton).setOnClickListener { addTraining() }
        loadTrainings()
    }

    private fun addTraining() {
        val type = when (findViewById<RadioGroup>(R.id.typeGroup).checkedRadioButtonId) {
            R.id.walkRadio -> "Spacer"
            R.id.runRadio -> "Bieg"
            R.id.strengthRadio -> "Trening siłowy"
            else -> "Unknown"
        }
        val distance = findViewById<EditText>(R.id.distanceInput).text.toString().toDoubleOrNull() ?: 0.0
        val duration = findViewById<EditText>(R.id.durationInput).text.toString().toDoubleOrNull() ?: 0.0
        val calories = findViewById<EditText>(R.id.caloriesInput).text.toString().toDoubleOrNull() ?: 0.0
        val intensity = findViewById<SeekBar>(R.id.intensityBar).progress

        trainings.add(Training(type, distance, duration, calories, intensity))
        adapter.notifyDataSetChanged()
        saveTrainings()
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
