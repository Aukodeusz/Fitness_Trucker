package com.example.fitnesstracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class TrainingAdapter(private val trainings: List<Training>) : RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder>() {
    class TrainingViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_training, parent, false)
        return TrainingViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        val training = trainings[position]
        holder.view.findViewById<TextView>(R.id.trainingType).text = training.type
        holder.view.findViewById<TextView>(R.id.trainingDetails).text =
            "Dystans: ${training.distance} km, Czas: ${training.duration} min, Kalorie: ${training.calories} kcal, Intensywność: ${training.intensity}"

        holder.view.setOnClickListener {
            AlertDialog.Builder(holder.view.context).apply {
                setTitle("Szczegóły treningu")
                setMessage("Typ: ${training.type}\nDystans: ${training.distance} km\nCzas: ${training.duration} min\nKalorie: ${training.calories} kcal\nIntensywność: ${training.intensity}")
                setPositiveButton("OK", null)
                show()
            }
        }
    }

    override fun getItemCount() = trainings.size
}
