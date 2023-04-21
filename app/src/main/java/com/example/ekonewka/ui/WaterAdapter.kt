package com.example.ekonewka.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ekonewka.data.models.Flower
import com.example.ekonewka.databinding.FlowerRowBinding
import com.example.ekonewka.databinding.FlowerRowWaterBinding
import java.text.SimpleDateFormat
import java.util.*

class WaterAdapter (private val flower: List<Flower>,
                    private val type: Int,
                    private val onClick: (Flower, Int) -> Unit
): RecyclerView.Adapter<WaterAdapter.FlowerViewHolder>() {
    inner class FlowerViewHolder(binding: FlowerRowWaterBinding):
        RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener{
                onClick(flower[adapterPosition], adapterPosition)
            }
        }

        val name = binding.nameTv
        val date = binding.waterDateTv
        val typeInfo = binding.textView5

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        return FlowerViewHolder(FlowerRowWaterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        )
    }
    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {

        bindData(holder,position)
    }
    private val sdf = SimpleDateFormat("dd-MM-yyyy", Locale("pl_"))

    private fun bindData(holder: FlowerViewHolder, position: Int) {
        if(type == 1){
        val day = sdf.format(Date(flower[position].water_schedule))
        holder.name.text = flower[position].name
        holder.date.text = day
        }
        else if(type == 2){
            val day = sdf.format(Date(flower[position].sprinkle_schedule))
            holder.name.text = flower[position].name
            holder.date.text = day
            holder.typeInfo.text = "Dzień spryskiwania: "
        }
        else{
            val day = sdf.format(Date(flower[position].fertilize_schedule))
            holder.name.text = flower[position].name
            holder.date.text = day
            holder.typeInfo.text = "Dzień nawożenia: "
        }


    }
    override fun getItemCount(): Int {
        return flower.size
    }
}