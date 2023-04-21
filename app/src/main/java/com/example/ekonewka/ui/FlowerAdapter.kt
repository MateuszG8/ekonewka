package com.example.ekonewka.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ekonewka.data.models.Flower
import com.example.ekonewka.databinding.FlowerRowBinding

class FlowerAdapter (private val flower: List<Flower>,
                     private val onClick: (Flower, Int) -> Unit
): RecyclerView.Adapter<FlowerAdapter.FlowerViewHolder>() {
    inner class FlowerViewHolder(binding: FlowerRowBinding):
        RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener{
                onClick(flower[adapterPosition], adapterPosition)
            }
        }

        val name = binding.nameTv

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        return FlowerViewHolder(FlowerRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        )
    }
    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {

        bindData(holder,position)
    }

    private fun bindData(holder: FlowerViewHolder, position: Int) {
        holder.name.text = flower[position].name
    }
    override fun getItemCount(): Int {
        return flower.size
    }
}